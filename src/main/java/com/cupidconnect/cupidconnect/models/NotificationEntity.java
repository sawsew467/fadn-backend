package com.cupidconnect.cupidconnect.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity // là một đối tượng trong cơ sở dữ liệu
@Table(name = "notifications")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UserEntity senderId; // nguoi gui

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private UserEntity recipientId; // nguoi nhan

    @Column(name = "message")
    private String message;

    @Column(name = "timestamp")
    @Temporal(TemporalType.DATE) // Sử dụng TemporalType.DATE để chỉ lưu ngày tháng năm
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date timestamp;

    @Column(name = "read_status")
    private boolean readStatus;

}
