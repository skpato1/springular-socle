package com.sifast.dto.user;

import java.util.Set;
import java.util.stream.Collectors;

import com.sifast.model.User;

public class UserInfoDto {
    private int id;
    private String login;
    private Set<String> authorities;
    private String firstName;
    private String lastName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

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

    public UserInfoDto(User user) {
        this.authorities = user.getRoles().stream().map(role -> role.getAuthorities()).flatMap(authority -> authority.stream()).map(auth -> auth.getDesignation().toLowerCase())
                .collect(Collectors.toSet());
        this.login = user.getLogin();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.id = user.getId();
    }
}
