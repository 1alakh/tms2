package com.tsm_authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsm_authentication.controller.AuthController;
import com.tsm_authentication.dto.JsonWebTokenResponse;
import com.tsm_authentication.dto.LoginRequest;
import com.tsm_authentication.exception.WrongCredentialException;
import com.tsm_authentication.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
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

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("User Login Tests")
@WebMvcTest(AuthController.class)
class UserLoginTests {
    private static final String API_PATH = "/api/v1/auth/login";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;


    private LoginRequest createLoginTest(String username, String password) {
        return new LoginRequest(username, password);
    }

    @ParameterizedTest
    @CsvSource({
            "employee1, 12345, token1",
            "manager1, pass123, token2"
    })
    @WithMockUser
    @DisplayName("Should successfully login and get jwt token")
    void should_login_user_When_ValidUserDataProvided(String username, String password, String token) throws Exception {
        // Arrange
        LoginRequest loginRequest = createLoginTest(username, password);
        Date fixedDate = new Date(0); // Use a fixed date for consistent test results
        JsonWebTokenResponse jwtResponse = new JsonWebTokenResponse(token, fixedDate, username, "12");

        given(authService.loginUser(any(LoginRequest.class), any(HttpServletResponse.class))).willReturn(jwtResponse);

        //  Act
        var resultActions = mockMvc.perform(MockMvcRequestBuilders.post(API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest))
                .with(csrf()));

        // Assert
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token));
    }

    @Test
    @WithMockUser
    @DisplayName("should return Incorrect username or password on if password is wrong")
    void should_Return_BadRequest_When_WrongCredentialsProvided() throws Exception {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("employee1", "wrongpassword");

        given(authService.loginUser(any(LoginRequest.class), any(HttpServletResponse.class))).willThrow(new WrongCredentialException("Incorrect username or password"));

        // Act
        var resultActions = mockMvc.perform(MockMvcRequestBuilders.post(API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest))
                .with(csrf()));

        // Assert
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Incorrect username or password"));
    }
}
