package com.sifast.web.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sifast.dao.IUserDao;
import com.sifast.model.User;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserDao userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userRepository.findByLogin(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException(username);
        }
        return new MyUserPrincipal(user.get());
    }
}
