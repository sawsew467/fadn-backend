package com.cupidconnect.cupidconnect.repositories;

import com.cupidconnect.cupidconnect.models.Weakness;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeaknessRepository extends JpaRepository<Weakness, Long> {
    List<Weakness> findByPersonalityType_Id(Long id);
}
