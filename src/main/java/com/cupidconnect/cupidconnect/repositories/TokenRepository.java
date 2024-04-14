package com.cupidconnect.cupidconnect.repositories;

import com.cupidconnect.cupidconnect.models.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<TokenEntity, Integer> {
}
