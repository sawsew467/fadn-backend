package com.cupidconnect.cupidconnect.repositories;

import com.cupidconnect.cupidconnect.models.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
    List<QuestionEntity> findByQuestionType(String questionType);
}
