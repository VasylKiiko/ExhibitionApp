package com.Kiiko.ExhibitionsApp.validation;

import com.Kiiko.ExhibitionsApp.dto.UserDto;
import com.Kiiko.ExhibitionsApp.validation.annotation.EqualsPassword;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EqualsPasswordValidator implements ConstraintValidator<EqualsPassword, UserDto> {
    @Override
    public void initialize(EqualsPassword constraintAnnotation) {
    }

    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext context) {
        return userDto.getPassword().equals(userDto.getRepeatPassword());
    }
}
