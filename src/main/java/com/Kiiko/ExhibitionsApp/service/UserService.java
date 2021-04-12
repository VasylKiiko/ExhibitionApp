package com.Kiiko.ExhibitionsApp.service;

import com.Kiiko.ExhibitionsApp.dto.UserDto;

public interface UserService {
    UserDto getUserByEmail(String email);

    UserDto getUserById(int userId);

    UserDto addUser(UserDto userDto);

    UserDto updateUser(int userId, UserDto userDto);
}
