package com.cupidconnect.cupidconnect.repositories;

import com.cupidconnect.cupidconnect.models.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RelationshipRepository extends JpaRepository<Relationship, Long> {
    Relationship findByPersonalityTypeId(Long personalityTypeId);

}