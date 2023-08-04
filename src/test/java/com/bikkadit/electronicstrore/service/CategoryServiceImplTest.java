package com.bikkadit.electronicstrore.service;

import com.bikkadit.electronicstrore.dtos.CategoryDto;
import com.bikkadit.electronicstrore.dtos.PageableResponse;
import com.bikkadit.electronicstrore.entities.Category;
import com.bikkadit.electronicstrore.exception.ResourceNotFoundException;
import com.bikkadit.electronicstrore.repository.CategoryRepository;
import com.bikkadit.electronicstrore.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CategoryServiceImplTest {

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryServiceImpl categoryServiceImpl;

    Category category1;
    Category category2;

    CategoryDto categoryDto;

    List<Category> categories;

    @BeforeEach
    public void init(){
        String categoryId1 = UUID.randomUUID().toString();
        category1= Category.builder()
                .categoryId(categoryId1)
                .title("washing machine")
                .description("washing machine available with discount")
                .build();
        String categoryId2 = UUID.randomUUID().toString();
        category2= Category.builder()
                .categoryId(categoryId2)
                .title("Mobiles")
                .description("Mobiles available with discount")
                .build();
        categories=new ArrayList<>();
        categories.add(category1);
        categories.add(category2);


    }

    @Test
    public void createCategoryTest(){
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category1);

        CategoryDto createdCategory = categoryServiceImpl.create (modelMapper.map(category1, CategoryDto.class));

        Assertions.assertEquals(category1.getTitle(),createdCategory.getTitle());
    }

    @Test
    public void updateCategoryTest(){
        String categoryId=UUID.randomUUID().toString();
        categoryDto = CategoryDto.builder()
                .categoryId(categoryId)
                .title(" Freeze")
                .description("Freeze available with discount")
                .build();
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category1));

        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category1);

        CategoryDto updatedCategory = categoryServiceImpl.update (categoryDto, categoryId);

        //Assertions.assertEquals(categoryDto.getTitle(),updatedCategory.getTitle());
        Assertions.assertNotNull(updatedCategory);
    }

    @Test
    public void getSingleCategoryTest(){
        String categoryId=UUID.randomUUID().toString();
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category1));

        CategoryDto categoryDto1 = categoryServiceImpl.get(categoryId);

        Assertions.assertEquals(category1.getTitle(),categoryDto1.getTitle());
    }


    @Test
    public void deleteCategoryByIdTest(){
        String categoryId = UUID.randomUUID().toString();

        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category1));

       categoryServiceImpl.delete (categoryId);

        Mockito.verify(categoryRepository,Mockito.times(1)).delete(category1);
    }
    @Test
    public void getAllCategoriesTest(){
        int pageNumber=0;
        int pageSize=2;
        String sortBy="title";
        String sortDir="asc";
        Sort sort=Sort.by(sortBy).ascending();
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);

        Page<Category> page=new PageImpl<>(categories);

        Mockito.when(categoryRepository.findAll(pageable)).thenReturn(page);

        PageableResponse<CategoryDto> allCategories = categoryServiceImpl.getAll (pageNumber, pageSize, sortBy, sortDir);

        Assertions.assertEquals(2,allCategories.getContent().size());
    }



    @Test
    public void deleteCategoryExceptionTest(){
        Assertions.assertThrows(ResourceNotFoundException.class,()->categoryServiceImpl.delete (null));
 }

}
