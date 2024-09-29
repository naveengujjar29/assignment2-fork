package org.health.assignment.healthzassignment.service;

import jakarta.transaction.Transactional;
import org.health.assignment.healthzassignment.converter.ObjectConverter;
import org.health.assignment.healthzassignment.dto.UserDto;
import org.health.assignment.healthzassignment.exception.BadRequestException;
import org.health.assignment.healthzassignment.model.User;
import org.health.assignment.healthzassignment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectConverter objectConverter;

    @Override
    public UserDto saveUserDetails(UserDto userDto) {
        // Check for the required fields before proceeding further.
        if (userDto.getFirstName() == null || userDto.getLastName() == null ||
                userDto.getEmail() == null || userDto.getPassword() == null) {
            throw new BadRequestException("Required fields have not been provided.");
        }

        Optional<User> existingUser = this.userRepository.findByEmail(userDto.getEmail());
        if (existingUser.isPresent()) {
            throw new BadRequestException("User already exist with this email id.");
        }
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = (User) objectConverter.convert(userDto, User.class);
        User savedUser = userRepository.saveAndFlush(user);
        return (UserDto) this.objectConverter.convert(savedUser, UserDto.class);
    }

    @Override
    public UserDto getUserDetails(String emailId) {
        Optional<User> existingUser = this.userRepository.findByEmail(emailId);
        return (UserDto) this.objectConverter.convert(existingUser.get(), UserDto.class);
    }

    @Override
    public UserDto updateUserDetails(String tokenEmailAddress, UserDto userDto) {
        if (userDto.getEmail() != null || userDto.getAccountCreated() != null || userDto.getAccountUpdated() != null) {
            throw new BadRequestException("User is trying to update non-allowed fields.");
        }
        Optional<User> existingUser = this.userRepository.findByEmail(tokenEmailAddress);
        if (existingUser.isPresent()) {
            existingUser.get().setFirstName(userDto.getFirstName());
            existingUser.get().setLastName(userDto.getLastName());
            existingUser.get().setPassword(passwordEncoder.encode(userDto.getPassword()));
            User savedUser = this.userRepository.saveAndFlush(existingUser.get());
            return (UserDto) this.objectConverter.convert(savedUser, UserDto.class);
        }
        throw new BadRequestException("user does not exist with this email id.");
    }
}
