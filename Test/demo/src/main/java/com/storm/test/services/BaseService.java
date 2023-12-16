package com.storm.test.services;

import com.storm.test.entities.Base;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public interface BaseService<E extends Base, ID extends Serializable> {
    public List<E> findAll()throws Exception;

    //PAGINACION
    public Page<E> findAll(Pageable pageable) throws Exception;

//------------------------------------------------------------------------

    public E findById(ID id)throws Exception;

    public E save(E entity);

    public E update(ID id, E entity) throws Exception;

    public boolean delete(ID id) throws Exception;
}
