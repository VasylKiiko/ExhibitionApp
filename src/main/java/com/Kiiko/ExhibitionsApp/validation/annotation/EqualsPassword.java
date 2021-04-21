package com.Kiiko.ExhibitionsApp.validation.annotation;

import com.Kiiko.ExhibitionsApp.validation.EqualsPasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Constraint(validatedBy = EqualsPasswordValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface EqualsPassword {
    String message() default "Passwords are not equal";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
