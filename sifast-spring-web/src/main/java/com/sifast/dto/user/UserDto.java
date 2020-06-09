package com.sifast.dto.user;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.sifast.common.ApiMessage;
import com.sifast.common.Constants;
import com.sifast.common.utils.IWebServicesValidators;

public class UserDto {

    @NotEmpty(message = ApiMessage.REQUIRED_VALIDATION_FAILED, groups = IWebServicesValidators.class)
    @NotNull(message = ApiMessage.REQUIRED_VALIDATION_FAILED, groups = IWebServicesValidators.class)
    @Size(max = Constants.MAX_STRING_SIZE, message = ApiMessage.STRING_SIZE_VALIDATION_FAILED, groups = IWebServicesValidators.class)
    private String firstName;

    @NotEmpty(message = ApiMessage.REQUIRED_VALIDATION_FAILED, groups = IWebServicesValidators.class)
    @NotNull(message = ApiMessage.REQUIRED_VALIDATION_FAILED, groups = IWebServicesValidators.class)
    @Size(max = Constants.MAX_STRING_SIZE, message = ApiMessage.STRING_SIZE_VALIDATION_FAILED, groups = IWebServicesValidators.class)
    private String lastName;

    @NotEmpty(message = ApiMessage.REQUIRED_VALIDATION_FAILED, groups = IWebServicesValidators.class)
    @NotNull(message = ApiMessage.REQUIRED_VALIDATION_FAILED, groups = IWebServicesValidators.class)
    @Size(max = Constants.MAX_STRING_SIZE, message = ApiMessage.STRING_SIZE_VALIDATION_FAILED, groups = IWebServicesValidators.class)
    private String login;

    private Set<Integer> rolesId;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Set<Integer> getRolesId() {
        return rolesId;
    }

    public void setRolesId(Set<Integer> rolesId) {
        this.rolesId = rolesId;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UserDto [firstName=");
        builder.append(firstName);
        builder.append(", lastName=");
        builder.append(lastName);
        builder.append(", login=");
        builder.append(login);
        builder.append("]");
        return builder.toString();
    }

}
