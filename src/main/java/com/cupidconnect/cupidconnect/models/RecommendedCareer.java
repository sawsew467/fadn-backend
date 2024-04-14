package com.cupidconnect.cupidconnect.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "recommended_careers")
public class RecommendedCareer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String career;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personality_type_id", nullable = false)
    @JsonBackReference
    private PersonalityType personalityType;
}
