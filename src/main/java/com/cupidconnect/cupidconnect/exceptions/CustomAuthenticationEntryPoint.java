package com.cupidconnect.cupidconnect.exceptions;

import com.cupidconnect.cupidconnect.dtos.ExceptionDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

    public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    public CustomAuthenticationEntryPoint() {
    }

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException authenticationException) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        ExceptionDTO exceptionDTO = new ExceptionDTO(
                "You need to login first in order to perform this action", // message
                status.name(), // error
                status.value() // statusCode
        );

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), exceptionDTO);
    }
}
