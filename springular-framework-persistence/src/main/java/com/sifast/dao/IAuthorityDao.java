package com.sifast.dao;

import org.springframework.stereotype.Repository;

import com.sifast.model.Authority;

@Repository
public interface IAuthorityDao extends IGenericDao<Authority, Integer> {

    Authority findByDesignationAndIsDeleted(String designation, boolean isDeleted);

    Authority findByDesignation(String designation);

}
