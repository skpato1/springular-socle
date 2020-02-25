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
    public Authority findByDesignation(String designation, LogicalDeleteEnum... isDeleted) {
        Authority authority;
        if (isDeleted.length > 0) {
            switch (isDeleted[0]) {
            case TRUE:
                authority = authorityDao.findByDesignationAndIsDeleted(designation, true);
                break;
            case ALL:
                authority = authorityDao.findByDesignation(designation);
                break;
            default:
                authority = authorityDao.findByDesignationAndIsDeleted(designation, false);
                break;
            }
        } else {
            authority = authorityDao.findByDesignationAndIsDeleted(designation, false);
        }
        return authority;
    }

}
