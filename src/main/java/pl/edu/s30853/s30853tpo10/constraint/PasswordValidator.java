package pl.edu.s30853.s30853tpo10.constraint;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if(password == null) return true;

        if(password.length() < 10){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("{link.password.length}").addConstraintViolation();
            return false;
        }

        int lowercaseCount = 0;
        int uppercaseCount = 0;
        int digitCount = 0;
        int specialCount = 0;
        for (char c : password.toCharArray()) {
            if (Character.isLowerCase(c)) lowercaseCount++;
            else if (Character.isUpperCase(c)) uppercaseCount++;
            else if (Character.isDigit(c)) digitCount++;
            else if (!Character.isLetterOrDigit(c)) specialCount++;
        }

        if(lowercaseCount < 1) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("{link.password.lowercase}").addConstraintViolation();
            return false;
        }
        else if(uppercaseCount < 2){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("{link.password.uppercase}").addConstraintViolation();
            return false;
        }
        else if(digitCount < 3){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("{link.password.digits}").addConstraintViolation();
            return false;
        }
        else if(specialCount < 4){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("{link.password.special}").addConstraintViolation();
            return false;
        }
        return true;
    }
}
