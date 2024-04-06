package com.cl.registercl.services;

import com.cl.registercl.Dtos.ResponseDto;
import com.cl.registercl.Dtos.UserDto;
import com.cl.registercl.Dtos.UserResponse;
import com.cl.registercl.entities.User;
import com.cl.registercl.repositories.UserRepository;
import com.cl.registercl.utils.RegexProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

class UserServiceTest {

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Spy
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RegexProperties regexProperties;

    @Test
    public void testFindAll() {
        // Preparación
        List<User> userList = new ArrayList<>();
        User user = new User();
        user.setName("Luis Garcia");
        user.setEmail("luis.garcia@example.cl");
        userList.add(user);
        doReturn(new ArrayList<>()).when(userRepository).findAll();
        ResponseDto<List<User>> response = userService.findAll();
        assertNotNull(response);
        assertEquals("Usuarios", response.getMessage());
    }

    @Test
    public void testSave() throws IOException {
        // Preparación
        UserDto userDto = new UserDto();
        userDto.setName("John Doe");
        userDto.setEmail("john.doe@example.com");
        userDto.setPassword("password");
        userDto.setPhones(Collections.emptyList());
        when(regexProperties.get("email.regex")).thenReturn("your-email-regex");
        when(regexProperties.get("password.regex")).thenReturn("your-password-regex");
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(UUID.randomUUID());
            user.setCreated(LocalDateTime.now());
            user.setLastLogin(LocalDateTime.now());
            user.setModified(LocalDateTime.now());
            user.setToken("asdfgsdfg");
            user.setActive(true);
            return user;
        });
        ResponseDto<?> response = userService.save(userDto);
        assertNotNull(response);
        assertEquals("formato de correo no valido, formato de password no valido", response.getMessage());
        assertFalse(response.getData() instanceof UserResponse);
    }
}
