package com.cupidconnect.cupidconnect.mappers.impl;

import com.cupidconnect.cupidconnect.dtos.GenderDTO;
import com.cupidconnect.cupidconnect.dtos.InterestGenderDTO;
import com.cupidconnect.cupidconnect.dtos.UserDTO;
import com.cupidconnect.cupidconnect.mappers.Mapper;
import com.cupidconnect.cupidconnect.models.GenderEntity;
import com.cupidconnect.cupidconnect.models.InterestGenderEntity;
import com.cupidconnect.cupidconnect.models.UserEntity;
import com.cupidconnect.cupidconnect.services.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class InterestGenderMapperImpl implements Mapper<InterestGenderEntity, InterestGenderDTO> {
    private final ModelMapper modelMapper;
    private final UserServiceImpl userService;
    private final Mapper<UserEntity, UserDTO> userMapper;
    private final Mapper<GenderEntity, GenderDTO> genderMapper;

    @Override
    public InterestGenderDTO mapTo(InterestGenderEntity interestGenderEntity) {
        return modelMapper.map(interestGenderEntity, InterestGenderDTO.class);
    }

    @Override
    public InterestGenderEntity mapFrom(InterestGenderDTO interestGenderDTO) {

        Optional<UserEntity> foundUser = userService.findById(interestGenderDTO.getUserDTOId());

        UserDTO userDTO = foundUser.map(userEntity -> userMapper.mapTo(userEntity))
                .orElseThrow(() -> new RuntimeException("User not found"));
        System.out.println(userDTO);

//        InterestGenderEntity interestGenderEntity = modelMapper.map(interestGenderDTO, InterestGenderEntity.class);
        InterestGenderEntity interestGenderEntity = new InterestGenderEntity();
        interestGenderEntity.setUser(userMapper.mapFrom(userDTO));
        interestGenderEntity.setGender(genderMapper.mapFrom(interestGenderDTO.getGenderDTO()));
        return interestGenderEntity;
    }

    public InterestGenderEntity updateMapFrom(InterestGenderDTO interestGenderDTO) {

        Optional<UserEntity> foundUser = userService.findById(interestGenderDTO.getUserDTOId());

        UserDTO userDTO = foundUser.map(userEntity -> userMapper.mapTo(userEntity))
                .orElseThrow(() -> new RuntimeException("User not found"));
        System.out.println(userDTO);

//        InterestGenderEntity interestGenderEntity = modelMapper.map(interestGenderDTO, InterestGenderEntity.class);
        InterestGenderEntity interestGenderEntity = new InterestGenderEntity();
//        interestGenderEntity.setUser(userMapper.mapFrom(userDTO));
        interestGenderEntity.setGender(genderMapper.mapFrom(interestGenderDTO.getGenderDTO()));
        return interestGenderEntity;
    }

    @Override
    public InterestGenderEntity registerMapFrom(InterestGenderDTO userDTO) {
        return null;
    }
}
