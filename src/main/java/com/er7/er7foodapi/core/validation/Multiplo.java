package com.er7.er7foodapi.core.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = { MultiploValidator.class })
public @interface Multiplo {

    String message() default "{ VALIDANDO NÚMERO MULTIPLO }";
    Class<?>[] groups() default  {};
    Class<? extends Payload>[] payload() default { };

    int numero();
}
