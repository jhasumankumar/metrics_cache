package com.sj.repository;


import com.sj.model.AbstractModel;
import com.sj.model.UserModel;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@NoRepositoryBean
public class GenericDaoImpl<T extends AbstractModel> implements GenericDao<T> {


    protected static final org.slf4j.Logger LOG = LoggerFactory.getLogger(GenericDaoImpl.class);

    @PersistenceContext
    protected EntityManager em;

    private Class<T> type;

    public GenericDaoImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        //noinspection unchecked
        type = (Class) pt.getActualTypeArguments()[0];
    }

    @Override
    public List<T> findAll() {
        return Collections.emptyList();
    }


    @Override
    public void delete(T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Iterable<? extends T> iterable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends T> S save(S s) {
        em.persist(s);
        return s;
    }

    @Override
    public <S extends T> List<S> save(Iterable<S> iterable) {
        List<S> result = new ArrayList<>();
        for (S entity : iterable) {
            em.persist(entity);
            result.add(entity);
        }
        return result;
    }


    @Override
    public T findOne(Long aLong) {
        return em.find(type, aLong);
    }

    @Override
    public boolean exists(Long aLong) {
        return false;
    }

    @Override
    public Iterable<T> findAll(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(Long aLong) {
        T t= findOne(aLong);
        if(t == null){
            em.remove(t);
        }

    }

}
