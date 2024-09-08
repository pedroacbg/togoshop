package com.pedroacbg.togoshop.tests;

import com.pedroacbg.togoshop.dto.ProductDTO;
import com.pedroacbg.togoshop.entities.Category;
import com.pedroacbg.togoshop.entities.Product;

import java.time.Instant;

public class Factory {

    public static Product createProduct() {
        Product product = new Product(1L, "Desk", "Good desk", 800.0, "https://img.com/img.png", Instant.parse("2024-08-29T03:00:00Z"));
        product.getCategories().add(new Category(4L, "Forniture"));
        return product;
    }

    public static ProductDTO createProductDTO(){
        Product product = createProduct();
        return new ProductDTO(product, product.getCategories());
    }

}
