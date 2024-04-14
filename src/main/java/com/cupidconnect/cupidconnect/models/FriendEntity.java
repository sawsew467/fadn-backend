package com.cupidconnect.cupidconnect.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Entity // là một đối tượng trong cơ sở dữ liệu
@Table(name = "friends")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendEntity {

    @EmbeddedId
    private FriendKey id;

    @ManyToOne
//    @MapsId("user_id")
    @JoinColumn(name = "user_id", insertable=false, updatable=false)
    private UserEntity userId; // user

    @ManyToOne
//    @MapsId("friend_id")
    @JoinColumn(name = "friend_id", insertable=false, updatable=false)
    private UserEntity friendId; // bạn của user

    @Column(name = "friend_since")
    @Temporal(TemporalType.DATE) // Sử dụng TemporalType.DATE để chỉ lưu ngày tháng năm
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date friendSince;

    @Column(name = "favorite", nullable = false)
    private boolean favorite;

    @Column(name = "status")
    private String status;

    @Embeddable
    @Data
    @NoArgsConstructor
    public static class FriendKey implements Serializable {
        @Column(name = "user_id", nullable = false)
        private Long userId;

        @Column(name = "friend_id", nullable = false)
        private Long friendId;
    }
}
