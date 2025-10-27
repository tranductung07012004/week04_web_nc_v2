package project.week03.logging.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.week03.logging.dto.AuthResponseDTO;
import project.week03.logging.dto.LoginRequestDTO;
import project.week03.logging.dto.RegisterRequestDTO;
import project.week03.logging.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    // 1. LOGIN API
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest,
                                                HttpServletResponse response) {
        try {
            AuthResponseDTO authResponse = authService.login(loginRequest);
            
            String token = authService.generateTokenForUser(loginRequest.getEmail());
            
            // Set JWT token in HTTP-only cookie
            Cookie jwtCookie = new Cookie("jwt", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setSecure(false); // Set to true in production with HTTPS
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(24 * 60 * 60); // 24 hours
            response.addCookie(jwtCookie);
            
            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponseDTO("Invalid email or password"));
        }
    }
    
    // 2. REGISTER API
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO registerRequest,
                                                    HttpServletResponse response) {
        try {
            AuthResponseDTO authResponse = authService.register(registerRequest);
            
            // Generate token for cookie
            String token = authService.generateTokenForUser(registerRequest.getEmail());
            
            // Set JWT token in HTTP-only cookie
            Cookie jwtCookie = new Cookie("jwt", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setSecure(false); // Set to true in production with HTTPS
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(24 * 60 * 60); // 24 hours
            response.addCookie(jwtCookie);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthResponseDTO(e.getMessage()));
        }
    }
    
    // 3. LOGOUT API
    @PostMapping("/logout")
    public ResponseEntity<AuthResponseDTO> logout(HttpServletResponse response) {
        AuthResponseDTO authResponse = authService.logout();
        
        // Clear JWT cookie
        Cookie jwtCookie = new Cookie("jwt", null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(false);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0); // Expire immediately
        response.addCookie(jwtCookie);
        
        return ResponseEntity.ok(authResponse);
    }
}
