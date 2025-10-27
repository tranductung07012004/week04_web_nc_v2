package project.week03.logging.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.week03.logging.dto.AuthResponseDTO;
import project.week03.logging.dto.LoginRequestDTO;
import project.week03.logging.dto.RegisterRequestDTO;
import project.week03.logging.entity.Role;
import project.week03.logging.entity.User;
import project.week03.logging.repository.RoleRepository;
import project.week03.logging.repository.UserRepository;
import project.week03.logging.service.AuthService;
import project.week03.logging.util.JwtUtil;

import java.util.Collections;

@Service
public class AuthServiceImpl implements AuthService {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Override
    public AuthResponseDTO login(LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
            )
        );
        
        User user = (User) authentication.getPrincipal();
        //String token = jwtUtil.generateToken(user);
        
        return new AuthResponseDTO(
            "Login successful",
            user.getUsername(),
            user.getEmail()
        );
    }
    
    @Override
    public AuthResponseDTO register(RegisterRequestDTO registerRequest) {
        // Check if username already exists
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        }
        
        // Check if email already exists
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email is already in use!");
        }
        
        // Create new user
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEnabled(true);
        
        // Assign default role (USER)
        Role userRole = roleRepository.findByRoleName("USER")
                .orElseThrow(() -> new RuntimeException("User role not found"));
        user.setRoles(Collections.singletonList(userRole));
        
        User savedUser = userRepository.save(user);
        
        // Generate token for the new user
        //String token = jwtUtil.generateToken(savedUser);
        
        return new AuthResponseDTO(
            "User registered successfully",
            savedUser.getUsername(),
            savedUser.getEmail()
        );
    }
    
    @Override
    public AuthResponseDTO logout() {
        SecurityContextHolder.clearContext();
        return new AuthResponseDTO("Logout successful");
    }
    
    @Override
    public String generateTokenForUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        System.out.println("This is user in generateTokenForUser" + true + user);
        return jwtUtil.generateToken(user);
    }
}
