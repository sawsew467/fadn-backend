package com.cupidconnect.cupidconnect.services;

import com.cupidconnect.cupidconnect.dtos.AuthenticationResponseDTO;
import com.cupidconnect.cupidconnect.dtos.UserLoginDTO;
import com.cupidconnect.cupidconnect.models.UserEntity;

public interface AuthenticationService {
    UserEntity register(UserEntity user);

    AuthenticationResponseDTO authenticate(UserLoginDTO userLoginDTO);
}
