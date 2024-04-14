package com.cupidconnect.cupidconnect.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionDTO {

    private String message;
    private String type;
    private int statusCode;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time = LocalDateTime.now();

    public ExceptionDTO(String message, String type, int statusCode) {
        this.message = message;
        this.type = type;
        this.statusCode = statusCode;
    }
}
