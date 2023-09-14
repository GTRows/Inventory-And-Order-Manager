package com.gtrows.DistributorOrderSystem.service;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public abstract class GenericService<T> {

    private final MongoRepository<T, String> repository;

    public GenericService(MongoRepository<T, String> repository) {
        this.repository = repository;
    }

    public List<T> getAll() {
        return repository.findAll();
    }

    public Optional<T> getById(String id) {
        return repository.findById(id);
    }

    @Transactional
    public T save(T entity) {
        return repository.save(entity);
    }

    @Transactional
    public void delete(String id) {
        repository.deleteById(id);
    }
}