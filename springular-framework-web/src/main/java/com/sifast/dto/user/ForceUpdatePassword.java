package com.sifast.dto.user;

public class ForceUpdatePassword {

    private int id;

    private String newPassword;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ChangePasswordDto [id=");
        builder.append(id);
        builder.append(", newPassword=");
        builder.append(newPassword);
        builder.append("]");
        return builder.toString();
    }
}
