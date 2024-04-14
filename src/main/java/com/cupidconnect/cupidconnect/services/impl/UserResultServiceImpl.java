package com.cupidconnect.cupidconnect.services.impl;

import com.cupidconnect.cupidconnect.models.UserResultEntity;
import com.cupidconnect.cupidconnect.repositories.UserResultRepository;
import com.cupidconnect.cupidconnect.services.UserResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserResultServiceImpl implements UserResultService {

    private final UserResultRepository userResultRepository;

    @Override
    public UserResultEntity saveUserResult(UserResultEntity userResult) {
        return userResultRepository.save(userResult);
    }

    @Override
    public List<UserResultEntity> getUserResultsByUserId(Integer userId) {
        return userResultRepository.findByUserId(userId);
    }
}
