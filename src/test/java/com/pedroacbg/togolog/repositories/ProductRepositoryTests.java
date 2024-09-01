package com.pedroacbg.togolog.repositories;

import com.pedroacbg.togolog.entities.Product;
import com.pedroacbg.togolog.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {

    private Long existingId;
    private Long nonExistingId;
    private Long countTotalProducts;

    @Autowired
    private ProductRepository repository;

    @BeforeEach
    void setUp() throws Exception{
        existingId = 1L;
        nonExistingId = 2434L;
        countTotalProducts = 25L;
    }

    @Test
    public void saveShouldPersistWithAutoincrementWhenIdIsNull(){
        Product entity = Factory.createProduct();
        entity.setId(null);
        entity = repository.save(entity);
        Assertions.assertNotNull(entity.getId());
        Assertions.assertEquals(countTotalProducts + 1, entity.getId());
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists(){
        repository.deleteById(existingId);
        Optional<Product> result = repository.findById(existingId);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void findByIdShouldReturnNonEmptyOptionalWhenIdExists(){
        Optional<Product> entity = repository.findById(existingId);
        Assertions.assertTrue(entity.isPresent());
    }

    @Test
    public void findByIdShouldReturnEmptyOptionalWhenIdDoesNotExists(){
        Optional<Product> entity = repository.findById(nonExistingId);
        Assertions.assertTrue(entity.isEmpty());
    }

}
