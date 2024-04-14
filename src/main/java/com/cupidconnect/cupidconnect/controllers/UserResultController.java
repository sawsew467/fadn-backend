package com.cupidconnect.cupidconnect.controllers;

import com.cupidconnect.cupidconnect.models.UserResultEntity;
import com.cupidconnect.cupidconnect.services.UserResultService;
import com.cupidconnect.cupidconnect.services.impl.UserResultServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("${api.prefix}/user-results")
@RequiredArgsConstructor
public class UserResultController {

    private final UserResultServiceImpl userResultService;

    @PostMapping
    public UserResultEntity saveUserResult(@RequestBody UserResultEntity userResult) {
        return userResultService.saveUserResult(userResult);
    }

    @GetMapping("/{userId}")
    public List<UserResultEntity> getUserResultsByUserId(@PathVariable Integer userId) {
        return userResultService.getUserResultsByUserId(userId);
    }
}
