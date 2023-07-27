package com.bikkadit.electronicstrore.controller;

import com.bikkadit.electronicstrore.dtos.CategoryDto;
import com.bikkadit.electronicstrore.dtos.PageableResponse;
import com.bikkadit.electronicstrore.helper.ApiResponseMessage;
import com.bikkadit.electronicstrore.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
    @Autowired
    private CategoryService categoryService;

    //create
    /**
     * @Author Shital
     * @param categoryDto
     * @return
     */
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        logger.info("Creating Category..!!! {}", categoryDto);
        //calling service to save object

        CategoryDto categoryDto1 = categoryService.create(categoryDto);
        logger.info("Category created successfully...!!! {}", categoryDto1);
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

    /**
     * @Author Shital
     * @param categoryId
     * @param categoryDto
     * @return
     */
    //update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable String categoryId,@Valid @RequestBody CategoryDto categoryDto) {
        logger.info("updating Category...!!! {}", categoryDto);

        CategoryDto updateCategory = categoryService.update(categoryDto, categoryId);

        logger.info("update Successfully...!!! {}", updateCategory);
        return new ResponseEntity<>(updateCategory, HttpStatus.OK);
    }

    /**
     * @Author Shital
     * @param categoryId
     * @return
     */
    //delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable String categoryId) {
        logger.info("Deleting Category by id {}", categoryId);
        categoryService.delete(categoryId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder().message("Category Deleted successfully...!!!").status(HttpStatus.OK).success(true).build();

        logger.info("Deleted Successfully {}", responseMessage);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }
    //get all

    /**
     * @Author Shital
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/allCategory")
    public ResponseEntity<PageableResponse<CategoryDto>> getAll(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = true) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) throws InterruptedException {
        //  Thread.sleep(1000);
        logger.info("Retrieving All category...!!!");
        PageableResponse<CategoryDto> pageableResponse = categoryService.getAll(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Retrieving successfully...!!! {}",pageableResponse);
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }

    //get single

    /**
     * @Author Shital
     * @param categoryId
     * @return
     */
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getSingle(@PathVariable String categoryId) {
        logger.info("Retrieving Category by Id");
        CategoryDto categoryDto = categoryService.get(categoryId);
        logger.info("Retrieving Successful...!!!{}",categoryDto);
        return ResponseEntity.ok(categoryDto);
    }
}
