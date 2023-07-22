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
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
    @Autowired
    private CategoryService categoryService;

    //create

    /**
     *
     * @param categoryDto
     * @return
     */
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        logger.info("Creating Category..!!!", categoryDto);
        //calling service to save object

        CategoryDto categoryDto1 = categoryService.create(categoryDto);
        logger.info("Category created sucessfully...!!!", categoryDto1);
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

    /**
     *
     * @param categoryId
     * @param categoryDto
     * @return
     */
    //update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable String categoryId, @RequestBody CategoryDto categoryDto) {
        logger.info("updating Ctegoy...!!!", categoryDto);

        CategoryDto updateCategory = categoryService.update(categoryDto, categoryId);

        logger.info("update Sucessfully...!!!", updateCategory);
        return new ResponseEntity<>(updateCategory, HttpStatus.OK);
    }

    /**
     *
     * @param categoryId
     * @return
     */
    //delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable String categoryId) {
        logger.info("Deleting Categoy by id", categoryId);
        categoryService.delete(categoryId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder().message("Category Deleted successfully...!!!").status(HttpStatus.OK).success(true).build();

        logger.info("Deleted Sucesfully", responseMessage);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }
    //get all

    /**
     *
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
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) throws InterruptedException {
        //  Thread.sleep(1000);
        logger.info("Retriving All category...!!!");
        PageableResponse<CategoryDto> pageableResponse = categoryService.getAll(pageNumber, pageSize, sortBy, sortDir);
        logger.info("retrive sucessfully...!!!",pageableResponse);
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }

    //get single

    /**
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getSingle(@PathVariable String categoryId) {
        logger.info("Retrivng Categoy by Id");
        CategoryDto categoryDto = categoryService.get(categoryId);
        logger.info("Retring Sucessfull...!!!",categoryDto);
        return ResponseEntity.ok(categoryDto);
    }
}
