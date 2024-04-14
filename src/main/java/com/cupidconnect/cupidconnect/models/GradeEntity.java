package com.cupidconnect.cupidconnect.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // là một đối tượng trong cơ sở dữ liệu
@Table(name = "grades")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "user_id_received")
    private UserEntity userReceived;

    @Column(name = "grade", nullable = false)
    private int grade;


}
