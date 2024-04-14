package com.cupidconnect.cupidconnect.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity // là một đối tượng trong cơ sở dữ liệu
@Table(name = "feedbacks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "rate", nullable = false)
    private int rate;

    @OneToMany(mappedBy = "feedback")
    private List<ConversationEntity> conversationEntities;

}
