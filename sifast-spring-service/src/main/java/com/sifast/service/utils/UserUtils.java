package com.sifast.service.utils;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sifast.common.ApiMessage;
import com.sifast.model.User;
import com.sifast.service.IUserService;

public class UserUtils {

    @SuppressWarnings("unchecked")
    public static User getConnectedUser(IUserService userService) {
        HttpSession session = getRequestedSession();
        Optional<User> connectedUser = Optional.empty();
        if (session != null) {
            if (session.getAttribute(ApiMessage.CONNECTED_USER) != null) {
                connectedUser = (Optional<User>) session.getAttribute(ApiMessage.CONNECTED_USER);
            } else {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                connectedUser = userService.findByLogin(authentication.getName());
                session.setAttribute(ApiMessage.CONNECTED_USER, connectedUser);
            }
        }
        if (connectedUser.isPresent()) {
            return connectedUser.get();
        } else {
            return null;
        }

    }

    public static HttpSession getRequestedSession() {
        HttpSession httpSession = null;
        if (RequestContextHolder.getRequestAttributes() != null) {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        }
        return httpSession;
    }
}
