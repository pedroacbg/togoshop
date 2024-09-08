package com.pedroacbg.togoshop.dto;

import com.pedroacbg.togoshop.entities.Role;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class RoleDTO {

    private Long id;
    private String authority;

    public RoleDTO() {
    }

    public RoleDTO(Long id, String authority) {
        this.id = id;
        this.authority = authority;
    }

    public RoleDTO(Role role){
        id = role.getId();
        authority = role.getAuthority();
    }
}
