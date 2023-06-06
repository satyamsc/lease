package com.allene.lease.service;

import java.util.List;

public interface GenericService<E, M> {
    E save(E dto);

    void deleteById(M id);

    E findById(M id);

    List<E> findAll();

    E update(E dto, M id);
}