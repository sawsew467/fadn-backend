package com.cupidconnect.cupidconnect.mappers.impl;

import com.cupidconnect.cupidconnect.dtos.GenderDTO;
import com.cupidconnect.cupidconnect.mappers.Mapper;
import com.cupidconnect.cupidconnect.models.GenderEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class GenderMapperImpl implements Mapper<GenderEntity, GenderDTO> {
    private ModelMapper modelMapper;

    public GenderMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public GenderDTO mapTo(GenderEntity genderEntity) {
        return modelMapper.map(genderEntity, GenderDTO.class);
    }

    @Override
    public GenderEntity mapFrom(GenderDTO genderDTO) {
        return modelMapper.map(genderDTO, GenderEntity.class);
    }

    @Override
    public GenderEntity registerMapFrom(GenderDTO userDTO) {
        return null;
    }
}
