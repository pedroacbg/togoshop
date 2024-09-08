package com.pedroacbg.togoshop.dto;

import com.pedroacbg.togoshop.entities.Category;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class CategoryDTO {

    private Long id;
    private String name;

    public CategoryDTO(){
        id = null;
        name = null;
    }

    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryDTO(Category entity){
        this.id = entity.getId();
        this.name = entity.getName();
    }

}
