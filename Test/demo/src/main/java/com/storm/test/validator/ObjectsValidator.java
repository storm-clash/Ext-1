package com.storm.test.validator;

import com.storm.test.entities.Base;
import com.storm.test.exceptions.ObjectNotValidException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ObjectsValidator<T> {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();


    public void validate (T entity){
        Set<ConstraintViolation<T>> violations = validator.validate(entity);
        if(!violations.isEmpty()){
         var errorMessages = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toSet());
            throw new ObjectNotValidException(errorMessages);
        }

    }
}
