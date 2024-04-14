package com.cupidconnect.cupidconnect.services.impl;

import com.cupidconnect.cupidconnect.models.GenderEntity;
import com.cupidconnect.cupidconnect.models.RoleEntity;
import com.cupidconnect.cupidconnect.models.StatusEntity;
import com.cupidconnect.cupidconnect.models.UserEntity;
import com.cupidconnect.cupidconnect.repositories.GenderRepository;
import com.cupidconnect.cupidconnect.repositories.RoleRepository;
import com.cupidconnect.cupidconnect.repositories.StatusRepository;
import com.cupidconnect.cupidconnect.repositories.UserRepository;
import com.cupidconnect.cupidconnect.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final GenderRepository genderRepository;
    private final StatusRepository statusRepository;

    @Override
    public UserEntity updateUser(UserEntity user) {
        user.setPassword(foundUpdatedPassword(user));
        return userRepository.save(user);
    }

    private String foundUpdatedPassword(UserEntity user) {
        Optional<UserEntity> foundUserEntityOptional  = userRepository.findById(user.getId());
        if (foundUserEntityOptional.isPresent()) {
            UserEntity foundUserEntity = foundUserEntityOptional.get();
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            // So sánh mật khẩu đã mã hóa
            if (foundUserEntity.getPassword().equals(encodedPassword) == false) {
                return encodedPassword;
            }
        }

        return user.getPassword();
    }

    @Override
    public boolean isExists(Integer id) {
        return userRepository.existsById(id);
    }

    @Override
    public List<UserEntity> findAll() {
        return StreamSupport.stream(userRepository
                .findAll() // Lấy ra từ Repository dạng List<UserEntity> là Entity
                .spliterator(),
                false)
                .collect(Collectors.toList()); // Đã biến đổi List<Entity> thành List<Model> ở bên Controller rồi
                                            // (xem ở constructor của AuthorDto (UserModel) có code copy dữ liệu)
    }

    @Override
    public Optional<UserEntity> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Boolean checkPassword(Integer id, String inputPassword) {
        Optional<UserEntity> foundUserEntityOptional  = userRepository.findById(id);
        if (foundUserEntityOptional.isPresent()) {
            UserEntity foundUserEntity = foundUserEntityOptional.get();
            return passwordEncoder.matches(inputPassword, foundUserEntity.getPassword());
        }

        return false;


    }

    @Override
    public boolean checkPhone(String phone) {
        Optional<UserEntity> userOptional = userRepository.findByPhone(phone);
        return userOptional.isPresent();
    }

    @Override
    public boolean checkNickname(String nickname) {
        Optional<UserEntity> userOptional = userRepository.findByNickname(nickname);
        return userOptional.isPresent();
    }

    @Override
    public UserEntity partialUpdate(Integer id, UserEntity userEntity) {
        userEntity.setId(id);

        return userRepository.findById(id).map(existingUser -> {
            Optional.ofNullable(userEntity.getFirstName()).ifPresent(existingUser::setFirstName);
            Optional.ofNullable(userEntity.getLastName()).ifPresent(existingUser::setLastName);
            Optional.ofNullable(userEntity.getNickname()).ifPresent(existingUser::setNickname);
            Optional.ofNullable(userEntity.getPassword())
                    .ifPresent(password -> existingUser.setPassword(passwordEncoder.encode(password)));
//            Optional.ofNullable(userEntity.getPassword()).ifPresent(existingUser::setPassword);
            Optional.ofNullable(userEntity.getGenderEntity())
                    .ifPresent(genderEntity -> existingUser.setGenderEntity(findGenderEntity(genderEntity.getId())));
            Optional.ofNullable(userEntity.getStatusEntity())
                            .ifPresent(statusEntity -> existingUser.setStatusEntity(findStatusEntity(statusEntity.getId())));
            Optional.ofNullable(userEntity.getEmail()).ifPresent(existingUser::setEmail);
            Optional.ofNullable(userEntity.getPhone()).ifPresent(existingUser::setPhone);
            Optional.ofNullable(userEntity.getCity()).ifPresent(existingUser::setCity);
            Optional.ofNullable(userEntity.getDob()).ifPresent(existingUser::setDob);
            Optional.ofNullable(userEntity.getFbAccountId()).ifPresent(existingUser::setFbAccountId);
            Optional.ofNullable(userEntity.getGoogleAccountId()).ifPresent(existingUser::setGoogleAccountId);
            Optional.ofNullable(userEntity.getConfirmationCode()).ifPresent(existingUser::setConfirmationCode);
            Optional.ofNullable(userEntity.getConfirmationTime()).ifPresent(existingUser::setConfirmationTime);
            Optional.ofNullable(userEntity.getPopularity()).ifPresent(existingUser::setPopularity);
            Optional.ofNullable(userEntity.getIsActive()).ifPresent(existingUser::setIsActive);
//            Optional.ofNullable(userEntity.getCreatedAt()).ifPresent(existingUser::setCreatedAt);
//            Optional.ofNullable(userEntity.getUpdatedAt()).ifPresent(existingUser::setUpdatedAt);
            Optional.ofNullable(userEntity.getIsAdmin()).ifPresent(existingUser::setIsAdmin);
            Optional.ofNullable(userEntity.getRoleEntity())
                    .ifPresent(roleEntity -> existingUser.setRoleEntity(findRoleEntity(roleEntity.getId())));
            Optional.ofNullable(userEntity.getProfileEntity()).ifPresent(existingUser::setProfileEntity);

            return userRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User does not exist!"));
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean isExistsByEmail(String email) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);
        return userOptional.isPresent();
    }

    @Override
    public boolean isPasswordValid(String email, String password) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow();
        PasswordEncoder passwordEncoder1 = new BCryptPasswordEncoder();
        // Kiểm tra mật khẩu đã hash từ người dùng với mật khẩu đã hash trong cơ sở dữ liệu
        return passwordEncoder.matches(password, user.getPassword());
    }

    private RoleEntity findRoleEntity(Long id) {
        Optional<RoleEntity> foundRoleEntityOptional = roleRepository.findById(id);
        RoleEntity foundRoleEntity = new RoleEntity();
        if (foundRoleEntityOptional.isPresent()) {
            foundRoleEntity = foundRoleEntityOptional.get();
        }

        return foundRoleEntity;
    }

    private StatusEntity findStatusEntity(Long id) {
        Optional<StatusEntity> foundStatusEntityOptional = statusRepository.findById(id);
        StatusEntity foundStatusEntity = new StatusEntity();
        if (foundStatusEntityOptional.isPresent()) {
            foundStatusEntity = foundStatusEntityOptional.get();
        }

        return foundStatusEntity;
    }

    private GenderEntity findGenderEntity(Integer id) {
        Optional<GenderEntity> foundGenderEntityOptional = genderRepository.findById(id);
        GenderEntity foundGenderEntity = new GenderEntity();
        if (foundGenderEntityOptional.isPresent()) {
            foundGenderEntity = foundGenderEntityOptional.get();
        }
        return foundGenderEntity;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username) // tìm theo email
                .orElseThrow(() -> new UsernameNotFoundException("User not found!!!"));
    }
}
