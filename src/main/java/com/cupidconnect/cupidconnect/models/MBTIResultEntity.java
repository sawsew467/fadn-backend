package com.cupidconnect.cupidconnect.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "mbti_results")
@Entity
public class MBTIResultEntity {
    @Id
    @Column(length = 4)
    private String mbtiType;

    @Column(nullable = false, columnDefinition = "TEXT")

    private String description;
}
