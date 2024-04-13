package com.cupidconnect.cupidconnect.repositories;

import com.cupidconnect.cupidconnect.models.FamousPersonality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FamousPersonalityRepository extends JpaRepository<FamousPersonality, Long> {
    List<FamousPersonality> findByPersonalityType_Id(Long id);
}
