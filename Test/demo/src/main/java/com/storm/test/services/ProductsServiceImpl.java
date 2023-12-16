package com.storm.test.services;

import com.storm.test.dtos.ProductsDTO;
import com.storm.test.entities.Products;
import com.storm.test.entities.Sections;
import com.storm.test.entities.Type;
import com.storm.test.exceptions.CantDeleteException;
import com.storm.test.repositories.BaseRepository;
import com.storm.test.repositories.ProductRepository;
import com.storm.test.repositories.SectionRepository;
import com.storm.test.repositories.TypeRepository;
import com.storm.test.validator.ObjectsValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class ProductsServiceImpl extends BaseServiceImpl<Products,Long> implements ProductsService {
    @Autowired
    private ProductRepository repository;

    @Autowired
    private ObjectsValidator<Products> validator;
    @Autowired
    private ObjectsValidator<ProductsDTO> validatorDTO;
    @Autowired
    private SectionRepository sectionsrepository;
    @Autowired
    private ModelMapper modelMapper;




    public ProductsServiceImpl(BaseRepository<Products,Long> baseRepository) {
        super(baseRepository);
    }


    @Override
    public List<ProductsDTO> search(String filtro,double price) throws Exception {
        try{
            List<Products> products = repository.searchFilter(filtro,price);
            return products.stream().map(this::convertToDTO).collect(Collectors.toList());

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    @Transactional
    @Override
    public ProductsDTO save(ProductsDTO entity) {
        validatorDTO.validate(entity);
        Products product = convertDTOtoEntity(entity);
        Optional<Sections> section = sectionsrepository.findById(product.getSections().getId());
        if(section.isEmpty()){
            throw new IllegalArgumentException("Missing Section");
        }

        if((product.getSize() * product.getAmount())>section.get().getSize()){
            throw new IllegalArgumentException("Size should be less that: "+section.get().getSize()+", The size for the: "+section.get().getTypeProduct().getName() +" section");
        }
        Products productsCreated = repository.save(product);

        return convertToDTO(productsCreated);
    }

    @Transactional
    @Override
    public ProductsDTO update(Long id, ProductsDTO entity) {
        validatorDTO.validate(entity);
        Optional<Products> entityOptional = repository.findById(id);

        if(entityOptional.isEmpty()){
            throw new IllegalArgumentException("There is no such product");
        }
        Products product = convertDTOtoEntity(entity);
        Optional<Sections> section = sectionsrepository.findById(product.getSections().getId());
        if(section.isEmpty()){
            throw new IllegalArgumentException("Missing Section");
        }
        if((product.getSize() * product.getAmount())>section.get().getSize()){
            throw new IllegalArgumentException("Size should be less that: "+section.get().getSize()+", The size for the: "+section.get().getTypeProduct().getName() +" section");
        }
        return convertToDTO(repository.save(product));
    }
    @Transactional
    @Override
    public boolean deleteProduct(Long id) {


            Optional<Products> products = repository.findById(id);
            if(products.isEmpty()){
                throw new IllegalArgumentException("There aren't a products with id: "+id);
            }

                repository.deleteById(id);
                return true;

    }

    @Override
    public Page<Products> search(String filtro, Pageable pageable) throws Exception {
        try {
            Page<Products> products = repository.findByColorContainingOrSizeContainingOrPriceContaining(filtro,filtro, Double.parseDouble(filtro),pageable);
            return products;

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public ProductsDTO convertToDTO(Products products) {
        validator.validate(products);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        ProductsDTO productsDTO = modelMapper.map(products,ProductsDTO.class);
        return productsDTO;
    }

    @Override
    public Products convertDTOtoEntity(ProductsDTO productDTO) {
        validatorDTO.validate(productDTO);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        Products product = modelMapper.map(productDTO,Products.class);
        return product;
    }

    @Transactional
    public Products save(Products product){

        Optional<Sections> section = sectionsrepository.findById(product.getSections().getId());
        if(section.isEmpty()){
            throw new IllegalArgumentException("Missing Section");
        }
        validator.validate(product);
        if((product.getSize() * product.getAmount())>section.get().getSize()){
            throw new IllegalArgumentException("Size should be less that: "+section.get().getSize()+", The size for the: "+section.get().getTypeProduct().getName() +" section");
        }
        product = repository.save(product);
        return product;

    }

}
