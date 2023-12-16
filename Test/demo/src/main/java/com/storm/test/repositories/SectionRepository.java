package com.storm.test.repositories;

import com.storm.test.entities.Products;
import com.storm.test.entities.Sections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepository extends BaseRepository<Sections,Long>, JpaSpecificationExecutor<Sections> {

    Page<Sections> findBySizeContaining( double size, Pageable pageable);

   // List<Sections> findBySizeContaining(double size);


    // Optional<Products> findBySectionContaining(@Param("section_id") long id);

    @Query(value = "SELECT p FROM Sections p WHERE p.size LIKE %:price%")
    List<Sections> searchFilter(@Param("price") double price);
}
