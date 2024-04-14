package com.cupidconnect.cupidconnect.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity // là một đối tượng trong cơ sở dữ liệu
@Table(name = "conversations")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "participant_1")
    private UserEntity participant1;

    @ManyToOne
    @JoinColumn(name = "participant_2")
    private UserEntity participant2;

    @Column(name = "time_started", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date timeStarted;

    @Column(name = "time_closed")
    private Date timeClosed;

    @Column(name = "status", length = 50)
    private String status;

    @ManyToOne
    @JoinColumn(name = "feedback_id")
    private FeedbackEntity feedback;

}
