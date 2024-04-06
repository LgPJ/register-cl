package com.cl.registercl.services;

import com.cl.registercl.Dtos.ResponseDto;
import com.cl.registercl.Dtos.UserDto;
import com.cl.registercl.Dtos.UserResponse;
import com.cl.registercl.entities.Phone;
import com.cl.registercl.entities.User;
import com.cl.registercl.repositories.PhoneRepository;
import com.cl.registercl.repositories.UserRepository;
import com.cl.registercl.utils.RegexProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PhoneRepository phoneRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public ResponseDto<List<User>> findAll() {

        ResponseDto<List<User>> response = new ResponseDto();
        response.setMessage("Usuarios");
        response.setData(userRepository.findAll());
        return response;
    }

    public ResponseDto<?> save(UserDto userDto) throws IOException {

        ResponseDto<UserResponse> response = new ResponseDto();

        //Validamos los campos del JSON Request
        if (!userDto.getEmail().matches(new RegexProperties().get("email.regex"))) {
            response.setMessage("formato de correo no valido");
        }

        if (!userDto.getPassword().matches(new RegexProperties().get("password.regex"))) {
            if (response.getMessage() != null) {
                response.setMessage(response.getMessage() + ", formato de password no valido");
            } else {
                response.setMessage("formato de password no valido");
            }
        }

        if (response.getMessage() != null) {
            return response;
        }

        //Obtenemos el user si existe
        Optional<User> userEmail = userRepository.findByEmail(userDto.getEmail());

        if (userEmail.isPresent()) {
            response.setMessage("Correo ya registrado");
            return response;
        }

        //Mapeamos y guardamos el user
        User userSave = userRepository.save(mapUser(userDto));

        //Mapeamos y guardamos los telefonos para ese user
        userDto.getPhones().forEach(phoneDto -> {
            Phone phone = new Phone();
            phone.setId(UUID.randomUUID());
            phone.setCityCode(phoneDto.getCitycode());
            phone.setCountryCode(phoneDto.getCountrycode());
            phone.setNumber(phoneDto.getNumber());
            phone.setUser(userSave);
            phoneRepository.save(phone);
        });

        //Mapeamos la respuesta JSON
        UserResponse userResponse = fromUser(response, userSave);
        response.setData(userResponse);

        return response;

    }

    private User mapUser(UserDto userDto) {
        User user = new User();
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setId(UUID.randomUUID());
        user.setCreated(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        user.setToken(jwtTokenProvider.generateToken(userDto.getName()));
        user.setActive(true);
        return user;
    }

    private UserResponse fromUser(ResponseDto<UserResponse> response, User userSave) {
        response.setMessage("Usuario");
        UserResponse userResponse = new UserResponse();
        userResponse.setId(String.valueOf(userSave.getId()));
        userResponse.setCreated(userSave.getCreated());
        userResponse.setLastLogin(userSave.getLastLogin());
        userResponse.setModified(userSave.getModified());
        userResponse.setToken(userSave.getToken());
        userResponse.setActive(userSave.isActive());
        return userResponse;
    }
}
