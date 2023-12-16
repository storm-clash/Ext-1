package com.storm.test.services;

import com.storm.test.entities.Products;
import com.storm.test.entities.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService extends BaseService<Type,Long>{

    List<Products> search(String filtro) throws Exception;



    //--------------------------PAGINACION-----------------------
    Page<Products> search(String filtro, Pageable pageable) throws Exception;
}
