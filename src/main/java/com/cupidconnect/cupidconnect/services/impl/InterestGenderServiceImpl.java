package com.cupidconnect.cupidconnect.services.impl;

import com.cupidconnect.cupidconnect.models.InterestGenderEntity;
import com.cupidconnect.cupidconnect.repositories.InterestGenderRepository;
import com.cupidconnect.cupidconnect.services.InterestGenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class InterestGenderServiceImpl implements InterestGenderService {

    private final InterestGenderRepository interestGenderRepository;

    @Override
    public InterestGenderEntity saveInterestGender(InterestGenderEntity interestGenderEntity) {
        return interestGenderRepository.save(interestGenderEntity);
    }

    @Override
    public InterestGenderEntity partialUpdate(Integer id, InterestGenderEntity interestGenderEntity) {
        interestGenderEntity.setId(id);
        log.info(String.valueOf(interestGenderEntity));

        return interestGenderRepository.findById(id).map(existingInterestGender -> {
            Optional.ofNullable(interestGenderEntity.getUser()).ifPresent(existingInterestGender::setUser);
            Optional.ofNullable(interestGenderEntity.getGender()).ifPresent(existingInterestGender::setGender);

            return interestGenderRepository.save(existingInterestGender);
        }).orElseThrow(() -> new RuntimeException("InterestGenderEntity does not exist!"));
    }

    @Override
    public void deleteInterestGender(Integer id) {
        interestGenderRepository.deleteById(id);
    }

    @Override
    public Optional<InterestGenderEntity> findById(Integer id) {
        return interestGenderRepository.findById(id);
    }
}
