package com.Kiiko.ExhibitionsApp.service.impl;

import com.Kiiko.ExhibitionsApp.dto.UserDto;
import com.Kiiko.ExhibitionsApp.exceptions.UserAlreadyExistException;
import com.Kiiko.ExhibitionsApp.exceptions.UserNotFoundException;
import com.Kiiko.ExhibitionsApp.model.User;
import com.Kiiko.ExhibitionsApp.repository.UserRepository;
import com.Kiiko.ExhibitionsApp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(UserNotFoundException::new);
        return mapUserToUserDto(user);
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return mapUserToUserDto(user);
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            log.debug("User with email {} already exists", userDto.getEmail());
            throw new UserAlreadyExistException(userDto.getEmail());
        }
        User userToAdd = mapUserDtoToUser(userDto);
        User user = userRepository.save(userToAdd);
        return mapUserToUserDto(user);
    }

    @Override
    public UserDto updateUser(Long userId, UserDto userDto) {
        User user = mapUserDtoToUser(userDto);
        User userFromDB = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        userRepository.delete(userFromDB);
        user = userRepository.save(user);
        return mapUserToUserDto(user);
    }

    private UserDto mapUserToUserDto(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .role(user.getUserRole())
                .name(user.getName())
                .surname(user.getSurname())
                .build();
    }

    private User mapUserDtoToUser(UserDto userDto) {
        return User.builder()
                .userId(userDto.getUserId())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .userRole(userDto.getRole())
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .build();
    }
}
