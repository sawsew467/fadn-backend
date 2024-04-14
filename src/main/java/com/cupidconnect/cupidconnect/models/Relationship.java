package com.cupidconnect.cupidconnect.models;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "relationships")
@Getter
@Setter
public class Relationship {
    @Id
    @Column(name = "personality_type_id")
    private Long personalityTypeId;

    private String description;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personality_type_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonBackReference
    private PersonalityType personalityType;
}
