package com.Kiiko.ExhibitionsApp.dto;

import com.Kiiko.ExhibitionsApp.model.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private Long userId;

    @Email(message = "Invalid email")
    @NotNull
    private String email;
    private String password;
    private String repeatPassword;

    private UserRole role;
    private String name;
    private String surname;
}
