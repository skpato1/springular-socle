package com.sifast.dto.user;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.sifast.common.ApiMessage;
import com.sifast.common.Constants;
import com.sifast.common.utils.IWebServicesValidators;

public class CreateUserDto extends UserDto {

    @NotEmpty(message = ApiMessage.REQUIRED_VALIDATION_FAILED, groups = IWebServicesValidators.class)
    @NotNull(message = ApiMessage.REQUIRED_VALIDATION_FAILED, groups = IWebServicesValidators.class)
    @Size(max = Constants.MAX_STRING_SIZE, message = ApiMessage.STRING_SIZE_VALIDATION_FAILED, groups = IWebServicesValidators.class)
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CreateUserDto [password=");
        builder.append(password);
        builder.append("]");
        return builder.toString();
    }

}
