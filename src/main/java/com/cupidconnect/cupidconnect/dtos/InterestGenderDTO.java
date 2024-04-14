package com.cupidconnect.cupidconnect.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InterestGenderDTO {

    private Integer id;
    private Integer userDTOId;
    private GenderDTO genderDTO;

}
