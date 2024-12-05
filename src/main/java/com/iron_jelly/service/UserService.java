package com.iron_jelly.service;

import com.iron_jelly.exception.CustomException;
import com.iron_jelly.mapper.UserMapper;
import com.iron_jelly.model.dto.UserDTO;
import com.iron_jelly.model.entity.User;
import com.iron_jelly.repository.UserRepository;
import com.iron_jelly.util.MessageSource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDTO saveOne(UserDTO userDTO) {
        if (userRepository.existsByUsernameIgnoreCase(userDTO.getUsername().trim())) {
            throw CustomException.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message(MessageSource.USERNAME_ALREADY_EXISTS.getText())
                    .build();
        }

        User user = userMapper.toEntity(userDTO);
        user.setUsername(user.getUsername().trim());
        user.setFirstName(user.getFirstName().trim());
        user.setLastName(user.getLastName().trim());
        user = userRepository.save(user);

        return userMapper.toDTO(user);
    }

    public UserDTO getOne(UUID id) {
        return userMapper.toDTO(findById(id));
    }

    public void deleteOne(UUID id) {
        User user = findById(id);
        userRepository.delete(user);
    }

    public User findById(UUID id) {
        return userRepository.findById(id).orElseThrow(
                () -> CustomException.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message(MessageSource.USER_NOT_FOUND.getText())
                        .build());
    }
}
