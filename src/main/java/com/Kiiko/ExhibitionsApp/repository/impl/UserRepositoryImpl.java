package com.Kiiko.ExhibitionsApp.repository.impl;

import com.Kiiko.ExhibitionsApp.exceptions.UserNotFoundException;
import com.Kiiko.ExhibitionsApp.model.User;
import com.Kiiko.ExhibitionsApp.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserRepositoryImpl implements UserRepository {
    private final List<User> usersList = new ArrayList<>();
    private static int userIdIncr = 0;

    @Override
    public User addUser(User newUser) {
        newUser.setUserId(++userIdIncr);
        usersList.add(newUser);
        return newUser;
    }

    @Override
    public boolean isUserEmailExists(String email) {
        return usersList.stream().anyMatch(user -> user.getEmail().equals(email));
    }

    @Override
    public User getUserByEmail(String email) {
        return usersList.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("email: " + email));
    }

    @Override
    public User getUserById(int userId) {
        return usersList.stream()
                .filter(user -> user.getUserId() == userId)
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("id: " + userId));
    }

    @Override
    public User updateUser(int userId, User user) {
        boolean isDeleted = usersList.removeIf(listUser -> listUser.getUserId() == userId);
        if (isDeleted) {
            user.setUserId(userId);
            usersList.add(user);
        } else {
            throw new UserNotFoundException("id: " + userId);
        }
        return user;
    }
}
