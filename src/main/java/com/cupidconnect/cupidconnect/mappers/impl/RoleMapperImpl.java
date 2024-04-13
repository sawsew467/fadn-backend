package com.cupidconnect.cupidconnect.mappers.impl;

import com.cupidconnect.cupidconnect.dtos.RoleDTO;
import com.cupidconnect.cupidconnect.mappers.Mapper;
import com.cupidconnect.cupidconnect.models.RoleEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RoleMapperImpl implements Mapper<RoleEntity, RoleDTO> {

    private ModelMapper modelMapper;

    public RoleMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    @Override
    public RoleDTO mapTo(RoleEntity roleEntity) {
        return modelMapper.map(roleEntity, RoleDTO.class);
    }

    @Override
    public RoleEntity mapFrom(RoleDTO roleDTO) {
        return modelMapper.map(roleDTO, RoleEntity.class);
    }

    @Override
    public RoleEntity registerMapFrom(RoleDTO userDTO) {
        return null;
    }
}
