package com.cupidconnect.cupidconnect.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
    protected void onCreate() {
        this.setRoleEntity(new RoleEntity(2L, "USER"));
        this.isActive = 1;
        this.isAdmin = 0;
        this.setPopularity(BigDecimal.valueOf(0));
        this.confirmationTime = new Date(System.currentTimeMillis());
        this.createdAt = new Date(System.currentTimeMillis());
        this.updatedAt = new Date(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date(System.currentTimeMillis());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;

    @Column(name = "first_name", nullable = false)
    @NotNull
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "gender_id")
    private GenderEntity genderEntity; // Foreign key 'gender_id'

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone", nullable = false, unique = true)
    private String phone;

    @Column(name = "address")
    private String address;

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

    @Column(name = "confirmation_code", nullable = false, unique = true)
    private String confirmationCode;

    @Column(name = "confirmation_time")
    @Temporal(TemporalType.TIMESTAMP) // Sử dụng TemporalType.TIMESTAMP để chỉ lưu ngày tháng năm và thời gian
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date confirmationTime;

    @Column(name = "popularity", precision = 5, scale = 2)
    private BigDecimal popularity;

    @Column(name = "is_active", nullable = false)
    private Integer isActive;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;

    @Column(name = "is_admin", nullable = false) // 1: admin | 0: not admin
    private Integer isAdmin;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity roleEntity; // Foreign key 'role_id'

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private ProfileEntity profileEntity; // Foreign key 'profile_id'

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (isAdmin == 1) {
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        } else if (isAdmin == 0) {
            if (roleEntity.getName().equals("USER")) {
                authorities.add(new SimpleGrantedAuthority("USER"));
            } else if (roleEntity.getName().equals("PREMIUM")) {
                authorities.add(new SimpleGrantedAuthority("PREMIUM"));
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
