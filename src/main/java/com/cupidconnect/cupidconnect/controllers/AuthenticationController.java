package com.cupidconnect.cupidconnect.controllers;

import com.cupidconnect.cupidconnect.dtos.*;
import com.cupidconnect.cupidconnect.mappers.Mapper;
import com.cupidconnect.cupidconnect.models.InterestGenderEntity;
import com.cupidconnect.cupidconnect.models.UserEntity;
import com.cupidconnect.cupidconnect.services.impl.AuthenticationServiceImpl;
import com.cupidconnect.cupidconnect.services.impl.InterestGenderServiceImpl;
import com.cupidconnect.cupidconnect.services.impl.JwtServiceImpl;
import com.cupidconnect.cupidconnect.services.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/")
@Slf4j
public class AuthenticationController {

    private final AuthenticationServiceImpl authenticationService;
    private final Mapper<UserEntity, UserDTO> userMapper;
    private final JwtServiceImpl jwtService;
    private final UserServiceImpl userService;
    private final Mapper<InterestGenderEntity, InterestGenderDTO> interestGenderMapper;
    private final InterestGenderServiceImpl interestGenderService;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(
            @Valid @RequestBody UserDataDTO userDataDTO,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            // HttpStatus.BAD_REQUEST.value(): mã 400
            return ResponseEntity.badRequest().body(errorMessages);
        }

        if (userService.isExistsByEmail(userDataDTO.getUserDTO().getEmail())) {
            HttpStatus status = HttpStatus.ALREADY_REPORTED; // 208
            ExceptionDTO exceptionDTO = new ExceptionDTO(
                    "Your email is already registered.", // message
                    status.name(), // error
                    status.value() // statusCode
            );
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(exceptionDTO);
        }

        // save user
//        UserEntity userEntity = userMapper.mapFrom(userDataDTO.getUserDTO());
        UserEntity userEntity = userMapper.registerMapFrom(userDataDTO.getUserDTO());
        UserEntity savedUserEntity = authenticationService.register(userEntity);
        UserDTO savedUserDTO = userMapper.mapTo(savedUserEntity);
        String token = jwtService.generateToken(savedUserEntity);
//        savedUserDTO.setToken(token);

        // save user_interest_gender
        userDataDTO.getInterestGenderDTO().setUserDTOId(savedUserDTO.getId());
        InterestGenderEntity interestGenderEntity = interestGenderMapper.mapFrom(userDataDTO.getInterestGenderDTO());
        interestGenderService.saveInterestGender(interestGenderEntity);

        AuthenticationResponseDTO responseDTO = new AuthenticationResponseDTO(token, HttpStatus.CREATED.value());
        // HTTP 201 CREATED
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody UserLoginDTO userLoginDTO,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            // HttpStatus.BAD_REQUEST.value(): mã 400
            return ResponseEntity.badRequest().body(errorMessages);
        }

        // check email not exist
        if (!userService.isExistsByEmail(userLoginDTO.getEmail())) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            ExceptionDTO exceptionDTO = new ExceptionDTO(
                    "User with the provided email not found.", // message
                    status.name(), // status
                    status.value() // statusCode
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionDTO);
        };

        // check password incorrect
        if (!userService.isPasswordValid(userLoginDTO.getEmail(), userLoginDTO.getPassword())) {
            HttpStatus status = HttpStatus.UNAUTHORIZED;
            ExceptionDTO exceptionDTO = new ExceptionDTO(
                    "Incorrect password.",
                    status.name(),
                    status.value()
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionDTO);
        }

        AuthenticationResponseDTO responseDTO = authenticationService.authenticate(userLoginDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @GetMapping("/get-me")
    public ResponseEntity<UserDTO> getMe(@RequestHeader(value = "Authorization") String authToken) {
        String token = authToken.substring("Bearer ".length());
        String userId = jwtService.extractUserId(token);
        Optional<UserEntity> foundUser = userService.findById(Integer.valueOf(userId));

        return foundUser.map(userEntity -> {
            UserDTO userDTO = userMapper.mapTo(userEntity);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
