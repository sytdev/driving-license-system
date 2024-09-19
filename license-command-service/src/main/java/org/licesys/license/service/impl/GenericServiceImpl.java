package org.licesys.license.service.impl;

import lombok.RequiredArgsConstructor;
import org.licesys.license.repository.GenericRepository;
import org.licesys.license.service.GenericService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

public abstract class GenericServiceImpl<T, ID> implements GenericService<T, ID> {

    protected final GenericRepository<T, ID> genericRepository;

    public GenericServiceImpl(final GenericRepository<T, ID> genericRepository) {
        this.genericRepository = genericRepository;
    }

    @Override
    public List<T> findAll() {
        return genericRepository.findAll();
    }

    @Override
    public void save(T t) {
        genericRepository.save(t);
    }

    @Override
    public Optional<T> findById(ID id) {
        return genericRepository.findById(id);
    }

    @Override
    public T update(ID id, T t) {
        return genericRepository.findById(id).map(e -> genericRepository.save(t)).orElse(t);
    }

    @Override
    public void delete(ID id) {
        genericRepository.deleteById(id);
    }
}
