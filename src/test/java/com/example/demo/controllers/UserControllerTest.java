package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserController userController;
    private UserRepository userRepository = mock(UserRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    //Inject mock objects as attributes to userController
    @BeforeEach
    void setUp() {
        userController = new UserController();
        TestUtils.injectObjetcs(userController, "userRepository", userRepository);
        TestUtils.injectObjetcs(userController, "cartRepository", cartRepository);
        TestUtils.injectObjetcs(userController, "bCryptPasswordEncoder", encoder);
    }

    @Test
    public void create_user_happy_path(){
        //Stub method from mock, when is called ser static return
        when(encoder.encode("testpassword")).thenReturn("thisIsHashed");
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setUsername("testuser");
        userRequest.setPassword("testpassword");
        userRequest.setConfirmPassword("testpassword");
        final ResponseEntity<User> response = userController.createUser(userRequest);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.getStatusCodeValue());
        User user = response.getBody();
        Assertions.assertNotNull(user);
        Assertions.assertEquals("testuser", user.getUsername());
    }

    @Test
    public void find_user_by_name(){
        when(userRepository.findByUsername("testuser")).thenReturn(dummyUser());
        final ResponseEntity<User> response = userController.findByUserName("testuser");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.getStatusCodeValue());
    }

    private User dummyUser(){
        User user = new User();
        user.setId(1);
        user.setUsername("testuser");
        user.setPassword("testpassword");
        return  user;
    }
}
