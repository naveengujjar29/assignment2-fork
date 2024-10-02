package org.health.assignment.healthzassignment.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.health.assignment.healthzassignment.model.User;
import org.health.assignment.healthzassignment.repository.UserRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;
import java.util.Base64;
import java.util.Optional;

/**
 * This class is to validate the token and get the email address from provided token.
 */
@Service
public class TokenUtil {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final HttpServletRequest request;

    public TokenUtil(UserRepository userRepository, PasswordEncoder passwordEncoder, HttpServletRequest request) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.request = request;
    }

    /**
     * Validate the basic token using the email address and password provided.
     * If user exist with that email address and password is correct then only
     * this method will return true.
     *
     * @return whether token is valid or not.
     */
    public String validateAndGetEmailAddressFromToken() throws AuthenticationException {
        String basicAuthToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (basicAuthToken != null && basicAuthToken.startsWith("Basic ")) {
            String base64Credentials = basicAuthToken.substring("Basic ".length()).trim();
            String credentials = new String(Base64.getDecoder().decode(base64Credentials));
            final String[] values = credentials.split(":", 2);
            String username = values[0];
            String password = values[1];
            Optional<User> user = this.userRepository.findByEmail(username);
            if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
                return values[0]; // return email address
            } else {
                // Either user does not exist or password is invalid.
                throw new AuthenticationException("Invalid credentials");
            }
        }
        throw new AuthenticationException("Invalid credentials");
    }
}
