package com.Kiiko.ExhibitionsApp.model;

import com.Kiiko.ExhibitionsApp.model.enums.UserRole;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class User {
    private int userId;

    private String email;
    private String password;
    private UserRole role;

    private String name;
    private String surname;
}
