package com.bikkadit.electronicstrore.controller;

import com.bikkadit.electronicstrore.dtos.ImageResponse;
import com.bikkadit.electronicstrore.dtos.PageableResponse;
import com.bikkadit.electronicstrore.dtos.ProductDto;
import com.bikkadit.electronicstrore.dtos.UserDto;
import com.bikkadit.electronicstrore.helper.ApiResponseMessage;
import com.bikkadit.electronicstrore.service.FileService;
import com.bikkadit.electronicstrore.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;

    @Value("${product.image.path}")
    private String imagePath;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    /**
     * @Author Shital
     * @param productDto
     * @return
     */

    @PostMapping("/")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto)
    {
        logger.info("Creating Product..!!! {}", productDto);
        ProductDto createProduct = productService.create(productDto);
        logger.info("Product created successfully...!!! {}", createProduct);
        return new ResponseEntity<>(createProduct, HttpStatus.CREATED);

    }

    /**
     * @Author Shital
     * @param productDto
     * @param productId
     * @return
     */
    @PutMapping ("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto, @PathVariable String  productId)
    {
        logger.info("Update Product..!!! {}", productDto);
        ProductDto updateProduct = productService.update(productDto,productId);

        logger.info("update Successfully...!!! {}", updateProduct);
        return new ResponseEntity<>(updateProduct, HttpStatus.OK);

    }

    /**
     * @Author Shital
     * @param productId
     * @return
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable String  productId)
    {
        logger.info("Deleting Product by id {}", productId);
          productService.delete(productId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder().message("Product is deleted successfully").status(HttpStatus.OK).success(true).build();

        logger.info("Deleted Successfully {}", responseMessage);
        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }

    /**
     * @Author Shital
     * @param productId
     * @return
     */
    @GetMapping("/{productId}")
        public ResponseEntity<ProductDto> getProduct(@PathVariable String productId)
    {
        logger.info("Retrieving product by Id");
        ProductDto productDto = productService.get(productId);
        logger.info("Retrieving Successful...!!!{}",productDto);
        return new ResponseEntity<>(productDto, HttpStatus.OK);

    }

    /**
     * @Author Shital
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
    @GetMapping
    public ResponseEntity<PageableResponse<ProductDto>> getAllProduct(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = true) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        logger.info("Retrieving All product..!!!");
        PageableResponse<ProductDto> pageableResponse = productService.getAll(pageNumber,pageSize,sortBy,sortDir);

        logger.info("Retrieving successfully...!!! {}",pageableResponse);
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }

    /**
     * @Author Shital
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */

    @GetMapping("/live")
    public ResponseEntity<PageableResponse<ProductDto>> getAllLive(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = true) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        logger.info("Retrieving  live product  ");
        PageableResponse<ProductDto> pageableResponse = productService.getAllLive(pageNumber,pageSize,sortBy,sortDir);
        logger.info("Retrieving successfully...!!! {}",pageableResponse);
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }

    /**
     * @Author Shital
     * @param query
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */

    @GetMapping("/search/{query}")
    public ResponseEntity<PageableResponse<ProductDto>> searchProduct(
            @PathVariable String query,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = true) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        logger.info("Search product....");
        PageableResponse<ProductDto> pageableResponse = productService.searchByTitle(query,pageNumber,pageSize,sortBy,sortDir);
        logger.info("Retrieving successfully...!!! {}",pageableResponse);
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }

    /**
     * @Author Shital
     * @param productId
     * @param image
     * @return
     * @throws IOException
     */
    @PostMapping("/images/{productId}")
    public ResponseEntity<ImageResponse> uploadProductImage(
        @PathVariable String productId,@RequestParam("ProductImage")MultipartFile image) throws IOException
    {
        logger.info("upload image by id {}",productId);
        String fileName = fileService.uploadFile(image, imagePath);
        ProductDto productDto = productService.get(productId);
        productDto.setImageName(fileName);
        ProductDto updatedProduct = productService.update(productDto, productId);

        ImageResponse response = ImageResponse.builder().imageName(updatedProduct.getImageName()).message("product image is successfully upload").status(HttpStatus.CREATED).success(true).build();
        logger.info("Upload image successfully...!!! {}",response);
        return  new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    /**
     * @Author Shital
     * @param productId
     * @param response
     * @throws IOException
     */

    @GetMapping("/image/{productId}")
    public void serveUserImage(@PathVariable String productId, HttpServletResponse response) throws IOException {
        logger.info("Retrieve image by id {}",productId);
        ProductDto productDto = productService.get(productId);
        InputStream resource = fileService.getResource(imagePath,productDto.getImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }
}
