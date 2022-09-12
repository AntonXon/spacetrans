package com.company.spacetrans.app.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = ValidSpaceportValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidSpaceport {
    String message() default "You should define planet or moon value. You cannot define both of them.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
