package com.cupidconnect.cupidconnect.services;

import com.cupidconnect.cupidconnect.models.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserEntity updateUser(UserEntity user);
    boolean isExists(Integer id);
    List<UserEntity> findAll();
    Optional<UserEntity> findById(Integer id);

    UserEntity partialUpdate(Integer id, UserEntity userEntity);

    void deleteUser(Integer id);

    boolean isExistsByEmail(String email);

    boolean isPasswordValid(String email, String password);

    Optional<UserEntity> findByEmail(String email);

    Boolean checkPassword(Integer id, String inputPassword);

    boolean checkPhone(String phone);

    boolean checkNickname(String nickname);
}
