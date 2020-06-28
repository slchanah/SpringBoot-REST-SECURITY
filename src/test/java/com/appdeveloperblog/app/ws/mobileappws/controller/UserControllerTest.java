package com.appdeveloperblog.app.ws.mobileappws.controller;

import com.appdeveloperblog.app.ws.mobileappws.dto.UserDto;
import com.appdeveloperblog.app.ws.mobileappws.service.UserService;
import com.appdeveloperblog.app.ws.mobileappws.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserServiceImpl userService;

    UserDto userDto;

    final String USER_ID = "dfbv887dvf";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getUser() {

    }
}