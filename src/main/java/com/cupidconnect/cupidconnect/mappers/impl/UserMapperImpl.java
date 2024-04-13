package com.cupidconnect.cupidconnect.mappers.impl;

import com.cupidconnect.cupidconnect.dtos.GenderDTO;
import com.cupidconnect.cupidconnect.dtos.RoleDTO;
import com.cupidconnect.cupidconnect.dtos.UserDTO;
import com.cupidconnect.cupidconnect.mappers.Mapper;
import com.cupidconnect.cupidconnect.models.GenderEntity;
import com.cupidconnect.cupidconnect.models.RoleEntity;
import com.cupidconnect.cupidconnect.models.UserEntity;
import com.cupidconnect.cupidconnect.repositories.GenderRepository;
import com.cupidconnect.cupidconnect.repositories.RoleRepository;
import com.cupidconnect.cupidconnect.services.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Component
@AllArgsConstructor
public class UserMapperImpl implements Mapper<UserEntity, UserDTO> {

    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;
    private final RoleMapperImpl roleMapper;
    private final GenderRepository genderRepository;
    private final GenderMapperImpl genderMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDTO mapTo(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDTO.class);
    }

    @Override
    public UserEntity mapFrom(UserDTO userDTO) {
        return modelMapper.map(userDTO, UserEntity.class);
    }

    @Override
    public UserEntity registerMapFrom(UserDTO userDTO) {
        System.out.println(userDTO);

        // hàm partialUpdateUser ko sử dụng đc 2 hàm dưới vì bị null
        // chỉ dùng cho việc register user
        if (userDTO.getGenderDTO() != null) {
            userDTO.setGenderDTO(findGenderDTO(userDTO.getGenderDTO().getId()));
        }
        if (userDTO.getRoleDTO() != null) {
            userDTO.setRoleDTO(findRoleDTO(userDTO.getRoleDTO().getId()));
        }

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return modelMapper.map(userDTO, UserEntity.class);
    }

    private RoleDTO findRoleDTO(Long id) {
        Optional<RoleEntity> roleEntityOptional = roleRepository.findById(id);
//        RoleDTO roleDTO = new RoleDTO();
        AtomicReference<RoleDTO> roleDTO = new AtomicReference<>(new RoleDTO());
        roleEntityOptional.ifPresent(roleEntity -> {
            RoleDTO rDTO = roleMapper.mapTo(roleEntity);
            roleDTO.set(rDTO);
//            roleDTO.setId(rDTO.getId());
//            roleDTO.setName(rDTO.getName());
        });

        return roleDTO.get();
    }

    private GenderDTO findGenderDTO(Integer id) {
        Optional<GenderEntity> genderEntityOptional = genderRepository.findById(id);
//        GenderDTO genderDTO = new GenderDTO();
        AtomicReference<GenderDTO> genderDTO = new AtomicReference<>(new GenderDTO());
        genderEntityOptional.ifPresent(genderEntity -> {
            GenderDTO gDTO = genderMapper.mapTo(genderEntity);
            genderDTO.set(gDTO);
//            genderDTO.setId(gDTO.getId());
//            genderDTO.setName(gDTO.getName());
        });

        return genderDTO.get();
    }

}
