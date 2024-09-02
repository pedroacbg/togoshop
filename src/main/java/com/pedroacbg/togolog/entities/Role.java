package com.pedroacbg.togolog.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_role")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String authority;

    public Role(){}

    public Role(Long id, String authority) {
        this.id = id;
        this.authority = authority;
    }
}
