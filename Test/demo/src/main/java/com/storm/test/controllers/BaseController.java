package com.storm.test.controllers;

import com.storm.test.dtos.ProductsDTO;
import com.storm.test.entities.Base;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;

public interface BaseController <E extends Base, ID extends Serializable>{

    public ResponseEntity<?> getAll();

    //--------------------------PAGINACION-------------------------------
    public ResponseEntity<?> getAll(Pageable pageable);

    //-------------------------------------------------------------------

    public ResponseEntity<?> getOne(@PathVariable ID id);

    public ResponseEntity<?> save(@RequestBody E entity);


    public ResponseEntity<?> update(@PathVariable ID id, @RequestBody E entity) throws Exception;

    public ResponseEntity<?> delete(@PathVariable ID id) throws Exception;

}
