package com.cupidconnect.cupidconnect.repositories;

import com.cupidconnect.cupidconnect.models.Strength;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StrengthRepository extends JpaRepository<Strength, Long> {
    List<Strength> findByPersonalityType_Id(Long personalityTypeId);
}
