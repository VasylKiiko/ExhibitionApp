package com.Kiiko.ExhibitionsApp.controller;

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

    @GetMapping
    public UserDto getUserByEmail(@RequestParam String email) {
        log.info("get User with email = '{}'", email);
        return userService.getUserByEmail(email);
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable int userId) {
        log.info("get User with userId = '{}'", userId);
        return userService.getUserById(userId);
    }

    @PostMapping
    public UserDto addUser(@Valid @RequestBody UserDto userDto) {
        log.info("addUser method, userDto: {}", userDto);
        return userService.addUser(userDto);
    }

    @PutMapping("/{userId}")
    public UserDto updateUser(@Valid @RequestBody UserDto userDto, @PathVariable int userId) {
        log.info("update user - {}", userDto);
        return userService.updateUser(userId, userDto);
    }
}
