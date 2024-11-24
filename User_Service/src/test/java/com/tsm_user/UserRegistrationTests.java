package com.tsm_user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsm_user.controller.UserController;
import com.tsm_user.dto.UserResponse;
import com.tsm_user.entity.User;
import com.tsm_user.exception.UserAlreadyExistsException;
import com.tsm_user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("User Registration Tests")
@WebMvcTest(UserController.class)
class UserRegistrationTests {

    private static final String API_PATH = "/api/v1/users/register";

    private final MockMvc mockMvc;
    @MockBean
    private final UserService userService;

    private final ObjectMapper objectMapper;

    @Autowired
    UserRegistrationTests(MockMvc mockMvc, UserService userService, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.userService = userService;
        this.objectMapper = objectMapper;
    }
    private User createTestUser(String username, String email, String password){
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(password);
        return user;
    }
    @ParameterizedTest
    @CsvSource({
            "mohitrawat, mohitrawat@gmail.com, 12345",
            "someone, someone@gmail.com, pass123"
    })
    @WithMockUser
    @DisplayName("Should successfully register user with valid data")
    void should_RegisterUser_When_ValidUserDataProvided(String username, String email, String password) throws Exception {
        // Arrange
        User user = createTestUser(username, email, password);
        UserResponse userResponse = new UserResponse(user.getId(), user.getUsername());

        given(userService.registerNewUser(any(User.class))).willReturn(userResponse);

        // Act
        var resultActions = mockMvc.perform(MockMvcRequestBuilders.post(API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))
                .with(csrf()));

        // Assert
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value(username));
    }

    @Test
    @WithMockUser
    @DisplayName("should return Conflict when user already Exists")
    void should_ReturnConflict_When_UserAlreadyExists() throws Exception {
        // Arrange
        User user = new User();
        user.setUsername("alreadythereuser");
        user.setEmail("alreadythere@gmail.com");
        user.setPasswordHash("123456");

        given(userService.registerNewUser(any(User.class))).willThrow(new UserAlreadyExistsException("User already exists"));

        // Act
        var resultActions = mockMvc.perform(MockMvcRequestBuilders.post(API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))
                .with(csrf()));

        // Assert
        resultActions.andExpect(status().isConflict())
                .andExpect(jsonPath("$").value("User already exists"));
    }
}
