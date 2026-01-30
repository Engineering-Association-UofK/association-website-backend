package edu.uofk.ea.association_website_backend.service;

import edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions.JwtSignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final AdminDetailsService adminDetailsService;
    private final HandlerExceptionResolver resolver;

    @Autowired
    public JwtAuthenticationFilter(JWTService jwtService, AdminDetailsService adminDetailsService, @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver){
        this.jwtService = jwtService;
        this.adminDetailsService = adminDetailsService;
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        String type = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
                username = jwtService.extractUserName(token);
                type = jwtService.extractClaim(token, claims -> claims.get("type", String.class));
            } catch (Exception e) {
                resolver.resolveException(request, response, null, new JwtSignatureException(e.getMessage()));
                return;
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = null;

            /// Changes the authentication type depending on Token Type
            /// For security, website admins are separate from normal user (students)
            if ("admin".equals(type)) {
                userDetails = adminDetailsService.loadUserByUsername(username);
            } else if ("user".equals(type)) {
                // TODO: Make user authentication
            }

            if (userDetails.isEnabled() && userDetails.isAccountNonLocked() && jwtService.isTokenValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        
        filterChain.doFilter(request, response);
    }
}
