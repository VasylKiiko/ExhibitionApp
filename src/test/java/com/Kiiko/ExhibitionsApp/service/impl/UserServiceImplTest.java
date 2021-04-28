package com.Kiiko.ExhibitionsApp.service.impl;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.Kiiko.ExhibitionsApp.dto.UserDto;
import com.Kiiko.ExhibitionsApp.exceptions.UserAlreadyExistException;
import com.Kiiko.ExhibitionsApp.exceptions.UserNotFoundException;
import com.Kiiko.ExhibitionsApp.model.User;
import com.Kiiko.ExhibitionsApp.repository.UserRepository;
import com.Kiiko.ExhibitionsApp.test.util.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith({MockitoExtension.class})
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void getUserByEmailTest() {
        String emailNotExists= "user not exists";
        User user = TestDataUtil.createUser(1L);
        when(userRepository.findUserByEmail(TestDataUtil.TEST_EMAIL)).thenReturn(Optional.of(user));
        when(userRepository.findUserByEmail(emailNotExists)).thenReturn(Optional.empty());

        UserDto userDto = userService.getUserByEmail(TestDataUtil.TEST_EMAIL);
        assertThat(userDto, allOf(
                hasProperty("email", equalTo(user.getEmail())),
                hasProperty("name", equalTo(user.getName()))
        ));

        assertThrows(UserNotFoundException.class, () -> userService.getUserByEmail(emailNotExists));
    }

    @Test
    void getUserByIdTest() {
        Long id = 1L;
        Long idNotExist = 0L;
        User user = TestDataUtil.createUser(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.findById(idNotExist)).thenReturn(Optional.empty());

        UserDto userDto = userService.getUserById(id);
        assertThat(userDto, allOf(
                hasProperty("userId", equalTo(user.getUserId())),
                hasProperty("email", equalTo(user.getEmail())),
                hasProperty("name", equalTo(user.getName()))
        ));

        assertThrows(UserNotFoundException.class,
                () -> userService.getUserById(idNotExist));
    }

    @Test
    void addUserTest() {
        Long userId = 1L;
        String emailAlreadyExists = "already in use";
        User user = TestDataUtil.createUser(userId);

        when(userRepository.existsByEmail(emailAlreadyExists)).thenReturn(true);
        when(userRepository.existsByEmail(TestDataUtil.TEST_EMAIL)).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);

        UserDto userDto = TestDataUtil.createUserDto(userId);
        UserDto alreadyExistsUser = TestDataUtil.createUserDto(userId);
        alreadyExistsUser.setEmail(emailAlreadyExists);

        UserDto createdUser = userService.addUser(userDto);


        assertThat(createdUser, allOf(
                hasProperty("userId", equalTo(userDto.getUserId())),
                hasProperty("email", equalTo(userDto.getEmail()))
        ));

        assertThrows(UserAlreadyExistException.class, () -> userService.addUser(alreadyExistsUser));
    }

    @Test
    void updateUserTest() {
        Long userId = 1L;
        Long userIdNotExists = 0L;
        User user = TestDataUtil.createUser(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.findById(userIdNotExists)).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        UserDto userDto = TestDataUtil.createUserDto(userId);
        UserDto notExistsUser = TestDataUtil.createUserDto(userId);
        notExistsUser.setUserId(userIdNotExists);

        UserDto createdUser = userService.updateUser(userId, userDto);


        assertThat(createdUser, allOf(
                hasProperty("userId", equalTo(userDto.getUserId())),
                hasProperty("email", equalTo(userDto.getEmail()))
        ));

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(userIdNotExists, notExistsUser));
    }
}