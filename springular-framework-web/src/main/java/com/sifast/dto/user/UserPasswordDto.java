package com.sifast.dto.user;

public class UserPasswordDto {

    private String login;

    private String password;

    public UserPasswordDto(String password, String login) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UserPasswordDto [login=");
        builder.append(login);
        builder.append(", password=");
        builder.append(password);
        builder.append("]");
        return builder.toString();
    }

}
