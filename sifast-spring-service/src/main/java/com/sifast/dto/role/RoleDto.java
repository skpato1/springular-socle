package com.sifast.dto.role;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.sifast.common.ApiMessage;
import com.sifast.common.Constants;
import com.sifast.common.utils.IWebServicesValidators;

public class RoleDto {

    @NotEmpty(message = ApiMessage.REQUIRED_VALIDATION_FAILED, groups = IWebServicesValidators.class)
    @NotNull(message = ApiMessage.REQUIRED_VALIDATION_FAILED, groups = IWebServicesValidators.class)
    @Size(max = Constants.MAX_STRING_SIZE, message = ApiMessage.STRING_SIZE_VALIDATION_FAILED, groups = IWebServicesValidators.class)
    private String designation;

    @NotEmpty(message = ApiMessage.REQUIRED_VALIDATION_FAILED, groups = IWebServicesValidators.class)
    @NotNull(message = ApiMessage.REQUIRED_VALIDATION_FAILED, groups = IWebServicesValidators.class)
    @Size(max = Constants.MAX_STRING_SIZE, message = ApiMessage.STRING_SIZE_VALIDATION_FAILED, groups = IWebServicesValidators.class)
    private String description;

    private Set<Integer> authoritiesId;

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

    public Set<Integer> getAuthoritiesId() {
        return authoritiesId;
    }

    public void setAuthoritiesId(Set<Integer> authoritiesId) {
        this.authoritiesId = authoritiesId;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RoleDto [designation=");
        builder.append(designation);
        builder.append(", description=");
        builder.append(description);
        builder.append(", authoritiesId=");
        builder.append(authoritiesId);
        builder.append("]");
        return builder.toString();
    }

}
