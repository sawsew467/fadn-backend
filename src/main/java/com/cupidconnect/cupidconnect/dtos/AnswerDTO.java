package com.cupidconnect.cupidconnect.dtos;

import lombok.*;

@AllArgsConstructor
@Data
@Getter
@Setter
@NoArgsConstructor
public class AnswerDTO {
    private String answerText;
    private int score;
    private String mbtiDimension;

}
