package com.cupidconnect.cupidconnect.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "personality_types")
@Getter
@Setter
public class PersonalityType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;


    @OneToMany(mappedBy = "personalityType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Strength> strengths;

    @OneToMany(mappedBy = "personalityType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Weakness> weaknesses;

    @OneToMany(mappedBy = "personalityType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<FamousPersonality> famousPersonalities;

    @OneToMany(mappedBy = "personalityType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<RecommendedCareer> recommendedCareers;

    @OneToOne(mappedBy = "personalityType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Relationship relationship;
}
