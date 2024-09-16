package com.pedroacbg.togoshop.services.validation;

import com.pedroacbg.togoshop.controllers.exceptions.FieldMessage;
import com.pedroacbg.togoshop.dto.UserUpdateDTO;
import com.pedroacbg.togoshop.entities.User;
import com.pedroacbg.togoshop.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.servlet.HandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserUpdateValidator implements ConstraintValidator<UserUpdateValid, UserUpdateDTO> {

    private final HttpServletRequest request;
    private final UserRepository userRepository;

    public UserUpdateValidator(HttpServletRequest request, UserRepository userRepository) {
        this.request = request;
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(UserUpdateValid ann) {
    }

    @Override
    public boolean isValid(UserUpdateDTO dto, ConstraintValidatorContext context) {
        //Pegar o parametro do request
        @SuppressWarnings("unchecked")
        var uriVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        long userId = Long.parseLong(uriVars.get("id"));

        List<FieldMessage> list = new ArrayList<>();

        // testes de validação, acrescentando objetos FieldMessage à lista
        User user = userRepository.findByEmail(dto.getEmail());
        if(user != null && userId != user.getId()){
            list.add(new FieldMessage("email", "Email já existe."));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
