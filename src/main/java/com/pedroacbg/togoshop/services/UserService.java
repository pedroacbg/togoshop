package com.pedroacbg.togoshop.services;

import com.pedroacbg.togoshop.dto.RoleDTO;
import com.pedroacbg.togoshop.dto.UserDTO;
import com.pedroacbg.togoshop.dto.UserInsertDTO;
import com.pedroacbg.togoshop.entities.Role;
import com.pedroacbg.togoshop.entities.User;
import com.pedroacbg.togoshop.repositories.RoleRepository;
import com.pedroacbg.togoshop.repositories.UserRepository;
import com.pedroacbg.togoshop.services.exceptions.DatabaseException;
import com.pedroacbg.togoshop.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository repository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> findAllPaged(Pageable pageable){
        Page<User> list = repository.findAll(pageable);
        return list.map(x -> new UserDTO(x));
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id){
        Optional<User> obj = repository.findById(id);
        User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found!"));
        return new UserDTO(entity);
    }

    @Transactional(readOnly = true)
    public UserDTO insert(UserInsertDTO obj){
        User entity = new User();
        copyToDto(obj, entity);
        entity.setPassword(bCryptPasswordEncoder.encode(obj.getPassword()));
        entity = repository.save(entity);
        return new UserDTO(entity);
    }

    @Transactional
    public UserDTO update(Long id, UserDTO obj) {
        try {
            User entity = repository.getReferenceById(id);
            copyToDto(obj, entity);
            entity = repository.save(entity);
            return new UserDTO(entity);
        }catch(EntityNotFoundException e){
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Resource not found, ID: " + id);
        }
        try{
            repository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DatabaseException("Reference integrity failure");
        }
    }

    private void copyToDto(UserDTO dto, User entity){
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());

        entity.getRoles().clear();
        for(RoleDTO obj : dto.getRoles()){
            Role role = roleRepository.getReferenceById(obj.getId());
            entity.getRoles().add(role);
        }
    }

}
