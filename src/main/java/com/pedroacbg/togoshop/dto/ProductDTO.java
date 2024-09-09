package com.pedroacbg.togoshop.dto;

import com.pedroacbg.togoshop.entities.Category;
import com.pedroacbg.togoshop.entities.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class ProductDTO {

    private Long id;

    @Size(min = 3,max = 30,message = "O campo nome deve ter entre 3 e 30 caracteres")
    @NotBlank(message = "O campo nome é obrigatório")
    private String name;

    @NotBlank(message = "O campo nome é obrigatório")
    private String description;

    @Positive(message = "O preço deve ser um valor positivo.")
    private Double price;
    private String imgUrl;

    @PastOrPresent(message = "A data do produto não pode ser futura.")
    private Instant date;

    private List<CategoryDTO> categories = new ArrayList<>();

    public ProductDTO(){}

    public ProductDTO(Long id, String name, String description, Double price, String imgUrl, Instant date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
        this.date = date;
    }

    public ProductDTO(Product entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.price = entity.getPrice();
        this.imgUrl = entity.getImgUrl();
        this.date = entity.getDate();
    }

    public ProductDTO(Product entity, Set<Category> categories){
        this(entity);
        categories.forEach(category -> this.categories.add(new CategoryDTO(category)));
    }

}
