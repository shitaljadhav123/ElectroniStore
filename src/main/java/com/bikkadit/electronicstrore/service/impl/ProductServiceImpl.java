package com.bikkadit.electronicstrore.service.impl;

import com.bikkadit.electronicstrore.dtos.PageableResponse;
import com.bikkadit.electronicstrore.dtos.ProductDto;
import com.bikkadit.electronicstrore.entities.Product;
import com.bikkadit.electronicstrore.exception.ResourceNotFoundException;
import com.bikkadit.electronicstrore.helper.Helper;
import com.bikkadit.electronicstrore.repository.ProductRepository;
import com.bikkadit.electronicstrore.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ProductDto create(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        //product id
        String productId = UUID.randomUUID().toString();
        product.setProductId(productId);

        //added
        product.setAddedDate(new Date());
        Product saveProduct = productRepository.save(product );
        return modelMapper.map(saveProduct, ProductDto.class);
    }

    @Override
    public ProductDto update(ProductDto productDto, String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product not found by given id"));
       product.setTitle(productDto.getTitle());
       product.setDescription(productDto.getDescription());
       product.setPrice(productDto.getPrice());
       product.setDiscountedPrice(productDto.getDiscountedPrice());
       product.setQuantity(productDto.getQuantity());
       product.setStock(productDto.isStock());
       product.setLive(productDto.isLive());

        Product updatedProduct = productRepository.save(product);
        return modelMapper.map(updatedProduct, ProductDto.class);
    }

    @Override
    public void delete(String productId) {
       Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found by given id"));
        productRepository.delete(product);
    }

    @Override
    public ProductDto get(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product not found by id"));
        return modelMapper.map(product, ProductDto.class);
    }


    @Override
   public PageableResponse <ProductDto> getAll(int pageNumber,int pageSize,String sortBy, String sortDir) {

        Sort sort=(sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        PageRequest pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> page = productRepository.findAll(pageable);
        return Helper.getPageableResponse(page, ProductDto.class);
    }

    @Override
     public PageableResponse <ProductDto> getAllLive(int pageNumber,int pageSize,String sortBy, String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()): (Sort.by(sortBy).ascending());
        PageRequest pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> page = productRepository.findByLiveTrue(pageable);
        return Helper.getPageableResponse(page, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber,int pageSize,String sortBy, String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        PageRequest pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> page = productRepository.findByTitleContaining(subTitle,pageable);
        return Helper.getPageableResponse(page, ProductDto.class);
    }
}
