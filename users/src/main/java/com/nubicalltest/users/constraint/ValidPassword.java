package com.nubicalltest.users.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.nubicalltest.users.constraint.validator.PasswordConstraintValidator;

@Documented
//@Constraint(validatedBy = PasswordConstraintValidator.class)
//@Target({ TYPE, FIELD, ANNOTATION_TYPE})
//@Retention(RUNTIME)
public @interface ValidPassword {

	String message() default "Invalid Password";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
}
