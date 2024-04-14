package com.cupidconnect.cupidconnect.repositories;

import com.cupidconnect.cupidconnect.models.PersonalityType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonalityTypeRepository extends JpaRepository<PersonalityType, Long> {
}
