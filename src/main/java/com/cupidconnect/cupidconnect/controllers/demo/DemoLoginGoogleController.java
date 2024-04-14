package com.cupidconnect.cupidconnect.controllers.demo;

import com.cupidconnect.cupidconnect.dtos.ExceptionDTO;
import com.cupidconnect.cupidconnect.services.impl.JwtServiceImpl;
import com.cupidconnect.cupidconnect.services.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class DemoLoginGoogleController {

    private final JwtServiceImpl jwtService;
    private final UserServiceImpl userService;

    @GetMapping("/home")
    public ResponseEntity<?> home() {
        return new ResponseEntity<>("Đăng nhập Google<br/><a href=\"/oauth2/authorization/google\">Google</a>", HttpStatus.OK);
    }


    @GetMapping("/profile")
    public ResponseEntity<?> profile(HttpServletRequest request, OAuth2AuthenticationToken authentication) {
        HttpSession session = request.getSession(false); // Trả về null nếu không có session nào tồn tại
//        if (authentication == null || authentication.getPrincipal() == null) {
//            return new ResponseEntity<>("Người dùng chưa đăng nhập.", HttpStatus.FORBIDDEN);
//        }

        // Kiểm tra và sử dụng thông tin từ session nếu cần
        if (session == null) {
            HttpStatus status = HttpStatus.FORBIDDEN;
            ExceptionDTO exceptionDTO = new ExceptionDTO(
                    "User not logging.", // message
                    status.name(), // error
                    status.value() // statusCode
            );
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exceptionDTO);
        }
        OAuth2AuthenticationToken userFromLoginGoogle = (OAuth2AuthenticationToken) session.getAttribute("OAUTH2_AUTHENTICATION_TOKEN"); // Ví dụ lấy thông tin

        // Hủy bỏ session
        session.invalidate();

        return new ResponseEntity<>(userFromLoginGoogle.getPrincipal().getAttributes(), HttpStatus.OK);

    }


}
