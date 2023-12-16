package com.storm.test.services;

import com.storm.test.dtos.ProductsDTO;
import com.storm.test.dtos.SectionDTO;
import com.storm.test.entities.Products;
import com.storm.test.entities.Sections;
import com.storm.test.exceptions.CantDeleteException;
import com.storm.test.repositories.BaseRepository;
import com.storm.test.repositories.ProductRepository;
import com.storm.test.repositories.SectionRepository;
import com.storm.test.validator.ObjectsValidator;
import jakarta.transaction.Transactional;
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

public class SectionsServiceImpl extends BaseServiceImpl<Sections,Long> implements SectionsService {

    @Autowired
    private SectionRepository repository;
    @Autowired
    private ProductRepository product;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ObjectsValidator<Sections> validator;
    @Autowired
    private ObjectsValidator<SectionDTO> validatorDTO;

    

    public SectionsServiceImpl(BaseRepository<Sections,Long> baseRepository) {
        super(baseRepository);
    }


    @Override
    public List<SectionDTO> search(double price) throws Exception {
        try{
            List<Sections> sections = repository.searchFilter(price);
            return sections.stream().map(this::convertToDTO).collect(Collectors.toList());

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Page<Sections> search(double filtro, Pageable pageable) throws Exception {
        return null;
    }

    @Override
    public SectionDTO convertToDTO(Sections sections) {
        validator.validate(sections);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        SectionDTO sectionDTO= modelMapper.map(sections,SectionDTO.class);
        return sectionDTO;
    }

    @Override
    public Sections convertDTOtoEntity(SectionDTO sectionDTO) {
        validatorDTO.validate(sectionDTO);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        Sections sections = modelMapper.map(sectionDTO,Sections.class);
        return sections;
    }

    @Transactional
    public boolean delete(Long id)  {
          Optional<Sections> section = repository.findById(id);
          if(section.isEmpty()){
              throw new IllegalArgumentException("There aren't a section with id: "+id);
          }
          List<Products> products = section.get().getProducts().stream().collect(Collectors.toList());
          if (products.isEmpty()) {
            repository.deleteById(id);
            return true;
          }else {
              throw new CantDeleteException("This Section can't be deleted because have products");
          }

    }




}
