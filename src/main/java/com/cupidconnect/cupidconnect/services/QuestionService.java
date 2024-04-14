package com.cupidconnect.cupidconnect.services;

import com.cupidconnect.cupidconnect.dtos.QuestionDTO;
import com.cupidconnect.cupidconnect.models.QuestionEntity;

import java.util.List;
import java.util.Optional;

public interface QuestionService {


    public List<QuestionDTO> getAllQuestionsWithAnswers();

    public List<QuestionEntity> getAllConstructiveQuestions();

    public QuestionEntity createQuestionWithAnswers(QuestionDTO questionDTO);

    public Optional<QuestionEntity> findById(Long id);

    public QuestionEntity updateQuestionWithAnswers(Long id, QuestionDTO questionDTO);
        public void deleteQuestion(QuestionEntity question);
    }
