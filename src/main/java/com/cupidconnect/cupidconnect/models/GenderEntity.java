package com.cupidconnect.cupidconnect.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity // là một đối tượng trong cơ sở dữ liệu
@Table(name = "genders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", unique = true)
    private String name;

//    @OneToMany(mappedBy = "gender")
//    private List<UserEntity> userEntities;

}
