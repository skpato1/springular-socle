package com.sifast.service;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.sifast.enumeration.LogicalDeleteEnum;
import com.sifast.model.User;

@Transactional
public interface IUserService extends IGenericService<User, Integer> {

    Optional<User> findByLogin(String login, LogicalDeleteEnum... isDeleted);

    void forceUserLogout(User user);

}
