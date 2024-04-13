package com.cupidconnect.cupidconnect.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "strengths")
@Getter
@Setter
public class Strength {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String strength;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personality_type_id", nullable = false)
    @JsonBackReference
    private PersonalityType personalityType;
}
