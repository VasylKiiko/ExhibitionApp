package com.Kiiko.ExhibitionsApp.service.impl;

import com.Kiiko.ExhibitionsApp.dto.UserDto;
import com.Kiiko.ExhibitionsApp.exceptions.UserAlreadyExistException;
import com.Kiiko.ExhibitionsApp.model.User;
import com.Kiiko.ExhibitionsApp.repository.UserRepository;
import com.Kiiko.ExhibitionsApp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.getUserByEmail(email);
        return mapUserToUserDto(user);
    }

    @Override
    public UserDto getUserById(int userId) {
        User user = userRepository.getUserById(userId);
        return mapUserToUserDto(user);
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        if (userRepository.isUserEmailExists(userDto.getEmail())) {
            log.debug("User with email {} already exists", userDto.getEmail());
            throw new UserAlreadyExistException(userDto.getEmail());
        }
        User userToAdd = mapUserDtoToUser(userDto);
        User user = userRepository.addUser(userToAdd);
        return mapUserToUserDto(user);
    }

    @Override
    public UserDto updateUser(int userId, UserDto userDto) {
        User userToUpdate = mapUserDtoToUser(userDto);
        User user = userRepository.updateUser(userId, userToUpdate);
        return mapUserToUserDto(user);
    }

    private UserDto mapUserToUserDto(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .role(user.getRole())
                .name(user.getName())
                .surname(user.getSurname())
                .build();
    }

    private User mapUserDtoToUser(UserDto userDto) {
        return User.builder()
                .userId(userDto.getUserId())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .role(userDto.getRole())
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .build();
    }
}
