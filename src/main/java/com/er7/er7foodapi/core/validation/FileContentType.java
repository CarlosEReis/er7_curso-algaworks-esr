package com.er7.er7foodapi.core.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = FileContentTypeValidator.class)
public @interface FileContentType {

    String message() default "ContentType inválido";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

    String[] allowed() default { };
}
