package com.sifast.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.sifast.model.Role;
import com.sifast.model.User;

@Repository
public interface IUserDao extends IGenericDao<User, Integer> {

    Optional<User> findByLoginAndIsDeleted(String login, boolean isDeleted);

    Optional<User> findByLogin(String login);

    List<User> findByRolesAndIsDeleted(Role role, boolean isDeleted);
}
