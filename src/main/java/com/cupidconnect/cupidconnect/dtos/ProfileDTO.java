package com.cupidconnect.cupidconnect.dtos;

import jakarta.persistence.Column;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDTO {

    private Integer id;
    private String avatar;
    private String intro;
    private String hobbies;
    private String workAt;
    private Float mbtiMark;

}
