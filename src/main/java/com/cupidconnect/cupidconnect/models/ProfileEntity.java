package com.cupidconnect.cupidconnect.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // là một đối tượng trong cơ sở dữ liệu
@Table(name = "profiles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "intro")
    private String intro;

    @Column(name = "hobbies")
    private String hobbies;

    @Column(name = "work_at")
    private String workAt;


}
