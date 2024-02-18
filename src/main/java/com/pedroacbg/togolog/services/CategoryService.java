package com.pedroacbg.togolog.services;

import com.pedroacbg.togolog.entities.Category;
import com.pedroacbg.togolog.repositories.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Category> findAll(){
        return repository.findAll();
    }


}
