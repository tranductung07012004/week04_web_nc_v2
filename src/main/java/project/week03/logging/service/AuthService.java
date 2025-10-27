package project.week03.logging.service;

import project.week03.logging.dto.AuthResponseDTO;
import project.week03.logging.dto.LoginRequestDTO;
import project.week03.logging.dto.RegisterRequestDTO;

public interface AuthService {
    
    AuthResponseDTO login(LoginRequestDTO loginRequest);
    
    AuthResponseDTO register(RegisterRequestDTO registerRequest);
    
    AuthResponseDTO logout();
    
    String generateTokenForUser(String email);
}
