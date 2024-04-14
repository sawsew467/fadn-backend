package com.cupidconnect.cupidconnect.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.TimeZone;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity // là một đối tượng trong cơ sở dữ liệu
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity implements UserDetails {
    @PrePersist
    public void onCreate() {
        this.setRoleEntity(new RoleEntity(1L, "USER"));
        if (isAdmin == null) {
            isAdmin = 0; // Giả sử mặc định không phải là quản trị viên
        }
        if (isActive == null) {
            isActive = 1; // Giả sử mặc định là hoạt động
        }
        if (popularity == null) {
            popularity = BigDecimal.valueOf(0); // Giả sử mặc định là 0
        }

//        this.confirmationTime = new Date(System.currentTimeMillis());
//        this.createdAt = new Date(System.currentTimeMillis());
//        this.updatedAt = new Date();
    }

//    @PreUpdate
//    protected void onUpdate() {
//        this.updatedAt = new Date(System.currentTimeMillis());
//    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name", nullable = false)
    @NotNull
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gender_id")
    private GenderEntity genderEntity; // Foreign key 'gender_id'

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private StatusEntity statusEntity;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone", nullable = false, unique = true)
    private String phone;

    @Column(name = "city")
    private String city;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE) // Sử dụng TemporalType.DATE để chỉ lưu ngày tháng năm
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    //    @Column(name = "fb_account_id")
    @Column(name = "fb_account_id", unique = true)
    private Integer fbAccountId;

    //    @Column(name = "google_account_id")
    @Column(name = "google_account_id", unique = true)
    private Integer googleAccountId;

    @Column(name = "confirmation_code", nullable = false)
    private String confirmationCode;

//    @Column(name = "confirmation_time")
//    @CreationTimestamp // khi xoá serverTimezone=UTC trong properties mới dùng đc ()
//    @Temporal(TemporalType.TIMESTAMP) // Sử dụng TemporalType.TIMESTAMP để chỉ lưu ngày tháng năm và thời gian
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date confirmationTime;

    @Generated(GenerationTime.INSERT)
    @Basic(optional = false)
    @Column(name = "confirmation_time")
    @Temporal(TemporalType.TIMESTAMP) // Sử dụng TemporalType.TIMESTAMP để chỉ lưu ngày tháng năm và thời gian
    private Date confirmationTime;

    @Column(name = "popularity", precision = 5, scale = 2)
    private BigDecimal popularity;

    @Column(name = "is_active", nullable = false)
    private Integer isActive;

    @Generated(GenerationTime.INSERT)
    @Basic(optional = false) // không được phép để trống (null) khi lưu vào cơ sở dữ liệu
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Generated(GenerationTime.ALWAYS)
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name = "is_admin", nullable = false) // 1: admin | 0: not admin
    private Integer isAdmin;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity roleEntity; // Foreign key 'role_id'

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private ProfileEntity profileEntity; // Foreign key 'profile_id'

//    -----------------------------------------------------------

//
//    @OneToMany(mappedBy = "userEntity")
//    private List<UserPurchasesEntity> userPurchasesEntities;
//
//    @OneToMany(mappedBy = "toId")
//    private List<FriendRequestEntity> toUsers;
//    @OneToMany(mappedBy = "fromId")
//    private List<FriendRequestEntity> fromUser;
//
//    @OneToMany(mappedBy = "senderId")
//    private List<NotificationEntity> senderId;
//    @OneToMany(mappedBy = "recipientId")
//    private List<NotificationEntity> recipientId;
//
//    @OneToMany(mappedBy = "userId")
//    private List<FriendEntity> userId;
//    @OneToMany(mappedBy = "friendId")
//    private List<FriendEntity> friendId;
//
//    @OneToMany(mappedBy = "fromUser")
//    private List<MessageEntity> messageFromUser;
//    @OneToMany(mappedBy = "toUser")
//    private List<MessageEntity> messageToUser;
//
//    @OneToMany(mappedBy = "userId")
//    private List<UserPhotoEntity> userPhotos;
//
//    @OneToMany(mappedBy = "userId")
//    private List<SocialAccountEntity> socialAccount;
//
//    // --
//    @OneToMany(mappedBy = "userId")
//    private List<BlockEntity> blocks;
//    @OneToMany(mappedBy = "userBlocked")
//    private List<BlockEntity> blockedByOthers;
//
//    @OneToMany(mappedBy = "participant1")
//    private List<ConversationEntity> conversationsAsParticipant1;
//    @OneToMany(mappedBy = "participant2")
//    private List<ConversationEntity> conversationsAsParticipant2;
//
//    @OneToMany(mappedBy = "user")
//    private List<GradeEntity> givenGrades;
//    @OneToMany(mappedBy = "userReceived")
//    private List<GradeEntity> receivedGrades;
//
//    @OneToMany(mappedBy = "user")
//    private List<InterestGenderEntity> interestGenders;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (isAdmin == 1) {
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        } else if (isAdmin == 0 && roleEntity != null) {
            String roleName = roleEntity.getName();
            if (roleName != null) {
                authorities.add(new SimpleGrantedAuthority(roleName));
            }
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        // login bằng email + password (thay vì username + password)
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
