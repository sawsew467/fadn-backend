package com.cupidconnect.cupidconnect.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "answers")
public class AnswerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("answer_id")
    private Long answerId;


    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private QuestionEntity question;

    @JsonProperty("answer_text")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String answerText;

    @JsonProperty("mbti_dimension")
    @Column(length = 4)
    private String mbtiDimension;

    @Column(nullable = false)
    private Integer score;

}
