package com.cupidconnect.cupidconnect.exceptions;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    public OAuth2LoginSuccessHandler() {
        this.setDefaultTargetUrl("http://localhost:3000/login");
        this.setAlwaysUseDefaultTargetUrl(true);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication instanceof OAuth2AuthenticationToken) {
            HttpSession session = request.getSession(true); // True để tạo session nếu chưa tồn tại
            // Lưu trữ toàn bộ OAuth2AuthenticationToken vào session
            session.setAttribute("OAUTH2_AUTHENTICATION_TOKEN", authentication);

        }

//        this.setAlwaysUseDefaultTargetUrl(true);
//        this.setDefaultTargetUrl("http://localhost:3000/login");
        super.onAuthenticationSuccess(request, response, authentication);
//        response.sendRedirect("/profile");
    }
}
