package com.cupidconnect.cupidconnect.mappers.impl;

import com.cupidconnect.cupidconnect.dtos.ProfileDTO;
import com.cupidconnect.cupidconnect.mappers.Mapper;
import com.cupidconnect.cupidconnect.models.ProfileEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapperImpl implements Mapper<ProfileEntity, ProfileDTO> {
    private ModelMapper modelMapper;

    public ProfileMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ProfileDTO mapTo(ProfileEntity profileEntity) {
        return modelMapper.map(profileEntity, ProfileDTO.class);
    }

    @Override
    public ProfileEntity mapFrom(ProfileDTO profileDTO) {
        return modelMapper.map(profileDTO, ProfileEntity.class);
    }

    @Override
    public ProfileEntity registerMapFrom(ProfileDTO userDTO) {
        return null;
    }
}
