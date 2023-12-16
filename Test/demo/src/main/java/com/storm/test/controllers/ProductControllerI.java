package com.storm.test.controllers;

import com.storm.test.dtos.ProductsDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ProductControllerI {

    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProductsDTO entity);
    public ResponseEntity<?> save(@RequestBody ProductsDTO entity);
}
