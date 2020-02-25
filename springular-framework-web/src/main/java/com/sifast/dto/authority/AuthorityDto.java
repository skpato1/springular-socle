package com.sifast.dto.authority;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.sifast.common.ApiMessage;
import com.sifast.common.Constants;
import com.sifast.common.utils.IWebServicesValidators;

public class AuthorityDto {

    @NotEmpty(message = ApiMessage.REQUIRED_VALIDATION_FAILED, groups = IWebServicesValidators.class)
    @NotNull(message = ApiMessage.REQUIRED_VALIDATION_FAILED, groups = IWebServicesValidators.class)
    @Size(max = Constants.MAX_STRING_SIZE, message = ApiMessage.STRING_SIZE_VALIDATION_FAILED, groups = IWebServicesValidators.class)
    private String designation;

    @NotEmpty(message = ApiMessage.REQUIRED_VALIDATION_FAILED, groups = IWebServicesValidators.class)
    @NotNull(message = ApiMessage.REQUIRED_VALIDATION_FAILED, groups = IWebServicesValidators.class)
    @Size(max = Constants.MAX_STRING_SIZE, message = ApiMessage.STRING_SIZE_VALIDATION_FAILED, groups = IWebServicesValidators.class)
    private String description;

    private int id;

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
