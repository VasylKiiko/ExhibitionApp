package com.Kiiko.ExhibitionsApp.validation;

import com.Kiiko.ExhibitionsApp.validation.annotation.ValidPassword;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, String> {
    private static final String EMPTY_PASSWORD = "Email can't be empty!";
    private static final String INVALID_PASSWORD = "Email should mach the requirements!";

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            context.buildConstraintViolationWithTemplate(EMPTY_PASSWORD)
                    .addPropertyNode("password")
                    .addConstraintViolation();
            return false;
        }
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        boolean valid = true;

        if (!matcher.matches()) {
            valid = false;
            context.buildConstraintViolationWithTemplate(INVALID_PASSWORD)
                    .addPropertyNode("password")
                    .addConstraintViolation();
        }

        return false;
    }
}
