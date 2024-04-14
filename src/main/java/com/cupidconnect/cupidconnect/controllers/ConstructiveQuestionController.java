package com.cupidconnect.cupidconnect.controllers;

import com.cupidconnect.cupidconnect.dtos.QuestionDTO;
import com.cupidconnect.cupidconnect.models.QuestionEntity;
import com.cupidconnect.cupidconnect.response.ApiResponse;
import com.cupidconnect.cupidconnect.services.impl.QuestionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/questions")
@RequiredArgsConstructor
public class ConstructiveQuestionController {
    private final QuestionServiceImpl questionService;

    @GetMapping("/constructive")
    public ResponseEntity<List<QuestionEntity>> getAllConstructiveQuestions() {
        return ResponseEntity.ok(questionService.getAllConstructiveQuestions());
    }

    @PostMapping("/constructive")
    public ResponseEntity<QuestionEntity> createQuestionWithAnswers(@RequestBody QuestionDTO questionDTO) {
        QuestionEntity savedQuestion = questionService.createQuestionWithAnswers(questionDTO);
        return new ResponseEntity<>(savedQuestion, HttpStatus.CREATED);
    }


    @PutMapping("/constructive/{id}")
    public ResponseEntity<QuestionEntity> updateConstructiveQuestion(@PathVariable Long id, @RequestBody QuestionDTO questionDTO) {
        QuestionEntity question = questionService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Question not found with id " + id));
        QuestionEntity savedQuestion = questionService.updateQuestionWithAnswers(id,questionDTO);
        return new ResponseEntity<>(savedQuestion, HttpStatus.CREATED);
    }

    @DeleteMapping("/constructive/{id}")
    public ResponseEntity<ApiResponse> deleteConstructiveQuestion(@PathVariable Long id) {
        QuestionEntity question = questionService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Question not found"));

        questionService.deleteQuestion(question);

        ApiResponse response = new ApiResponse("Success", "Question deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }





}
