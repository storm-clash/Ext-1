package com.storm.test.services;

import com.storm.test.entities.Products;
import com.storm.test.entities.Type;
import com.storm.test.repositories.BaseRepository;
import com.storm.test.repositories.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TypeServiceImpl extends BaseServiceImpl<Type,Long> implements TypeService{

    @Autowired
    private TypeRepository repository;

    public TypeServiceImpl(BaseRepository<Type,Long> baseRepository) {
        super(baseRepository);
    }

    @Override
    public List<Products> search(String filtro) throws Exception {
        return null;
    }

    @Override
    public Page<Products> search(String filtro, Pageable pageable) throws Exception {
        return null;
    }
}
