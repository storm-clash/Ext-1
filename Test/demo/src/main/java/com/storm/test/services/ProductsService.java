package com.storm.test.services;

import com.storm.test.dtos.ProductsDTO;
import com.storm.test.entities.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductsService extends BaseService<Products,Long>{

    List<ProductsDTO> search(String filtro,double price) throws Exception;

    public ProductsDTO save(ProductsDTO entity);

    public ProductsDTO update(Long id, ProductsDTO entity);

    public boolean deleteProduct (Long id) ;





    //--------------------------PAGINACION-----------------------
    Page<Products> search(String filtro, Pageable pageable) throws Exception;

    public ProductsDTO convertToDTO(Products products);

    public Products convertDTOtoEntity(ProductsDTO productDTO);


}
