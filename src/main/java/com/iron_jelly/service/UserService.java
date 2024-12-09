package com.iron_jelly.service;

import com.iron_jelly.exception.CustomException;
import com.iron_jelly.mapper.UserMapper;
import com.iron_jelly.model.dto.AuthDTO;
import com.iron_jelly.model.dto.UserDTO;
import com.iron_jelly.model.entity.User;
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
        userDTO.setUsername(userDTO.getUsername().trim());
        userDTO.setFirstName(userDTO.getFirstName().trim());
        userDTO.setLastName(userDTO.getLastName().trim());
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword().trim()));
        checkUsernameUniqueness(userDTO.getUsername());
        User user = userMapper.toEntity(userDTO);
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
    public void deleteOne(UUID id) {
        User user = findEntityByExternalId(id);
        userRepository.delete(user);
    }

    public UserDTO getOne(UUID id) {
        return userMapper.toDTO(findEntityByExternalId(id));
    }

    public User findEntityByExternalId(UUID id) {
        return userRepository.findByExternalId(id).orElseThrow(
                () -> CustomException.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message(MessageSource.USER_NOT_FOUND.getText())
                        .build());
    }

    private void checkUsernameUniqueness(String username) {
        if (userRepository.existsByUsernameIgnoreCase(username)) {
            throw CustomException.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message(MessageSource.USERNAME_ALREADY_EXISTS.getText())
                    .build();
        }
    }
}
