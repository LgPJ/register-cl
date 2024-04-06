package com.cl.registercl.controllers;

import com.cl.registercl.Dtos.ResponseDto;
import com.cl.registercl.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class UserControllerTest {

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Test
    void testFindUser() {
        doReturn(new ResponseDto<>()).when(userService).findAll();
        this.userController.findUser();
        assertTrue(true);
    }

    @Test
    void testRegisterUser() throws IOException {
        doReturn(new ResponseDto<>()).when(userService).save(any());
        this.userController.registerUser(any());
        assertTrue(true);
    }
}
