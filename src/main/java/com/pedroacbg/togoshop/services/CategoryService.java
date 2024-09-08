package com.pedroacbg.togoshop.services;

import com.pedroacbg.togoshop.dto.CategoryDTO;
import com.pedroacbg.togoshop.entities.Category;
import com.pedroacbg.togoshop.repositories.CategoryRepository;
import com.pedroacbg.togoshop.services.exceptions.DatabaseException;
import com.pedroacbg.togoshop.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAllPaged(Pageable pageable){
        Page<Category> list = repository.findAll(pageable);
        return list.map(x -> new CategoryDTO(x));
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id){
        Optional<Category> obj = repository.findById(id);
        Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found!"));
        return new CategoryDTO(entity);
    }

    @Transactional(readOnly = true)
    public CategoryDTO insert(CategoryDTO obj){
        Category entity = new Category();
        copyToDto(obj, entity);
        entity = repository.save(entity);
        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO obj) {
        try {
            Category entity = repository.getReferenceById(id);
            copyToDto(obj, entity);
            entity = repository.save(entity);
            return new CategoryDTO(entity);
        }catch(EntityNotFoundException e){
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Resource not found, ID: " + id);
        }
        try{
            repository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DatabaseException("Reference integrity failure");
        }
    }

    private void copyToDto(CategoryDTO dto, Category entity){
        entity.setName(dto.getName());
    }
}
