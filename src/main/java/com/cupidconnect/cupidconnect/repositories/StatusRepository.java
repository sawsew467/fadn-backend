package com.cupidconnect.cupidconnect.repositories;

import com.cupidconnect.cupidconnect.models.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<StatusEntity, Long> {
}
