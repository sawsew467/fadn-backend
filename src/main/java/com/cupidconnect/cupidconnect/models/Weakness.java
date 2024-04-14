package com.cupidconnect.cupidconnect.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "weaknesses")
@Getter
@Setter
public class Weakness {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String weakness;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personality_type_id", nullable = false)
    @JsonBackReference
    private PersonalityType personalityType;
}