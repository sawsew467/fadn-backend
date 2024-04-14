package com.cupidconnect.cupidconnect.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // là một đối tượng trong cơ sở dữ liệu
@Table(name = "blocks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userId;

    @ManyToOne
    @JoinColumn(name = "user_id_blocked")
    private UserEntity userBlocked;

}
