package com.pedroacbg.togolog.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_category")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Category(){
        id = null;
        name = null;
    }

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }



}
