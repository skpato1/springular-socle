package com.sifast.service;

import org.springframework.transaction.annotation.Transactional;

import com.sifast.model.Authority;

@Transactional
public interface IAuthorityService extends IGenericService<Authority, Integer> {

    Authority findByDesignation(String designation);
}
