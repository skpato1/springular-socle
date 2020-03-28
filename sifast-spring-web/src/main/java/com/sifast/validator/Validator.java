package com.sifast.validator;

import org.springframework.validation.BindingResult;

import com.sifast.common.ApiMessage;
import com.sifast.exception.ChoException;

public class Validator {

    public void validateInputData(BindingResult bindingResult) throws ChoException {
        if (bindingResult.hasFieldErrors()) {
            throw new ChoException(ApiMessage.REQUIRED_VALIDATION_FAILED);
        }
    }
}
