package com.Kiiko.ExhibitionsApp.controller;

import com.Kiiko.ExhibitionsApp.api.UserApi;
import com.Kiiko.ExhibitionsApp.controller.assembler.UserAssembler;
import com.Kiiko.ExhibitionsApp.controller.model.UserModel;
import com.Kiiko.ExhibitionsApp.dto.UserDto;
import com.Kiiko.ExhibitionsApp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {
    private final UserService userService;
    private final UserAssembler userAssembler;

    @Override
    public UserModel getUserByEmail(String email) {
        log.info("get User with email = '{}'", email);
        UserDto userDto = userService.getUserByEmail(email);
        return userAssembler.toModel(userDto);
    }

    @Override
    public UserModel getUserById(int userId) {
        log.info("get User with userId = '{}'", userId);
        UserDto userDto = userService.getUserById(userId);
        return userAssembler.toModel(userDto);
    }

    @Override
    public UserModel addUser(UserDto userDto) {
        log.info("addUser method, userDto: {}", userDto);
        UserDto addedUser = userService.addUser(userDto);
        return userAssembler.toModel(addedUser);
    }

    @Override
    public UserModel updateUser(UserDto userDto, int userId) {
        log.info("update user - {}", userDto);
        UserDto updatedUser = userService.updateUser(userId, userDto);
        return userAssembler.toModel(updatedUser);
    }
}
