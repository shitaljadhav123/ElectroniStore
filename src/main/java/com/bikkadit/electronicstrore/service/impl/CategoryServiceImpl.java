package com.bikkadit.electronicstrore.service.impl;

import com.bikkadit.electronicstrore.dtos.CategoryDto;
import com.bikkadit.electronicstrore.dtos.PageableResponse;
import com.bikkadit.electronicstrore.entities.Category;
import com.bikkadit.electronicstrore.exception.ResourceNotFoundException;
import com.bikkadit.electronicstrore.helper.AppConstants;
import com.bikkadit.electronicstrore.helper.Helper;
import com.bikkadit.electronicstrore.repository.CategoryRepository;
import com.bikkadit.electronicstrore.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    public CategoryDto create(CategoryDto categoryDto) {

        logger.info("Category is creating...!!!{}",categoryDto);
        String categoryId = UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryId);
        Category category = mapper.map(categoryDto, Category.class);
        Category saveCategory = categoryRepository.save(category);

        logger.info("Category Created Successfully..!!{}",saveCategory);
        return mapper.map(saveCategory,CategoryDto.class);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) {

        logger.info("Update category by id {}",categoryId);
        //get category using Id
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND));

        //update category details

        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());

        Category updatedCategory = categoryRepository.save(category);
        logger.info("Category updating successfully");
        return mapper.map(updatedCategory,CategoryDto.class);

    }

    @Override
    public void delete(String categoryId) {
        logger.info("Deleting Category by id {}", categoryId);
        //get category By Id
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.DELETE_CATEGORY));
        logger.info("Deleted Successfully {}", category);
        categoryRepository.delete(category);

    }

    @Override
    public PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        logger.info("Retrieving All category...!!!");
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> page = categoryRepository.findAll(pageable);
        PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page, CategoryDto.class);
        logger.info("Retrieving successfully...!!! {}",pageableResponse);
        return pageableResponse;
    }



    @Override
    public CategoryDto get(String categoryId) {
        logger.info("Retrieving Category by Id");
        Category category=categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException(AppConstants.NOT_FOUND));
        logger.info("Retrieving Successful...!!!{}",category);
        return mapper.map(category,CategoryDto.class);
}

    }
