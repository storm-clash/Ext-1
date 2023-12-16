package com.storm.test.controllers;

import com.storm.test.entities.Base;
import com.storm.test.services.BaseServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

public class BaseControllerImpl <E extends Base, S extends BaseServiceImpl<E,Long>> implements BaseController<E,Long>{
    @Autowired
    protected S service;


    @GetMapping("")
    public ResponseEntity<?> getAll(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.findAll());

        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":Error, Por favor intente mas tarde.\"}");
        }

    }
    @GetMapping("/paged")//Paginacion
    public ResponseEntity<?> getAll(Pageable pageable){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.findAll(pageable));

        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":Error, Por favor intente mas tarde.\"}");
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));

        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":Error, Por favor intente mas tarde.\"}");
        }
    }
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody E entity)  {
        return ResponseEntity
                .accepted()
                .body(service.save(entity));

    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody E entity) throws Exception {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.update(id,entity));

        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":Error, Por favor intente mas tarde.\"}");
        }

    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.delete(id));

        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":Error, Por favor intente mas tarde.\"}");
        }
    }
}
