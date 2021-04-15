package com.Kiiko.ExhibitionsApp.controller;

import com.Kiiko.ExhibitionsApp.controller.assembler.UserAssembler;
import com.Kiiko.ExhibitionsApp.controller.model.UserModel;
import com.Kiiko.ExhibitionsApp.dto.UserDto;
import com.Kiiko.ExhibitionsApp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserAssembler userAssembler;

    @GetMapping("/email/{email}")
    public UserModel getUserByEmail(@PathVariable String email) {
        log.info("get User with email = '{}'", email);
        UserDto userDto = userService.getUserByEmail(email);
        return userAssembler.toModel(userDto);
    }

    @GetMapping("/userId/{userId}")
    public UserModel getUserById(@PathVariable int userId) {
        log.info("get User with userId = '{}'", userId);
        UserDto userDto = userService.getUserById(userId);
        return userAssembler.toModel(userDto);
    }

    @PostMapping
    public UserModel addUser(@Valid @RequestBody UserDto userDto) {
        log.info("addUser method, userDto: {}", userDto);
        UserDto addedUser = userService.addUser(userDto);
        return userAssembler.toModel(addedUser);
    }

    @PutMapping("/{userId}")
    public UserModel updateUser(@Valid @RequestBody UserDto userDto, @PathVariable int userId) {
        log.info("update user - {}", userDto);
        UserDto updatedUser = userService.updateUser(userId, userDto);
        return userAssembler.toModel(updatedUser);
    }
}
