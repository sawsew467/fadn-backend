package com.cupidconnect.cupidconnect.controllers;

import com.cupidconnect.cupidconnect.dtos.UserDTO;
import com.cupidconnect.cupidconnect.mappers.Mapper;
import com.cupidconnect.cupidconnect.mappers.impl.UserMapperImpl;
import com.cupidconnect.cupidconnect.models.UserEntity;
import com.cupidconnect.cupidconnect.services.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}")
@RequiredArgsConstructor
//@Validated -> Ko dùng annotation này khi sử dụng BindingResult
public class UserController {

    private final UserServiceImpl userService;
    private final Mapper<UserEntity, UserDTO> userMapper;

//    @PostMapping("/users/register")
//    public ResponseEntity<?> createUser(
//            @Valid @RequestBody UserDTO userDTO,
//            BindingResult result
//    ) {
//        if (result.hasErrors()) {
//            List<String> errorMessages = result.getFieldErrors()
//                    .stream()
//                    .map(FieldError::getDefaultMessage)
//                    .toList();
//            // HttpStatus.BAD_REQUEST.value(): mã 400
//            return ResponseEntity.badRequest().body(errorMessages);
//        }
//
//        UserEntity userEntity = userMapper.registerMapFrom(userDTO);
//        UserEntity savedUserEntity = userService.createUser(userEntity);
//        UserDTO savedUserDTO = userMapper.mapTo(savedUserEntity);
//        // create
//        // HTTP 201 CREATED
//        return new ResponseEntity<>(savedUserDTO, HttpStatus.CREATED);
//
//    }

    @GetMapping("/users")
    public List<UserDTO> userList() {
        List<UserEntity> users = userService.findAll();

        return users.stream()
                .map(userMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") Integer id) {
        Optional<UserEntity> foundUser = userService.findById(id);

        return foundUser.map(userEntity -> {
            UserDTO userDTO = userMapper.mapTo(userEntity);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> getUserDetail(@PathVariable("id") Integer id) {
        Optional<UserEntity> foundUser = userService.findById(id);

        return foundUser.map(userEntity -> {
            UserDTO userDTO = userMapper.mapTo(userEntity);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("users/{id}")
    public ResponseEntity<UserDTO> fullUpdateUser(
            @PathVariable("id") Integer id,
            @Valid @RequestBody UserDTO userDTO
    ) {
        if (!userService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userDTO.setId(id);
        UserEntity userEntity = userMapper.mapFrom(userDTO);
        // mặc dù để method trong repository là lưu (save) vào DB nhưng nó cũng full update user đc
        UserEntity savedUserEntity = userService.updateUser(userEntity);
        UserDTO savedUserDTO = userMapper.mapTo(savedUserEntity);
        return new ResponseEntity<>(
                savedUserDTO,
                HttpStatus.OK
        );
    }


    // Lỗi: UserDTO.getGenderDTO()\" is null
    @PatchMapping("users/{id}")
    public ResponseEntity<UserDTO> partialUpdateUser(
            @PathVariable("id") Integer id,
            @RequestBody UserDTO userDTO
    ) {
        if (!userService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // ở đây ko thể set userDTO.setId(id); giống bên fullUpdate đc vì userDTO dc client
        // gửi vào chỉ có vài thuộc tính thôi, còn lại là null nên t phải set id trong userEntity trong
        // service tới DB rồi lấy ra data đc
        UserEntity userEntity = userMapper.mapFrom(userDTO);
        UserEntity savedUserEntity = userService.partialUpdate(id, userEntity);
        UserDTO savedUserDTO = userMapper.mapTo(savedUserEntity);
        return new ResponseEntity<>(
                savedUserDTO,
                HttpStatus.OK
        );
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Integer id) {
        if (!userService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
