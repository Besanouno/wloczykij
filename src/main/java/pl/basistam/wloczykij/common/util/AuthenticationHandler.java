package pl.basistam.wloczykij.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.basistam.wloczykij.common.exceptions.ResourceNotFoundException;
import pl.basistam.wloczykij.database.model.User;
import pl.basistam.wloczykij.database.repository.UserRepository;

@Service
public class AuthenticationHandler {
    private final UserRepository userRepository;

    @Autowired
    public AuthenticationHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser() {
        String principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return userRepository.findUserByLogin(principal)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + principal + " not found"));
    }
}
