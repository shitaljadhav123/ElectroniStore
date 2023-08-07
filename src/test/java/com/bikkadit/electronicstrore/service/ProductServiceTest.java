package com.bikkadit.electronicstrore.service;

import com.bikkadit.electronicstrore.dtos.ProductDto;
import com.bikkadit.electronicstrore.entities.Product;
import com.bikkadit.electronicstrore.repository.CategoryRepository;
import com.bikkadit.electronicstrore.repository.ProductRepository;
import com.bikkadit.electronicstrore.service.impl.CategoryServiceImpl;
import com.bikkadit.electronicstrore.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@SpringBootTest

public class ProductServiceTest {
    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @Autowired
    private CategoryServiceImpl categoryServiceImpl;

    @Autowired
    private ModelMapper modelMapper;

    Product product1;

    Product product2;

    ProductDto productDto;

    List<Product> products;

    @BeforeEach
    public void init(){
        String productId= UUID.randomUUID().toString();
        product1 = Product.builder()
                .productId(productId)
                .title("Samsung A34")
                .discountedPrice(3000)
                .price(20000)
                .quantity(100)
                .live(true)
                .addedDate(new Date())
                .stock(true)
                .description("This mobile  has many fetures")
                .imageName("abc.png")
                .build();

        String productId2= UUID.randomUUID().toString();
        product2 = Product.builder()
                .productId(productId2)
                .title("HP laptop")
                .discountedPrice(3000)
                .price(60000)
                .live(true)
                .quantity(100)
                .addedDate(new Date())
                .stock(true)
                .description("This laptop  has many fetures")
                .imageName("xyz.png")
                .build();

        products=new ArrayList<>();
        products.add(product1);
        products.add(product2);

    }

    @Test
    void createProductTest() {

        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product1);

        ProductDto productDto1 = productServiceImpl.create(modelMapper.map(product1, ProductDto.class));

        Assertions.assertEquals(product1.getTitle(),productDto1.getTitle());
    }

}
