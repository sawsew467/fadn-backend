package com.cupidconnect.cupidconnect.dtos;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDataDTO {

    private UserDTO userDTO;
    private InterestGenderDTO interestGenderDTO;

}
