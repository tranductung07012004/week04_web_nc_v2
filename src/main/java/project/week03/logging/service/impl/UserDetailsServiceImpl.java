package project.week03.logging.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.week03.logging.entity.User;
import project.week03.logging.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Try to find user by email first, then by username
        User user = userRepository.findByEmail(username)
                .orElseGet(() -> {
                    try {
                        return userRepository.findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException("User not found with email/username: " + username));
                    } catch (Exception e) {
                        throw new UsernameNotFoundException("User not found with email/username: " + username);
                    }
                });
        
        return user;
    }
    
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        
        return user;
    }
}
