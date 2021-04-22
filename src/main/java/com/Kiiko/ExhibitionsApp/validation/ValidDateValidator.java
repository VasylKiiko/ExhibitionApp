package com.Kiiko.ExhibitionsApp.validation;

import com.Kiiko.ExhibitionsApp.dto.ExhibitionDto;
import com.Kiiko.ExhibitionsApp.validation.annotation.ValidDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidDateValidator implements ConstraintValidator<ValidDate, ExhibitionDto> {
    @Override
    public void initialize(ValidDate constraintAnnotation) {
    }

    @Override
    public boolean isValid(ExhibitionDto exhibitionDto, ConstraintValidatorContext context) {
        return exhibitionDto.getDateFrom().isBefore(exhibitionDto.getDateTo());
    }
}
