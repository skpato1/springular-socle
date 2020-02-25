package com.sifast.common.utils;

import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

public interface IValidatorError {

    static String getValidatorErrors(BindingResult bindingResult) {
        return bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(", "));
    }
}