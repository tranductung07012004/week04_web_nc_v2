package project.week03.logging.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import project.week03.logging.entity.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import project.week03.logging.service.impl.UserDetailsServiceImpl;
import project.week03.logging.util.JwtUtil;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        
        String email = null;
        String jwt = null;
        System.out.println("Breakpoint 1" + true);
        
//        // 1. Check Authorization header first
//        final String authorizationHeader = request.getHeader("Authorization");
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            jwt = authorizationHeader.substring(7);
//            try {
//                username = jwtUtil.extractUsername(jwt);
//            } catch (Exception e) {
//                logger.error("JWT token is invalid or expired");
//            }
//        }

        System.out.println("Breakpoint 2");
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    jwt = cookie.getValue();
                    try {
                        email = jwtUtil.extractEmail(jwt);
                    } catch (Exception e) {
                        logger.error("JWT cookie is invalid or expired");
                    }
                    break;
                }
            }
        }

        // Only authenticate if we have a valid token AND no existing authentication
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            System.out.println("Break point 3");
            try {
                User userDetails = this.userDetailsService.loadUserByEmail(email);

                System.out.println("Breakpoint 4 " + email + " " + userDetails + " " + jwt);

                if (jwtUtil.validateToken(jwt, userDetails)) {
                    System.out.println("Breakpoint 5");
                    // Debug: Log authorities
                    logger.info("User: {} has authorities: {}" + email + userDetails.getAuthorities());
                    
                    UsernamePasswordAuthenticationToken authToken = 
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } catch (Exception e) {
                logger.error("Cannot set user authentication: {}" + e.getMessage() + e);
            }
        }
        
        filterChain.doFilter(request, response);
    }
}
