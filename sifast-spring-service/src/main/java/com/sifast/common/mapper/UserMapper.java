package com.sifast.common.mapper;

import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.sifast.dto.user.CreateUserDto;
import com.sifast.dto.user.UserDto;
import com.sifast.dto.user.ViewUserDto;
import com.sifast.model.Role;
import com.sifast.model.User;
import com.sifast.service.IRoleService;
import com.sifast.service.config.ConfiguredModelMapper;

@Component
public class UserMapper {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private ConfiguredModelMapper modelMapper;

    public User mapCreateUser(CreateUserDto userDto) {
        User mappedUser = modelMapper.map(userDto, User.class);
        mappedUser.setRoles(new HashSet<>(roleService.findByIdIn(userDto.getRolesId())));
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        mappedUser.setPassword(encoder.encode(userDto.getPassword()));
        mappedUser.setEnabled(true);
        return mappedUser;
    }

    public ViewUserDto mapUserToViewUserDto(User user) {
        ViewUserDto viewUserDto = modelMapper.map(user, ViewUserDto.class);
        viewUserDto.setRolesId(user.getRoles().stream().map(Role::getId).collect(Collectors.toSet()));
        return viewUserDto;
    }

    public User mapUpdateUser(UserDto userDto, Optional<User> user, int id) {
        User preUpdateUser = modelMapper.map(userDto, User.class);
        preUpdateUser.setRoles(new HashSet<>(roleService.findByIdIn(userDto.getRolesId())));
        preUpdateUser.setId(id);
        preUpdateUser.setPassword(user.isPresent() ? user.get().getPassword() : null);
        preUpdateUser.setEnabled(true);
        return preUpdateUser;
    }
}
