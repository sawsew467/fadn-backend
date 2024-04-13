package com.cupidconnect.cupidconnect.services;

import com.cupidconnect.cupidconnect.models.UserEntity;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

public interface JwtService {
    String extractUsername(String token);
    boolean isValid(String token, UserDetails user);
    boolean isTokenExpired(String token);
    Date extractExpiration(String token);
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
    String generateToken(UserEntity user);
    SecretKey getSigninKey();
}
