package com.cupidconnect.cupidconnect.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity // là một đối tượng trong cơ sở dữ liệu
@Table(name = "messages")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "from_user_id")
    private UserEntity fromUser;

    @ManyToOne
    @JoinColumn(name = "to_user_id")
    private UserEntity toUser;

    @Column(name = "message_text", columnDefinition = "TEXT")
    private String messageText;

    @Column(name = "send_datetime", nullable = false)
    private Date sendDatetime;

}
