package com.Kiiko.ExhibitionsApp.dto;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

import com.Kiiko.ExhibitionsApp.model.enums.UserRole;
import com.Kiiko.ExhibitionsApp.validation.annotation.EqualsPassword;
import com.Kiiko.ExhibitionsApp.validation.annotation.UniqueEmail;
import com.Kiiko.ExhibitionsApp.validation.annotation.ValidPassword;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@EqualsPassword(groups = UserDto.Registration.class)
public class UserDto {

    public interface Registration { }
    public interface Updating { }
    public interface LogIn { }

    private Long userId;

    @Email(message = "Invalid email")
    @NotNull(groups = {Registration.class, Updating.class, LogIn.class})
    @UniqueEmail(groups = {Registration.class})
    private String email;

    @NotNull(groups = {Registration.class, Updating.class, LogIn.class})
    @ValidPassword
    private String password;

    @NotNull(groups = {Registration.class})
    private String repeatPassword;

    @NotNull(groups = {Registration.class, Updating.class})
    private UserRole role;

    @NotNull(groups = {Registration.class, Updating.class})
    private String name;

    @NotNull(groups = {Registration.class, Updating.class})
    private String surname;
}
