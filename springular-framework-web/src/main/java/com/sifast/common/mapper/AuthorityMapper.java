package com.sifast.common.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sifast.dto.authority.AuthorityDto;
import com.sifast.dto.authority.ViewAuthorityDto;
import com.sifast.model.Authority;
import com.sifast.web.config.ConfiguredModelMapper;

@Component
public class AuthorityMapper {

    @Autowired
    private ConfiguredModelMapper modelMapper;

    public List<ViewAuthorityDto> mapAuthoritiesToViewAuthorityDtos(List<Authority> authorities) {
        List<ViewAuthorityDto> viewAuthorities = new ArrayList<>();
        List<Authority> parents = authorities.stream().filter(authority -> !authority.isDisplayed()).collect(Collectors.toList());
        for (Authority parent : parents) {
            ViewAuthorityDto viewAuthorityDto = new ViewAuthorityDto();
            viewAuthorityDto.setParent(modelMapper.map(parent, AuthorityDto.class));
            authorities.removeAll(parents);
            List<Authority> childrens = authorities.stream().filter(authority -> authority.getParent().getId() == parent.getId()).collect(Collectors.toList());
            viewAuthorityDto.setChildren(childrens.stream().map(children -> modelMapper.map(children, AuthorityDto.class)).collect(Collectors.toList()));
            viewAuthorities.add(viewAuthorityDto);
        }
        return viewAuthorities;
    }
}
