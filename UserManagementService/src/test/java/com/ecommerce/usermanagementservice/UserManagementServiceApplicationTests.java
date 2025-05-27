package com.ecommerce.usermanagementservice;

import com.ecommerce.usermanagementservice.controllers.UserController;
import com.ecommerce.usermanagementservice.dtos.LoginRequestDto;
import com.ecommerce.usermanagementservice.dtos.LoginResponseDto;
import com.ecommerce.usermanagementservice.dtos.SignUpRequestDto;
import com.ecommerce.usermanagementservice.dtos.SignUpResponseDto;
import com.ecommerce.usermanagementservice.models.User;
import com.ecommerce.usermanagementservice.services.IUserService;
import org.antlr.v4.runtime.misc.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest(UserController.class)
class UserManagementServiceApplicationTests {
    @MockBean
    private IUserService userService;

    @Autowired
    private UserController userController;


    // Add test for signup
    @Test
    public void Test_SignUp() throws Exception {
        // Create a mock User
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("testuser");
        mockUser.setEmail("testuser@email.com");
        mockUser.setFullName("Test User");

        when(userService.signup(any(User.class))).thenReturn(mockUser);

        // act
        // Create a mock SignUpRequestDto
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto();
        signUpRequestDto.setUsername("testuser");
        signUpRequestDto.setPassword("password");
        signUpRequestDto.setEmail("testuser@email.com");
        signUpRequestDto.setFullName("Test User");
        ResponseEntity<SignUpResponseDto> response = userController.signUp(signUpRequestDto);

        // assert
        assertNotNull(response);
        assertEquals("testuser", response.getBody().getUsername());
        assertEquals("testuser@email.com", response.getBody().getEmail());
        assertEquals("Test User", response.getBody().getFullName());
        verify(userService, times(1)).signup(any(User.class));
    }

    // Add test for login
    @Test
    public void Test_Login() throws Exception {
        // mock response
        MultiValueMap<String, String> headers= new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.SET_COOKIE, "qwertyuiop");
        User mockUser = new User();
        mockUser.setId(1L);
        when(userService.login(any(String.class), any(String.class))).thenReturn(new Pair<>(mockUser, headers));

        // act
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setUsername("abhijeet");
        loginRequestDto.setPassword("password");
        ResponseEntity<LoginResponseDto> response = userController.login(loginRequestDto);

        //assert
        assertNotNull(response);
        assertEquals("Login successful", response.getBody().getMessage());
        verify(userService, times(1)).login("abhijeet", "password");
    }
}
