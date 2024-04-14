package com.cupidconnect.cupidconnect.services;

import com.cupidconnect.cupidconnect.models.InterestGenderEntity;

import java.util.Optional;

public interface InterestGenderService {

    InterestGenderEntity saveInterestGender(InterestGenderEntity interestGenderEntity);

    InterestGenderEntity partialUpdate(Integer id, InterestGenderEntity interestGenderEntity);

    void deleteInterestGender(Integer id);

    Optional<InterestGenderEntity> findById(Integer id);

}
