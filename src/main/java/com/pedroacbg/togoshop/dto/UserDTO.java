package com.pedroacbg.togoshop.dto;

import com.pedroacbg.togoshop.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class UserDTO {

    private Long id;

    @NotBlank(message = "O campo nome é obrigatório")
    private String firstName;

    @NotBlank(message = "O campo nome é obrigatório")
    private String lastName;

    @Email(message = "Favor inserir um email válido.")
    private String email;

    @Setter(AccessLevel.NONE)
    Set<RoleDTO> roles = new HashSet<>();

    public UserDTO(){}

    public UserDTO(Long id, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public UserDTO(User entity) {
        id = entity.getId();
        firstName = entity.getFirstName();
        lastName = entity.getLastName();
        email = entity.getEmail();
        entity.getRoles().forEach(role -> this.roles.add(new RoleDTO(role)));
    }

}
