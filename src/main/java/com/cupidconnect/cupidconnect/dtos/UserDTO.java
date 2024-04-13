package com.cupidconnect.cupidconnect.dtos;

import com.cupidconnect.cupidconnect.models.GenderEntity;
import com.cupidconnect.cupidconnect.models.ProfileEntity;
import com.cupidconnect.cupidconnect.models.RoleEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Integer id;

    @NotNull(message = "First name is required !")
    private String firstName;

    @NotNull(message = "Last name is required !")
    private String lastName;

    @NotNull(message = "Nickname is required !")
    private String nickname;

    @NotNull(message = "Password is required !")
//    @JsonIgnore // Lỗi: ko đc trả về password nhưng đồng thời phải nhận giá trị password
    private String password;

    @NotNull(message = "Gender_id is required !")
    private GenderDTO genderDTO; // Foreign key 'gender_id'
//    private Integer genderId; // bởi vì bảng genders đc tạo sẵn trong DB nên ko cần truyền Object vào, chỉ
    // cần id của gender đc tạo sẵn là đc

    @NotNull(message = "Email is required !")
    @Email
    private String email;

    @NotNull(message = "Phone is required !")
    private String phone;

    @NotNull(message = "Address is required !")
    private String address;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dob;

    private Integer fbAccountId;

    private Integer googleAccountId;

    @NotNull(message = "Confirmation code is required !")
    private String confirmationCode;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date confirmationTime;

    private BigDecimal popularity;

    private Integer isActive;

//    @NotNull(message = "Create_at is required !") -> Not null khi lưu trong DB, ko phải gửi lên
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

//    @NotNull(message = "Updated_at is required !") -> Not null khi lưu trong DB, ko phải gửi lên
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;

    private Integer isAdmin;

//    @NotNull(message = "Role_id is required !") -> Not null khi lưu trong DB, ko phải gửi lên
    private RoleDTO roleDTO; // Foreign key 'role_id'

    @NotNull(message = "Profile is required !")
    private ProfileDTO profileDTO; // Foreign key 'profile_id'

//    private String token; // ko mapper tới UserEntity
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        if (isAdmin == 1) {
//            authorities.add(new SimpleGrantedAuthority("ADMIN"));
//        } else if (isAdmin == 0) {
//            if (roleDTO.getName().equals("USER")) {
//                authorities.add(new SimpleGrantedAuthority("USER"));
//            } else if (roleDTO.getName().equals("PREMIUM")) {
//                authorities.add(new SimpleGrantedAuthority("PREMIUM"));
//            }
//        }
//        return authorities;
//    }
//
//    @Override
//    public String getUsername() {
//        return email;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
}
