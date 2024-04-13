package com.cupidconnect.cupidconnect.services.impl;

import com.cupidconnect.cupidconnect.dtos.AuthenticationResponseDTO;
import com.cupidconnect.cupidconnect.dtos.UserLoginDTO;
import com.cupidconnect.cupidconnect.models.UserEntity;
import com.cupidconnect.cupidconnect.repositories.UserRepository;
import com.cupidconnect.cupidconnect.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtServiceImpl jwtService;

    @Override
    public UserEntity register(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public AuthenticationResponseDTO authenticate(UserLoginDTO userLoginDTO) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    userLoginDTO.getEmail(),
                    userLoginDTO.getPassword()
            )
        );

        UserEntity userEntity = userRepository.findByEmail(userLoginDTO.getEmail())
                .orElseThrow();
        String token = jwtService.generateToken(userEntity);
        AuthenticationResponseDTO responseDTO = new AuthenticationResponseDTO(token);

        return responseDTO;
    }
}
