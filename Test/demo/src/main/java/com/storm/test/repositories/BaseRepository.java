package com.storm.test.repositories;

import com.storm.test.entities.Base;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepository <E extends Base,ID extends Serializable> extends JpaRepository<E,ID> {
}
