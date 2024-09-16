package com.pedroacbg.togoshop.dto;

import com.pedroacbg.togoshop.services.validation.UserInsertValid;
import com.pedroacbg.togoshop.services.validation.UserUpdateValid;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@UserUpdateValid
public class UserUpdateDTO extends UserDTO{

    public UserUpdateDTO() {
        super();
    }
}
