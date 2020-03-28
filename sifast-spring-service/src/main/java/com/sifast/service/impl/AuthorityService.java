package com.sifast.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sifast.dao.IAuthorityDao;
import com.sifast.enumeration.LogicalDeleteEnum;
import com.sifast.model.Authority;
import com.sifast.service.IAuthorityService;

@Service
public class AuthorityService extends GenericService<Authority, Integer> implements IAuthorityService {

    @Autowired
    private IAuthorityDao authorityDao;

    @Override
    public Authority findByDesignation(String designation) {
        Authority authority;
        authority = authorityDao.findByDesignation(designation);
        return authority;
    }

}
