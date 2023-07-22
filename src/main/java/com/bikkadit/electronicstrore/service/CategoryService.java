package com.bikkadit.electronicstrore.service;

import com.bikkadit.electronicstrore.dtos.CategoryDto;
import com.bikkadit.electronicstrore.dtos.PageableResponse;
import com.bikkadit.electronicstrore.dtos.UserDto;

import java.util.List;

public interface CategoryService {
   //create
   CategoryDto create (CategoryDto categoryDto);

  //update
  CategoryDto update(CategoryDto categoryDto ,String categoryId);

   //delete
   void  delete (String CategoryId);

   //get all user
   PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);

   //get single user by id
   CategoryDto get (String categoryId);

    }