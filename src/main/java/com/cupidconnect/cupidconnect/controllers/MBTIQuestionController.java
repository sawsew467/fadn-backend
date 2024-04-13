package com.cupidconnect.cupidconnect.controllers;

import com.cupidconnect.cupidconnect.dtos.QuestionDTO;
import com.cupidconnect.cupidconnect.services.impl.QuestionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/questions")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class MBTIQuestionController {

    private final QuestionServiceImpl questionService;


    @GetMapping("/mbti")
    public ResponseEntity<List<QuestionDTO>> getAllMBTIQuestionsWithAnswers() {
        return ResponseEntity.ok(questionService.getAllMBTIQuestionsWithAnswers());
    }
    @GetMapping("")
    public ResponseEntity<List<QuestionDTO>> getAllQuestionsWithAnswers() {
        return ResponseEntity.ok(questionService.getAllQuestionsWithAnswers());
    }












}