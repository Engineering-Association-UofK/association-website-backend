package edu.uofk.ea.association_website_backend.service;

import edu.uofk.ea.association_website_backend.model.admin.AdminModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /// This is the method that generates JWT tokens for admins.
    public String generateAdminToken(AdminModel admin) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", admin.getRoles());
        claims.put("type", "admin");

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(admin.getName())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(1000 * 60 * 60 * 24 * 2))) // 2 Days.
                .and()
                .signWith(getKey())
                .compact();
    }

    /// This is the method that generates JWT tokens for users.
    public String generateUserToken(AdminModel admin) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", admin.getRoles());
        claims.put("type", "user");

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(admin.getName())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(1000 * 60 * 60 * 24 * 2))) // 2 Days.
                .and()
                .signWith(getKey())
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public  <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
