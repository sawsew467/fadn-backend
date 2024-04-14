package com.cupidconnect.cupidconnect.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "user_results")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer resultId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "mbti_type", length = 4)
    private String mbtiType;

    @Column(name = "test_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date testDate;


}
