package com.Kiiko.ExhibitionsApp.validation.annotation;

import com.Kiiko.ExhibitionsApp.validation.ValidDateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = {ValidDateValidator.class})
public @interface ValidDate {
    String message() default "Invalid date!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
