package com.pedroacbg.togoshop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInsertDTO extends UserDTO{

    private String password;

    public UserInsertDTO() {
        super();
    }
}
