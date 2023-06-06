package com.allene.lease.dao;

import com.allene.lease.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModelRepository extends JpaRepository<Model, Long> {

    List<Model> findALlByBrandId(long id);

    void deleteByBrandId(long id);
}