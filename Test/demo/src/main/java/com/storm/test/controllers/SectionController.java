package com.storm.test.controllers;

import com.storm.test.dtos.RequestDTO;
import com.storm.test.entities.Products;
import com.storm.test.entities.Sections;
import com.storm.test.repositories.SectionRepository;
import com.storm.test.services.FiltersSpecifications;
import com.storm.test.services.SectionsServiceImpl;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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
@RequestMapping (path = "api/v1/sections")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
public class SectionController extends BaseControllerImpl<Sections, SectionsServiceImpl>{


    private final SectionsServiceImpl section;

    private final SectionRepository repository;

    private final FiltersSpecifications<Sections> sectionsFiltersSpecifications;

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(service.delete(id));
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam double price){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.search(price));

        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }

    @GetMapping("/searchPaged")
    public ResponseEntity<?> search(@RequestParam double filtro, Pageable pageable){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.search(filtro,pageable));

        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }

    @PostMapping("/filters")
    public List<Sections> getSections(@RequestBody RequestDTO requestDTO){
       Specification<Sections> serachSpecification = sectionsFiltersSpecifications.getSearch(requestDTO.getSearchRequestDTO(),requestDTO.getGlobalOperator());
       return repository.findAll(serachSpecification);
        }

}
