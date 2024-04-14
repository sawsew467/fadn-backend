package com.cupidconnect.cupidconnect.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Entity // là một đối tượng trong cơ sở dữ liệu
@Table(name = "friend_requests")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendRequestEntity {

    @EmbeddedId
    private FriendRequestKey id;

    @ManyToOne
    @JoinColumn(name = "to_id", insertable=false, updatable=false)
    private UserEntity toId;

    @ManyToOne
    @JoinColumn(name = "from_id", insertable=false, updatable=false)
    private UserEntity fromId;

    @Column(name = "accepted")
    private int accepted;

    @Column(name = "message")
    private String message;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    @Embeddable
    @Data
    @NoArgsConstructor
    public static class FriendRequestKey implements Serializable {
        @Column(name = "to_id", nullable = false)
        private Long toId;

        @Column(name = "from_id", nullable = false)
        private Long fromId;
    }
}
