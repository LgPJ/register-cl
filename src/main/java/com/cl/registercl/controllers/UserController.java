package com.cl.registercl.controllers;

import com.cl.registercl.Dtos.ResponseDto;
import com.cl.registercl.Dtos.UserDto;
import com.cl.registercl.entities.User;
import com.cl.registercl.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/find")
    public ResponseEntity<ResponseDto<List<User>>> findUser() {

        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<ResponseDto<?>> registerUser(@RequestBody UserDto userDto) throws IOException {
        ResponseDto<?> response = userService.save(userDto);

        if (response.getData() == null) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
}
