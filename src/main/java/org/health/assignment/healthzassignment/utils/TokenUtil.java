package org.health.assignment.healthzassignment.utils;

import org.health.assignment.healthzassignment.model.User;
import org.health.assignment.healthzassignment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;

/**
 * This class is to validate the token and get the email address from provided token.
 */
@Service
public class TokenUtil {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Validate the basic token using the email address and password provided.
     * If user exist with that email address and password is correct then only
     * this method will return true.
     *
     * @param token
     * @return whether token is valid or not.
     */
    public boolean validateToken(String token) {
        if (token != null && token.startsWith("Basic ")) {
            String base64Credentials = token.substring("Basic ".length()).trim();
            String credentials = new String(Base64.getDecoder().decode(base64Credentials));
            final String[] values = credentials.split(":", 2);
            String username = values[0];
            String password = values[1];
            Optional<User> user = this.userRepository.findByEmail(username);
            if (user.isPresent()) {
                return passwordEncoder.matches(password, user.get().getPassword());
            }
        }
        return false;
    }

    /**
     * Get the email address from the provided token in Authorization header.
     *
     * @param token
     * @return Parsed email address from the token.
     */
    public String getEmailAddressFromToken(String token) {
        String base64Credentials = token.substring("Basic ".length()).trim();
        String credentials = new String(Base64.getDecoder().decode(base64Credentials));
        final String[] values = credentials.split(":", 2);
        return values[0];
    }
}
