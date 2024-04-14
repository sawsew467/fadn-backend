package com.cupidconnect.cupidconnect.repositories;

import com.cupidconnect.cupidconnect.models.RecommendedCareer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommendedCareerRepository extends JpaRepository<RecommendedCareer, Long> {
    List<RecommendedCareer> findByPersonalityType_Id(Long id);
}