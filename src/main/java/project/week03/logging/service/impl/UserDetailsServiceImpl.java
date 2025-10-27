package project.week03.logging.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import project.week03.logging.entity.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.week03.logging.entity.User;
import project.week03.logging.repository.UserRepository;

@Service
public class UserDetailsServiceImpl {
    
    @Autowired
    private UserRepository userRepository;
    // Cai nay dang gap van de
    public User loadUserByEmail(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }
}
