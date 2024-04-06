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
    private RegexProperties regexProperties;

    public ResponseDto<List<User>> findAll() {

        ResponseDto<List<User>> response = new ResponseDto();
        response.setMessage("Usuarios");
        response.setData(userRepository.findAll());
        return response;
    }

    public ResponseDto<?> save(UserDto userDto) throws IOException {

        ResponseDto<UserResponse> response = new ResponseDto();

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

        Optional<User> userEmail = userRepository.findByEmail(userDto.getEmail());

        if (userEmail.isPresent()) {
            response.setMessage("Correo ya registrado");
            return response;
        }

        User user = new User();
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setId(UUID.randomUUID());
        User userSave = userRepository.save(user);

        userDto.getPhones().forEach(phoneDto -> {
            Phone phone = new Phone();
            phone.setCityCode(phoneDto.getCitycode());
            phone.setCountryCode(phoneDto.getCountrycode());
            phone.setNumber(phoneDto.getNumber());
            phone.setUser(userSave);
            phoneRepository.save(phone); // Asumiendo que tienes un repositorio para Phone
        });

        UserResponse userResponse = fromUser(response, userSave);
        response.setData(userResponse);

        return response;

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
