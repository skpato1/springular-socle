package com.sifast.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

@NoRepositoryBean
public interface IGenericDao<T, P extends Serializable> extends JpaRepository<T, P>, JpaSpecificationExecutor<T> {


    List<T> findByIdIn(Iterable<Integer> ids);

}
