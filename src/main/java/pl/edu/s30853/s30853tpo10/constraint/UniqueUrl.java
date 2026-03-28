package pl.edu.s30853.s30853tpo10.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueUrlValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUrl {
    String message() default "{msg.targetUrl.unique}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
