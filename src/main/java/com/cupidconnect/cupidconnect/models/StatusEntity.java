package com.cupidconnect.cupidconnect.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "status")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class StatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

}
