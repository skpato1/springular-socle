package com.sifast.service.impl;

import java.util.Iterator;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import com.sifast.dao.IUserDao;
import com.sifast.model.Role;
import com.sifast.model.User;
import com.sifast.service.IRoleService;

@Service
public class RoleService extends GenericService<Role, Integer> implements IRoleService {

    @Autowired
    private IUserDao userDao;

    @Resource(name = "tokenStore")
    TokenStore tokenStore;

    @Resource(name = "tokenServices")
    ConsumerTokenServices tokenServices;

    @Override
    public void forceRoleLogout(Role role) {
        userDao.findByRolesAndIsDeleted(role, false).stream().forEach(user -> forceUserLogout(user));
    }

    @Override
    public void forceUserLogout(User user) {
        OAuth2Authentication authentication = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
        String clientId = authentication.getOAuth2Request().getClientId();
        Iterator<OAuth2AccessToken> tokens = tokenStore.findTokensByClientIdAndUserName(clientId, user.getLogin()).iterator();
        while (tokens.hasNext()) {
            tokenServices.revokeToken(tokens.next().getValue());
        }
    }
}
