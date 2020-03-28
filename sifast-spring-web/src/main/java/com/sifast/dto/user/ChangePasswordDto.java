package com.sifast.dto.user;

public class ChangePasswordDto extends ForceUpdatePassword {

    private String oldPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ChangePasswordDto [oldPassword=");
        builder.append(oldPassword);
        builder.append("]");
        return builder.toString();
    }

}
