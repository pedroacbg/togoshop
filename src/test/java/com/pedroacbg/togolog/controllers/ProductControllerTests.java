package com.pedroacbg.togolog.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pedroacbg.togolog.dto.ProductDTO;
import com.pedroacbg.togolog.services.ProductService;
import com.pedroacbg.togolog.services.exceptions.DatabaseException;
import com.pedroacbg.togolog.services.exceptions.ResourceNotFoundException;
import com.pedroacbg.togolog.tests.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@WebMvcTest(ProductController.class)
public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService service;

    private Long existingId;
    private Long nonExistingId;
    private PageImpl<ProductDTO> page;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() throws Exception{
        existingId = 1L;
        nonExistingId = 2L;
        productDTO = Factory.createProductDTO();
        page = new PageImpl<>(List.of(productDTO));

        Mockito.when(service.findAllPaged(ArgumentMatchers.any())).thenReturn(page);

        Mockito.when(service.findById(existingId)).thenReturn(productDTO);
        Mockito.when(service.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);

        Mockito.when(service.update(ArgumentMatchers.eq(existingId), ArgumentMatchers.any())).thenReturn(productDTO);
        Mockito.when(service.update(ArgumentMatchers.eq(nonExistingId), ArgumentMatchers.any())).thenThrow(ResourceNotFoundException.class);
    }

    @Test
    public void findAllShouldReturnPage() throws Exception{
       ResultActions result =  mockMvc.perform(MockMvcRequestBuilders.get("/products").accept(MediaType.APPLICATION_JSON));
       result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void findByIdShouldReturnProductDTOWhenIdExists() throws Exception{
        ResultActions result =  mockMvc.perform(MockMvcRequestBuilders.get("/products/{id}", existingId).accept(MediaType.APPLICATION_JSON));
        result.andExpect(MockMvcResultMatchers.status().isOk());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.name").exists());
    }

    @Test
    public void findByIdShouldReturnNotFoundWhenIdDoesNotExists() throws Exception{
        ResultActions result =  mockMvc.perform(MockMvcRequestBuilders.get("/products/{id}", nonExistingId).accept(MediaType.APPLICATION_JSON));
        result.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void updateShouldReturnProductDTOWhenIdExists() throws Exception{
        String jsonBody = objectMapper.writeValueAsString(productDTO);

        ResultActions result =  mockMvc.perform(MockMvcRequestBuilders.put("/products/{id}", existingId)
                .content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
        result.andExpect(MockMvcResultMatchers.status().isOk());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.name").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.description").exists());
    }

    @Test
    public void updateShouldReturnNotFoundWhenIdDoesNotExists() throws Exception{
        String jsonBody = objectMapper.writeValueAsString(productDTO);

        ResultActions result =  mockMvc.perform(MockMvcRequestBuilders.put("/products/{id}", nonExistingId)
                .content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
        result.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
