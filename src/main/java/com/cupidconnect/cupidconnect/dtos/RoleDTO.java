package com.cupidconnect.cupidconnect.dtos;


import com.cupidconnect.cupidconnect.models.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {

    private Long id;
    private String name;
    @JsonIgnore
    private List<UserDTO> userDTOS;

}
