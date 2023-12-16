package com.storm.test.services;


import com.storm.test.dtos.SectionDTO;
import com.storm.test.entities.Sections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SectionsService extends BaseService<Sections,Long> {
    List<SectionDTO> search(double filtro) throws Exception;



    //--------------------------PAGINACION-----------------------
    Page<Sections> search(double filtro, Pageable pageable) throws Exception;

    public SectionDTO convertToDTO(Sections sections);

    public Sections convertDTOtoEntity(SectionDTO sectionDTO);

}
