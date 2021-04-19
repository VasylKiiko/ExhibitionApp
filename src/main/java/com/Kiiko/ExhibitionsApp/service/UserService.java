package com.Kiiko.ExhibitionsApp.service;

import com.Kiiko.ExhibitionsApp.dto.UserDto;

public interface UserService {
    UserDto getUserByEmail(String email);

    UserDto getUserById(Long userId);

    UserDto addUser(UserDto userDto);

    UserDto updateUser(Long userId, UserDto userDto);
}
