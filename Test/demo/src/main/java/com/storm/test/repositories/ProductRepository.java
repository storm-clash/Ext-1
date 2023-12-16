package com.storm.test.repositories;

import com.storm.test.entities.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends BaseRepository<Products,Long>, JpaSpecificationExecutor<Products> {

   // List<Products> findByColorContainingOrSizeContainingOrPriceContaining(String color, String size,double price);
    //------------------------------PAGINACION-------------------------------------
    Page<Products> findByColorContainingOrSizeContainingOrPriceContaining(String color, String size,double price ,Pageable pageable);


   // Optional<Products> findBySectionContaining(@Param("section_id") long id);

    @Query(value = "SELECT p FROM Products p WHERE p.color LIKE %:filtro% OR p.price BETWEEN %:filtro% AND %:price%  " +
            "OR p.fragile LIKE %:filtro% OR p.batch LIKE %:filtro%")
    List<Products> searchFilter(@Param("filtro") String filtro,@Param("price") double price);

    @Query(value = "SELECT p FROM Products p WHERE p.color LIKE %:size% OR p.price " +
            "LIKE %:filtro% OR p.fragile LIKE %:filtro% OR p.batch LIKE %:filtro%")
    List<Products> searchFilterPageable(@Param("filtro") String filtro,Pageable pageable);
}
