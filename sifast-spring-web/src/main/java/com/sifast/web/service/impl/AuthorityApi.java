package com.sifast.web.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sifast.common.mapper.AuthorityMapper;
import com.sifast.model.Authority;
import com.sifast.service.IAuthorityService;
import com.sifast.web.service.api.IAuthorityApi;

import io.swagger.annotations.Api;

@RestController
@CrossOrigin("*")
@Api(value = "authority api", tags = "authority-api")
@RequestMapping(value = "/api/")
public class AuthorityApi implements IAuthorityApi {

    @Autowired
    private IAuthorityService authorityService;

    @Autowired
    private AuthorityMapper authorityMapper;

    private Object httpResponseBody;

    private HttpStatus httpStatus;

    @Override
    public ResponseEntity<Object> getAllAuthorities() {
        List<Authority> authorities = authorityService.findAll();
        httpStatus = HttpStatus.OK;
        httpResponseBody = !authorities.isEmpty() ? authorityMapper.mapAuthoritiesToViewAuthorityDtos(authorities) : Collections.emptyList();
        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }

}
