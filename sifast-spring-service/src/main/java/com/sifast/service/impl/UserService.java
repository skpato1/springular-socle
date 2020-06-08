package com.sifast.service.impl;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import com.sifast.dao.IUserDao;
import com.sifast.model.User;
import com.sifast.service.IUserService;

@Service
public class UserService extends GenericService<User, Integer> implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Resource(name = "tokenStore")
    TokenStore tokenStore;

    @Resource(name = "tokenServices")
    ConsumerTokenServices tokenServices;

    @Override
    public Optional<User> findByLogin(String login) {
        Optional<User> user;
        user = userDao.findByLogin(login);
        return user;
    }

    @Override
    public void forceUserLogout(User user) {
        OAuth2Authentication authentication = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
        String clientId = authentication.getOAuth2Request().getClientId();
        tokenStore.findTokensByClientIdAndUserName(clientId, user.getLogin()).stream().forEach(token -> tokenServices.revokeToken(token.getValue()));
    }

}
