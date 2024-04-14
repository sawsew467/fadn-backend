package com.cupidconnect.cupidconnect.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // là một đối tượng trong cơ sở dữ liệu
@Table(name = "tokens")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "token", unique = true)
    private String token;

    @Column(name = "token_type", unique = true)
    private String tokenType;

    @Column(name = "revoked", unique = true)
    private Integer revoked;

    @Column(name = "expired", unique = true)
    private Integer expired;



}
