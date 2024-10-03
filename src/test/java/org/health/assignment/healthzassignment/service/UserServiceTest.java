package org.health.assignment.healthzassignment.service;

import org.health.assignment.healthzassignment.converter.ObjectConverter;
import org.health.assignment.healthzassignment.dto.UserDto;
import org.health.assignment.healthzassignment.model.User;
import org.health.assignment.healthzassignment.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private UserDto userDto;

    private User userMock;

    private ObjectConverter objectConverter;

    @BeforeEach
    public void init() {
        objectConverter = Mockito.spy(new ObjectConverter());
    }

    @BeforeEach
    public void setup() {
        userDto = new UserDto();
        userDto.setEmail("dummyuser@gmail.com");
        userDto.setPassword("Test123456");
        userDto.setFirstName("Dummy");
        userDto.setLastName("User");

        userMock = new User();
        userMock.setEmail("dummyuser@gmail.com");
        userMock.setPassword("Test17798");
        userMock.setFirstName("Dummy");
        userMock.setLastName("User");
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveUserDetails_UserAlreadyExists() {
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(userMock));
        assertThrows(RuntimeException.class, () -> userService.saveUserDetails(userDto));
    }
}