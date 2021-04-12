package com.Kiiko.ExhibitionsApp.repository;

import com.Kiiko.ExhibitionsApp.model.User;

public interface UserRepository {
    User addUser(User newUser);

    boolean isUserEmailExists(String email);

    User getUserByEmail(String email);

    User getUserById(int userId);

    User updateUser(int userId, User user);
}
