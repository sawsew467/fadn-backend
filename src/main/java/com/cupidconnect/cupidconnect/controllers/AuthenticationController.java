package com.cupidconnect.cupidconnect.controllers;

import com.cupidconnect.cupidconnect.dtos.AuthenticationResponseDTO;
import com.cupidconnect.cupidconnect.dtos.UserDTO;
import com.cupidconnect.cupidconnect.dtos.UserLoginDTO;
import com.cupidconnect.cupidconnect.mappers.Mapper;
import com.cupidconnect.cupidconnect.models.UserEntity;
import com.cupidconnect.cupidconnect.services.impl.AuthenticationServiceImpl;
import com.cupidconnect.cupidconnect.services.impl.JwtServiceImpl;
import com.cupidconnect.cupidconnect.services.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationServiceImpl authenticationService;
    private final Mapper<UserEntity, UserDTO> userMapper;
    private final JwtServiceImpl jwtService;
    private final UserServiceImpl userService;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(
            @Valid @RequestBody UserDTO userDTO,
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

        UserEntity userEntity = userMapper.registerMapFrom(userDTO);
        UserEntity savedUserEntity = authenticationService.register(userEntity);
        UserDTO savedUserDTO = userMapper.mapTo(savedUserEntity);
        String token = jwtService.generateToken(savedUserEntity);
//        savedUserDTO.setToken(token);
        AuthenticationResponseDTO responseDTO = new AuthenticationResponseDTO(token);
        // creates
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

        if (!userService.isExistsByEmail(userLoginDTO.getEmail())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        };

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
