package com.Kiiko.ExhibitionsApp.validation.annotation;

import com.Kiiko.ExhibitionsApp.validation.UniqueEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Constraint(validatedBy = UniqueEmailValidator.class)
public @interface UniqueEmail {
    String message() default "Current email is already in use";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
