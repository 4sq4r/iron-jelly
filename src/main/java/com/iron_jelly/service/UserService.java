package com.iron_jelly.service;

import com.iron_jelly.exception.CustomException;
import com.iron_jelly.mapper.UserMapper;
import com.iron_jelly.model.dto.AuthDTO;
import com.iron_jelly.model.dto.UserDTO;
import com.iron_jelly.model.entity.User;
import com.iron_jelly.model.enums.UserRole;
import com.iron_jelly.repository.UserRepository;
import com.iron_jelly.security.JwtService;
import com.iron_jelly.util.MessageSource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional(rollbackFor = Exception.class)
    public UserDTO saveOne(UserDTO userDTO) {
        boolean isEmployee = userDTO.getCompanyId() != null;

        if (isEmployee) {
            checkAdminRole(jwtService.getRole());
        }

        String username = jwtService.getUsername();
        formatDTO(userDTO);
        checkUsernameUniqueness(userDTO.getUsername());
        User user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(isEmployee ? UserRole.EMPLOYEE : UserRole.GUEST);
        user.setCreatedBy(username);
        user.setUpdatedBy(username);
        userRepository.save(user);

        return userMapper.toDTO(user);
    }

    public AuthDTO login(AuthDTO authDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authDTO.getUsername(), authDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        authDTO.setToken(jwtService.generateToken(authentication));

        return authDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    public User assignAdminRole(String username) {
        User user = userRepository.findByUsernameIgnoreCase(username).orElseThrow(
                () -> CustomException.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message(MessageSource.USER_NOT_FOUND.getText())
                        .build());
        user.setRole(UserRole.ADMIN);

        return userRepository.save(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public User assignOwnerRole(String username) {
        User user = userRepository.findByUsernameIgnoreCase(username).orElseThrow(
                () -> CustomException.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message(MessageSource.USER_NOT_FOUND.getText())
                        .build());
        user.setRole(UserRole.OWNER);

        return userRepository.save(user);
    }

    public User findEntityByExternalId(UUID id) {
        return userRepository.findByExternalId(id).orElseThrow(
                () -> CustomException.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message(MessageSource.USER_NOT_FOUND.getText())
                        .build());
    }

    public void checkUsernameUniqueness(String username) {
        if (userRepository.existsByUsernameIgnoreCase(username)) {
            throw CustomException.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message(MessageSource.USERNAME_ALREADY_EXISTS.getText())
                    .build();
        }
    }

    public void formatDTO(UserDTO userDTO) {
        userDTO.setUsername(userDTO.getUsername().trim());
        userDTO.setPassword(userDTO.getPassword().trim());
        userDTO.setFirstName(userDTO.getFirstName().trim());
        userDTO.setLastName(userDTO.getLastName().trim());
    }

    private void checkAdminRole(String role) {
        if (!Objects.equals(role, UserRole.ADMIN.name())) {
            throw CustomException.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message(MessageSource.ACCESS_DENIED.getText())
                    .build();
        }
    }

    private void checkOwnerRole(String role) {
        if (!Objects.equals(role, UserRole.OWNER.name())) {
            throw CustomException.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message(MessageSource.ACCESS_DENIED.getText())
                    .build();
        }
    }
}
