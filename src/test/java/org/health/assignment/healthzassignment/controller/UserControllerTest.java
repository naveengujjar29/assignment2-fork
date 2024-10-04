package org.health.assignment.healthzassignment.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.health.assignment.healthzassignment.dto.UserDto;
import org.health.assignment.healthzassignment.service.IUserService;
import org.health.assignment.healthzassignment.utils.CommonUtils;
import org.health.assignment.healthzassignment.utils.TokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserService userService;

    @MockBean
    private TokenUtil tokenUtil;

    @MockBean
    private CommonUtils commonUtils;

    @MockBean
    private HttpServletRequest request;

    private UserDto userDto;

    @BeforeEach
    public void setup() {
        userDto = new UserDto();
        userDto.setId(UUID.fromString("d7fbc649-5df2-40e8-8bb1-b7cb8f625a98"));
        userDto.setFirstName("Dummy");
        userDto.setLastName("user");
        userDto.setEmail("Dummyuser@gmail.com");
    }

    @Test
    public void testCreateUser_Success() throws Exception {
        when(userService.saveUserDetails(any(UserDto.class))).thenReturn(userDto);
        String inputJson = "{\"first_name\":\"Dummy\",\"last_name\":\"user\",\"email\":\"Dummyuser@gmail.com\",\"password\":\"Test1234\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.first_name").value("Dummy"))
                .andExpect(jsonPath("$.last_name").value("user"))
                .andExpect(jsonPath("$.email").value("Dummyuser@gmail.com"))
                .andDo(print());
    }

    @Test
    public void testGetSelfUserDetails_Success() throws Exception {
        when(tokenUtil.validateAndGetEmailAddressFromToken()).thenReturn("Dummyuser@gmail.com");
        when(userService.getUserDetails("Dummyuser@gmail.com")).thenReturn(userDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/user/self")
                        .header("Authorization", "Bearer some-valid-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.first_name").value("Dummy"))
                .andExpect(jsonPath("$.last_name").value("user"))
                .andExpect(jsonPath("$.email").value("Dummyuser@gmail.com"))
                .andDo(print());
    }

    @Test
    public void testCreateUser_InvalidQueryParameters() throws Exception {
        when(request.getQueryString()).thenReturn("Query Parameter is not supported.");
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Dummy\",\"lastName\":\"user\",\"email\":\"Dummyuser@gmail.com\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateSelfUserDetails_Success() throws Exception {
        when(tokenUtil.validateAndGetEmailAddressFromToken()).thenReturn("Dummyuser@gmail.com");
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/user/self")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"first_name\":\"Dummy\",\"last_name\":\"user\",\"password\":\"Dummy62345\"}"))
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(userService, times(1)).updateUserDetails(eq("Dummyuser@gmail.com"), any(UserDto.class));
    }
}
