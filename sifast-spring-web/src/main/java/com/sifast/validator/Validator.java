package com.sifast.validator;

import org.springframework.validation.BindingResult;

import com.sifast.common.ApiMessage;
import com.sifast.exception.CustomException;

public class Validator {

    public void validateInputData(BindingResult bindingResult) throws CustomException {
        if (bindingResult.hasFieldErrors()) {
            throw new CustomException(ApiMessage.REQUIRED_VALIDATION_FAILED);
        }
    }
}
