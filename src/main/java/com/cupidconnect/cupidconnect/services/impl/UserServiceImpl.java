package com.cupidconnect.cupidconnect.services.impl;

import com.cupidconnect.cupidconnect.models.UserEntity;
import com.cupidconnect.cupidconnect.repositories.UserRepository;
import com.cupidconnect.cupidconnect.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserEntity updateUser(UserEntity user) {
        Optional<UserEntity> foundUserEntityOptional  = userRepository.findById(Integer.valueOf(user.getId()));
        if (foundUserEntityOptional.isPresent()) {
            UserEntity foundUserEntity = foundUserEntityOptional.get();
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            // So sánh mật khẩu đã mã hóa
            if (foundUserEntity.getPassword().equals(encodedPassword) == false) {
                user.setPassword(encodedPassword);
            }
        }
        return userRepository.save(user);
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
    public UserEntity partialUpdate(Integer id, UserEntity userEntity) {
        userEntity.setId(String.valueOf(id));

        return userRepository.findById(id).map(existingUser -> {
            Optional.ofNullable(userEntity.getFirstName()).ifPresent(existingUser::setFirstName);
            Optional.ofNullable(userEntity.getLastName()).ifPresent(existingUser::setLastName);
            Optional.ofNullable(userEntity.getNickname()).ifPresent(existingUser::setFirstName);
            Optional.ofNullable(userEntity.getPassword()).ifPresent(password -> existingUser.setPassword(passwordEncoder.encode(password)));
//            Optional.ofNullable(userEntity.getPassword()).ifPresent(existingUser::setPassword);
            Optional.ofNullable(userEntity.getGenderEntity()).ifPresent(existingUser::setGenderEntity);
            Optional.ofNullable(userEntity.getEmail()).ifPresent(existingUser::setEmail);
            Optional.ofNullable(userEntity.getPhone()).ifPresent(existingUser::setPhone);
            Optional.ofNullable(userEntity.getAddress()).ifPresent(existingUser::setAddress);
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
            Optional.ofNullable(userEntity.getRoleEntity()).ifPresent(existingUser::setRoleEntity);
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username) // tìm theo email
                .orElseThrow(() -> new UsernameNotFoundException("User not found!!!"));
    }
}
