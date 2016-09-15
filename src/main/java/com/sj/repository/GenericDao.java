package com.sj.repository;

import com.sj.model.AbstractModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericDao<T extends AbstractModel>  extends CrudRepository<T, Long> {
}
