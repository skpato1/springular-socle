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

    List<T> findByIsDeletedOrderByCreationDateDesc(boolean isDeleted);

    Optional<T> findByIdAndIsDeleted(P id, boolean isDeleted);

    List<T> findByIdInAndIsDeleted(Iterable<Integer> ids, boolean isDeleted);

    List<T> findByIdIn(Iterable<Integer> ids);

    @Transactional
    @Modifying
    @Query("update #{#entityName} e set e.isDeleted = true where e in (:entities)")
    void deleteAllSoft(@Param("entities") Iterable<? extends T> entities);

    @Transactional
    @Modifying
    @Query("update #{#entityName} e set e.isDeleted = true where e = :entity")
    void deleteSoft(@Param("entity") T entity);

    @Override
    @Transactional
    @Modifying
    @Query("select e from #{#entityName} e  where e.isDeleted = false order By e.creationDate DESC")
    List<T> findAll();
}
