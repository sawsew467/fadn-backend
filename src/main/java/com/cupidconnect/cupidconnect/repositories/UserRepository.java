package com.cupidconnect.cupidconnect.repositories;

import com.cupidconnect.cupidconnect.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepository
        extends JpaRepository<UserEntity, Integer>, PagingAndSortingRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);

}
