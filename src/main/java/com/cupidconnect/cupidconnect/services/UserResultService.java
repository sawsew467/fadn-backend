package com.cupidconnect.cupidconnect.services;

import com.cupidconnect.cupidconnect.models.UserResultEntity;

import java.util.List;

public interface UserResultService {
    public UserResultEntity saveUserResult(UserResultEntity userResult) ;


    public List<UserResultEntity> getUserResultsByUserId(Integer userId) ;
}
