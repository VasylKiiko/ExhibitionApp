package com.Kiiko.ExhibitionsApp.controller;

import com.Kiiko.ExhibitionsApp.dto.UserDto;
import com.Kiiko.ExhibitionsApp.exceptions.UserNotFoundException;
import com.Kiiko.ExhibitionsApp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@Slf4j
@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public UserDto getUserByEmail(@RequestParam String email) {
        log.debug("get User with email = '{}'", email);
        UserDto userDto;
        try {
            userDto = userService.getUserByEmail(email);
        } catch (UserNotFoundException ex) {
            log.debug("user with email = '{}' not found", email);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with current email not found", ex);
        }
        return userDto;
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable int userId) {
        log.debug("get User with userId = '{}'", userId);
        return userService.getUserById(userId);
    }

    @PostMapping
    public UserDto addUser(@Valid @RequestBody UserDto userDto) {
        log.debug("addUser method, userDto: {}", userDto);
        return userService.addUser(userDto);
    }

    @PutMapping("/{userId}")
    public UserDto updateUser(@Valid @RequestBody UserDto userDto, @PathVariable int userId) {
        log.debug("update user - {}", userDto);
        return userService.updateUser(userId, userDto);
    }
}
