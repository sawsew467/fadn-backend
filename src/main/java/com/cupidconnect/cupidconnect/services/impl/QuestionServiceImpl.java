package com.cupidconnect.cupidconnect.services.impl;

import com.cupidconnect.cupidconnect.dtos.AnswerDTO;
import com.cupidconnect.cupidconnect.dtos.QuestionDTO;
import com.cupidconnect.cupidconnect.models.AnswerEntity;
import com.cupidconnect.cupidconnect.models.QuestionEntity;
import com.cupidconnect.cupidconnect.repositories.AnswerRepository;
import com.cupidconnect.cupidconnect.repositories.QuestionRepository;
import com.cupidconnect.cupidconnect.services.QuestionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;


    @Override
    public List<QuestionDTO> getAllQuestionsWithAnswers() {
        List<QuestionEntity> questions = questionRepository.findAll();
        return questions.stream().map(question -> {
            List<AnswerDTO> answerDtos = question.getAnswers().stream()
                    .map(answer -> new AnswerDTO(answer.getAnswerText(), answer.getScore(),answer.getMbtiDimension()))
                    .collect(Collectors.toList());

            QuestionDTO dto = new QuestionDTO();
            dto.setQuestionId(question.getQuestionId().toString());
            dto.setQuestionText(question.getQuestionText());
            dto.setAnswerOptions(answerDtos);
            dto.setQuestionType(question.getQuestionType());
            return dto;
        }).collect(Collectors.toList());
    }
    public List<QuestionDTO> getAllMBTIQuestionsWithAnswers() {
        List<QuestionEntity> questions = questionRepository.findByQuestionType("mbti");
        return questions.stream().map(question -> {
            List<AnswerDTO> answerDtos = question.getAnswers().stream()
                    .map(answer -> new AnswerDTO(answer.getAnswerText(), answer.getScore(),answer.getMbtiDimension()))
                    .collect(Collectors.toList());

            QuestionDTO dto = new QuestionDTO();

            dto.setQuestionId(question.getQuestionId().toString());
            dto.setQuestionText(question.getQuestionText());
            dto.setAnswerOptions(answerDtos);
            dto.setQuestionType(question.getQuestionType());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<QuestionEntity> getAllConstructiveQuestions() {
        return questionRepository.findByQuestionType("constructive");
    }



    public QuestionEntity createQuestionWithAnswers(QuestionDTO questionDTO) {
        QuestionEntity question = new QuestionEntity();
        question.setQuestionText(questionDTO.getQuestionText());
        question.setQuestionType(questionDTO.getQuestionType());
        question.setAnswers(questionDTO.getAnswerOptions().stream().map(answerDTO -> {
            AnswerEntity answer = new AnswerEntity();
            answer.setAnswerText(answerDTO.getAnswerText());
            answer.setMbtiDimension(answerDTO.getMbtiDimension());
            answer.setScore(answerDTO.getScore());
            answer.setQuestion(question);
            return answer;
        }).collect(Collectors.toList()));
        return questionRepository.save(question);
    }

    @Override
    public QuestionEntity updateQuestionWithAnswers(Long id, QuestionDTO questionDTO) {
        QuestionEntity question = questionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Question not found"));


        question.setQuestionText(questionDTO.getQuestionText());
        question.setQuestionType(questionDTO.getQuestionType());
        answerRepository.deleteAll(question.getAnswers());
        question.getAnswers().clear();
        List<AnswerEntity> newAnswers = questionDTO.getAnswerOptions().stream().map(answerDTO -> {
            AnswerEntity answer = new AnswerEntity();
            answer.setAnswerText(answerDTO.getAnswerText());
            answer.setMbtiDimension(answerDTO.getMbtiDimension());
            answer.setScore(answerDTO.getScore());
            answer.setQuestion(question); // Set back-reference to question
            return answer;
        }).collect(Collectors.toList());

        question.getAnswers().addAll(newAnswers);

        return questionRepository.save(question);
    }


    public Optional<QuestionEntity> findById(Long id) {
        return questionRepository.findById(id);
    }

    public void deleteQuestion(QuestionEntity question) {
        questionRepository.delete(question);
    }








}
