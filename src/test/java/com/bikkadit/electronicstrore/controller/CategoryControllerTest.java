package com.bikkadit.electronicstrore.controller;

import com.bikkadit.electronicstrore.dtos.CategoryDto;
import com.bikkadit.electronicstrore.dtos.PageableResponse;
import com.bikkadit.electronicstrore.entities.Category;
import com.bikkadit.electronicstrore.service.CategoryService;
import com.bikkadit.electronicstrore.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.UUID;


import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc

public class CategoryControllerTest {

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private ProductService productService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MockMvc mockMvc;

    private Category category;

    @BeforeEach
    public void setUp(){
        String categoryId1 = UUID.randomUUID().toString();
        category= Category.builder()
                .categoryId(categoryId1)
                .title("washing machine")
                .description("washing machine available with discount")
                .coverImage("abc.png")
                .build();
    }

    @Test
    void createCategoryTest() throws Exception {
        CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);

        Mockito.when(categoryService.create(Mockito.any())).thenReturn(categoryDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/category/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(category))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());




    }

    private String convertObjectToJsonString(Object user) {

        try {
            return new ObjectMapper().writeValueAsString(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    void updateCategoryTest() throws Exception {
        String categoryId=UUID.randomUUID().toString();
        CategoryDto categoryDto=CategoryDto.builder()
                .categoryId(categoryId)
                .title("Mobiles")
                .description("Mobiles available with discounts")
                .coverImage("abc.png")
                .build();

        Mockito.when(categoryService.update (Mockito.any(),Mockito.anyString())).thenReturn(categoryDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/category/" +categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(category))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.description").exists());


    }

    @Test
    void getSingleCategoryTest() throws Exception {
        String categoryId=UUID.randomUUID().toString();

        CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);

        Mockito.when(categoryService.get (categoryId)).thenReturn(categoryDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/category/"+categoryId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.description").exists());

    }

    @Test
    void deleteCategoryTest() throws Exception {
        String categoryId=UUID.randomUUID().toString();

        mockMvc.perform(MockMvcRequestBuilders.delete("/category/"+categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());


    }

    @Test
    void getAllCategoriesTest() throws Exception {
        CategoryDto categoryDto1=CategoryDto.builder().categoryId(UUID.randomUUID().toString())
                .title("Cooler")
                .description("Cooler available with good quality")
                .coverImage("xyz.png")
                .build();
        CategoryDto categoryDto2=CategoryDto.builder().categoryId(UUID.randomUUID().toString())
                .title("LED TVS")
                .description("LED TVS available with good quality")
                .coverImage("def.png")
                .build();
        CategoryDto categoryDto3=CategoryDto.builder().categoryId(UUID.randomUUID().toString())
                .title("AC")
                .description("AC available with good quality")
                .coverImage("pqr.png")
                .build();
        CategoryDto categoryDto4=CategoryDto.builder().categoryId(UUID.randomUUID().toString())
                .title("phones")
                .description("phones available with good quality")
                .coverImage("dbw.png")
                .build();

        PageableResponse<CategoryDto> pageableResponse=new PageableResponse<>();
        pageableResponse.setContent(Arrays.asList(categoryDto1,categoryDto2,categoryDto3,categoryDto4));
        pageableResponse.setPageNumber(0);
        pageableResponse.setPageSize(10);
        pageableResponse.setTotalPage(100);
        pageableResponse.setTotalElements(1000);
        pageableResponse.setLastPage(false);

        Mockito.when(categoryService.getAll (Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/category/allCategory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }


}
