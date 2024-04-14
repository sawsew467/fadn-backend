package com.cupidconnect.cupidconnect.repositories;

import com.cupidconnect.cupidconnect.models.UserResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserResultRepository extends JpaRepository<UserResultEntity, Integer> {
    List<UserResultEntity> findByUserId(Integer userId);

}
