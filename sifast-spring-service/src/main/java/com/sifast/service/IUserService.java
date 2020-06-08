package com.sifast.service;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.sifast.model.User;

@Transactional
public interface IUserService extends IGenericService<User, Integer> {

    Optional<User> findByLogin(String login);

    void forceUserLogout(User user);

}
