package com.cupidconnect.cupidconnect.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
public class EmailDTO {

    private String recipient;
    private String subject;
    private String message;
    private MultipartFile attachment;

}
