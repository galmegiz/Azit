package com.sun.Azit.error.validation;

import com.sun.Azit.dto.EventFormDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class EventFormRequestValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return EventFormDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
