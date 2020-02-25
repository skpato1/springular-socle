package com.sifast.service;

import org.springframework.transaction.annotation.Transactional;

import com.sifast.model.Role;
import com.sifast.model.User;

@Transactional
public interface IRoleService extends IGenericService<Role, Integer> {

    void forceRoleLogout(Role role);

    void forceUserLogout(User user);
}
