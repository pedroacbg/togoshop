package com.pedroacbg.togolog.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class Category {

    private final Long id;
    private final String name;

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }



}
