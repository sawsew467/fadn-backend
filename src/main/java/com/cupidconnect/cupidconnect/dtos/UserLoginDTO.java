package com.cupidconnect.cupidconnect.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO { // 17 trên 21

    @NotNull(message = "Gender name is required !")
    private String email;

    @NotNull(message = "Password is required !")
    private String password;
}
