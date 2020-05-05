package com.sifast.validator;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import com.sifast.common.ApiMessage;
import com.sifast.dto.user.CreateUserDto;
import com.sifast.exception.CustomException;
import com.sifast.model.User;
import com.sifast.service.IUserService;

@Component
public class UserValidator extends Validator {

    @Autowired
    private IUserService userService;

    public void createUserValidator(CreateUserDto userDto, BindingResult bindingResult) throws CustomException {
        validateInputData(bindingResult);
        checkExistanceLogin(userDto.getLogin());
    }

    private void checkExistanceLogin(String login) throws CustomException {
        Optional<User> user = userService.findByLogin(login);
        if (user.isPresent()) {
            throw new CustomException(ApiMessage.USER_ALREADY_EXIT);
        }

    }
}
