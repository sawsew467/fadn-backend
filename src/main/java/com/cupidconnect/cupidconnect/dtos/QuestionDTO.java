package com.cupidconnect.cupidconnect.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@Data
@Getter
@Setter
@NoArgsConstructor
public class QuestionDTO {
    @JsonProperty("question_id")
    private String questionId;
    @JsonProperty("question_text")
    private String questionText;
    @JsonProperty("question_type")
    private String questionType;
    @JsonProperty("answers")
    private List<AnswerDTO> answerOptions;
}
