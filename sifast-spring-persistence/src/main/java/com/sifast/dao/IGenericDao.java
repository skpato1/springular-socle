package com.sifast.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IGenericDao<T, P extends Serializable> extends JpaRepository<T, P>, JpaSpecificationExecutor<T> {

    List<T> findByIdIn(Iterable<Integer> ids);

    Set<T> findByIdIn(Set<Long> set);

}
