package com.sifast.service.impl;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.sifast.common.Constants;
import com.sifast.dao.IGenericDao;
import com.sifast.enumeration.LogicalDeleteEnum;
import com.sifast.service.IGenericService;

public class GenericService<T, P extends Serializable> implements IGenericService<T, P> {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(Constants.PATTERN_DATE);

    @Autowired
    private IGenericDao<T, P> genericDao;

    @Override
    public void deleteById(P id) {
        genericDao.deleteById(id);
    }

    @Override
    public void delete(T entity) {
        genericDao.deleteSoft(entity);
    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        if (entities != null && IterableUtils.size(entities) > 0) {
            genericDao.deleteAllSoft(entities);
        }
    }

    @Override
    public void deleteInBatch(Iterable<T> entities) {
        genericDao.deleteInBatch(entities);

    }

    @Override
    public void deleteAll() {
        genericDao.deleteAll();
    }

    @Override
    public void deleteAllInBatch() {
        genericDao.deleteAllInBatch();
    }

    @Override
    public Optional<T> findById(P id, LogicalDeleteEnum... isDeleted) {
        Optional<T> returnedObject;
        if (isDeleted.length > 0) {
            switch (isDeleted[0]) {
            case TRUE:
                returnedObject = genericDao.findByIdAndIsDeleted(id, true);
                break;
            case ALL:
                returnedObject = genericDao.findById(id);
                break;
            default:
                returnedObject = genericDao.findByIdAndIsDeleted(id, false);
                break;
            }
        } else {
            returnedObject = genericDao.findByIdAndIsDeleted(id, false);
        }
        return returnedObject;

    }

    @Override
    public T getOne(P id) {
        return genericDao.getOne(id);
    }

    @Override
    public boolean existsById(P id) {
        return genericDao.existsById(id);
    }

    @Override
    public List<T> findAll() {
        return genericDao.findAll();
    }

    @Override
    public List<T> findAllById(Iterable<P> ids) {
        return genericDao.findAllById(ids);
    }

    @Override
    public List<T> findAll(Sort sort) {
        return genericDao.findAll(sort);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return genericDao.findAll(pageable);
    }

    @Override
    public Optional<T> findOne(Specification<T> spec) {
        return genericDao.findOne(spec);
    }

    @Override
    public List<T> findAll(Specification<T> spec) {
        return genericDao.findAll(spec);
    }

    @Override
    public Page<T> findAll(Specification<T> spec, Pageable pageable) {
        return genericDao.findAll(spec, pageable);
    }

    @Override
    public List<T> findAll(Specification<T> spec, Sort sort) {
        return genericDao.findAll(spec, sort);
    }

    @Override
    public <S extends T> Optional<S> findOne(Example<S> example) {
        return genericDao.findOne(example);
    }

    @Override
    public <S extends T> long count(Example<S> example) {
        return genericDao.count(example);
    }

    @Override
    public <S extends T> boolean exists(Example<S> example) {
        return genericDao.exists(example);
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example) {
        return genericDao.findAll(example);
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
        return genericDao.findAll(example, sort);
    }

    @Override
    public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
        return genericDao.findAll(example, pageable);
    }

    @Override
    public int countEntities() {
        return (int) genericDao.count();
    }

    @Override
    public long count(Specification<T> spec) {
        return genericDao.count(spec);
    }

    @Override
    public <S extends T> S save(S entity) {
        return genericDao.save(entity);
    }

    @Override
    public <S extends T> S saveAndFlush(S entity) {
        return genericDao.saveAndFlush(entity);
    }

    @Override
    public <S extends T> List<S> saveAll(Iterable<S> entities) {
        return genericDao.saveAll(entities);
    }

    @Override
    public void flush() {
        genericDao.flush();
    }

    @Override
    public T update(T entity) {
        return genericDao.save(entity);
    }

    @Override
    public List<T> findByIsDeleted(LogicalDeleteEnum... isDeleted) {
        List<T> returnedList;
        if (isDeleted.length > 0) {
            switch (isDeleted[0]) {
            case TRUE:
                returnedList = genericDao.findByIsDeletedOrderByCreationDateDesc(true);
                break;
            case ALL:
                returnedList = genericDao.findAll();
                break;
            default:
                returnedList = genericDao.findByIsDeletedOrderByCreationDateDesc(false);
                break;
            }
        } else {
            returnedList = genericDao.findByIsDeletedOrderByCreationDateDesc(false);
        }
        return returnedList;
    }

    @Override
    public List<T> findByIdIn(Iterable<Integer> ids, LogicalDeleteEnum... isDeleted) {
        List<T> returnedList;
        if (isDeleted.length > 0) {
            switch (isDeleted[0]) {
            case TRUE:
                returnedList = genericDao.findByIdInAndIsDeleted(ids, true);
                break;
            case ALL:
                returnedList = genericDao.findByIdIn(ids);
                break;
            default:
                returnedList = genericDao.findByIdInAndIsDeleted(ids, false);
                break;
            }
        } else {
            returnedList = genericDao.findByIdInAndIsDeleted(ids, false);
        }
        return returnedList;
    }

    @Override
    public T validate(T entity) {
        return genericDao.save(entity);
    }

    @Override
    public List<T> validateAll(Iterable<T> entities) {
        return genericDao.saveAll(entities);
    }

}
