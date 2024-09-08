package com.pedroacbg.togoshop.services;

import com.pedroacbg.togoshop.dto.CategoryDTO;
import com.pedroacbg.togoshop.dto.ProductDTO;
import com.pedroacbg.togoshop.entities.Category;
import com.pedroacbg.togoshop.entities.Product;
import com.pedroacbg.togoshop.repositories.CategoryRepository;
import com.pedroacbg.togoshop.repositories.ProductRepository;
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
public class ProductService {

    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository repository, CategoryRepository categoryRepository) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(Pageable pageable){
        Page<Product> list = repository.findAll(pageable);
        return list.map(x -> new ProductDTO(x));
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){
        Optional<Product> obj = repository.findById(id);
        Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found!"));
        return new ProductDTO(entity, entity.getCategories());
    }

    @Transactional(readOnly = true)
    public ProductDTO insert(ProductDTO obj){
        Product entity = new Product();
        copyToDto(obj, entity);
        entity = repository.save(entity);
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO obj) {
        try {
            Product entity = repository.getReferenceById(id);
            copyToDto(obj, entity);
            entity = repository.save(entity);
            return new ProductDTO(entity);
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

    private void copyToDto(ProductDTO dto, Product entity){
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setDescription(dto.getDescription());
        entity.setImgUrl(dto.getImgUrl());
        entity.setDate(dto.getDate());

        entity.getCategories().clear();
        for(CategoryDTO obj : dto.getCategories()){
            Category category = categoryRepository.getReferenceById(obj.getId());
            entity.getCategories().add(category);
        }
    }

}
