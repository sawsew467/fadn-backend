package com.cupidconnect.cupidconnect.mappers.impl;

import com.cupidconnect.cupidconnect.dtos.StatusDTO;
import com.cupidconnect.cupidconnect.mappers.Mapper;
import com.cupidconnect.cupidconnect.models.StatusEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class StatusMapperImpl implements Mapper<StatusEntity, StatusDTO> {

    private ModelMapper modelMapper;

    public StatusMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public StatusDTO mapTo(StatusEntity statusEntity) {
        return modelMapper.map(statusEntity, StatusDTO.class);
    }

    @Override
    public StatusEntity mapFrom(StatusDTO statusDTO) {
        return modelMapper.map(statusDTO, StatusEntity.class);
    }

    @Override
    public StatusEntity registerMapFrom(StatusDTO userDTO) {
        return null;
    }

    @Override
    public StatusEntity updateMapFrom(StatusDTO interestGenderDTO) {
        return null;
    }
}
