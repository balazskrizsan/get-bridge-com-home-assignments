package com.kbalazsworks.common.validators;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;

@Service
public class JavaxValidatorService<T>
{
    public void validateWithConsoleLog(@NonNull T entity)
    {
        ValidatorFactory factory   = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<T>> violations = validator.validate(entity);

        for (ConstraintViolation<T> violation : violations)
        {
            System.out.println("Validation error: " + violation.getMessage());
        }
    }

    public void validate(@NonNull T request)
    {
        ValidatorFactory factory   = Validation.buildDefaultValidatorFactory();
        Validator        validator = factory.getValidator();

        Set<ConstraintViolation<T>> violations = validator.validate(request);

        ArrayList<ValidationException> validationException = new ArrayList<>();

        for (ConstraintViolation<T> violation : violations)
        {
            validationException.add(new ValidationException(violation.getMessage()));
        }

        new RecursiveValidationExceptionBuilder().buildAndThrow(validationException);
    }

    public void arrayValidateWithConsoleLog(ArrayList<T> entities)
    {
        for (T entity : entities)
        {
            validateWithConsoleLog(entity);
        }
    }
}