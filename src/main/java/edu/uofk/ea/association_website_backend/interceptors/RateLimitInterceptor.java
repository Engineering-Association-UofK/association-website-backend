package edu.uofk.ea.association_website_backend.interceptors;

import edu.uofk.ea.association_website_backend.annotations.RateLimited;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();
    private final Map<String, Long> banExpiry = new ConcurrentHashMap<>();
    private final Map<String, Integer> violationCounts = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        RateLimited rateLimited = handlerMethod.getMethodAnnotation(RateLimited.class);

        if (rateLimited != null) {
            String ip = getClientIp(request);
            String key = ip + "_" + rateLimited.key();

            if (isBanned(key)) {
                long secondsLeft = (banExpiry.get(key) - Instant.now().toEpochMilli()) / 1000;
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                response.getWriter().write("Too many requests. Try again in " + secondsLeft + " seconds.");
                return false;
            }

            Bucket bucket = cache.computeIfAbsent(key, k -> createNewBucket(rateLimited));

            if (bucket.tryConsume(1)) {
                return true;
            } else {
                if (rateLimited.exponentialBackoff()) {
                    handleExponentialBackoff(key, rateLimited.refillDuration());
                }

                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                response.getWriter().write("Too many requests. Please try again later.");
                return false;
            }
        }

        return true;
    }

    private boolean isBanned(String key) {
        Long expiry = banExpiry.get(key);
        if (expiry == null) return false;
        if (System.currentTimeMillis() > expiry) {
            banExpiry.remove(key); // Ban expired
            return false;
        }
        return true;
    }

    private void handleExponentialBackoff(String key, int baseDurationSeconds) {
        int violations = violationCounts.getOrDefault(key, 0) + 1;
        violationCounts.put(key, violations);

        long banDurationMillis = (long) (baseDurationSeconds * Math.pow(2, violations - 1) * 1000);
        
        // Cap the ban at 1 hour to prevent infinite lockouts
        banDurationMillis = Math.min(banDurationMillis, 3600000);

        banExpiry.put(key, System.currentTimeMillis() + banDurationMillis);
    }

    private Bucket createNewBucket(RateLimited annotation) {
        return Bucket.builder()
                .addLimit(Bandwidth.classic(annotation.capacity(), 
                        Refill.greedy(annotation.refillTokens(), Duration.ofSeconds(annotation.refillDuration()))))
                .build();
    }

    private String getClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}