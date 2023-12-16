package com.storm.test.controllers;

import com.storm.test.dtos.ProductsDTO;
import com.storm.test.dtos.RequestDTO;
import com.storm.test.entities.Products;
import com.storm.test.entities.Sections;
import com.storm.test.repositories.ProductRepository;
import com.storm.test.repositories.SectionRepository;
import com.storm.test.services.FiltersSpecifications;
import com.storm.test.services.ProductsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/products")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
public class ProductsController  implements ProductControllerI{

    private final ProductsServiceImpl productsService;

    private final ProductRepository repository;

    private final FiltersSpecifications<Products> sectionsFiltersSpecifications;



    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody ProductsDTO entity){

        return ResponseEntity
                .accepted()
                .body(productsService.save(entity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody ProductsDTO entity) {

        return ResponseEntity
                .accepted()
                .body(productsService.update(id,entity));



    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(productsService.deleteProduct(id));

        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":Error, Por favor intente mas tarde.\"}");
        }
    }

    @PostMapping("/filters")
    public List<Products> getSections(@RequestBody RequestDTO requestDTO){
        Specification<Products> serachSpecification = sectionsFiltersSpecifications.getSearch(requestDTO.getSearchRequestDTO(),requestDTO.getGlobalOperator());
        return repository.findAll(serachSpecification);
    }

}
