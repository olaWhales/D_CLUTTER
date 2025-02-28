package com.example.D_CLUTTER.service;

import com.example.D_CLUTTER.data.Product;
import com.example.D_CLUTTER.data.UserPrincipal;
import com.example.D_CLUTTER.data.Users;
import com.example.D_CLUTTER.dto.Response.productResponse.CreateProductResponse;
import com.example.D_CLUTTER.dto.Response.productResponse.DeleteProductResponse;
import com.example.D_CLUTTER.dto.Response.productResponse.GetProductResponse;
import com.example.D_CLUTTER.dto.Response.productResponse.UpdateProductResponse;
import com.example.D_CLUTTER.dto.request.productRequest.*;
import com.example.D_CLUTTER.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImp implements ProductService {
    @Autowired
    private ProductRepository productRepository;


    @Override
    public CreateProductResponse createProduct(CreateProductRequest productRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalArgumentException("Authentication required");
        }
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof UserPrincipal)) {
            System.out.println( " is not a user " + principal);
            throw new IllegalArgumentException("Invalid user authentication");
        }
        Users user = ((UserPrincipal) principal).getUsers(); // Get actual user
        Product product = new Product();
        product.setUsers(user);
        product.setName(productRequest.getProductName());
        product.setDescription(productRequest.getProductDescription());
        product.setQuantity(productRequest.getProductQuantity());
        product.setPrice(productRequest.getProductPrice());
        product.setId(product.getId());
        return getResponse(product);
    }

    CreateProductResponse getResponse(Product product) {
        CreateProductResponse response = new CreateProductResponse();
        response.setProductName(product.getName());
        response.setProductDescription(product.getDescription());
        response.setProductQuantity(product.getQuantity());
        response.setProductPrice(product.getPrice());
        response.setProductId(product.getId());
        return response;
    }

    @Override
    public DeleteProductResponse delete(DeleteProductRequest deleteProduct) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalArgumentException("User is not authenticated");
        }
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        Users user = principal.getUsers();

        Long productId = deleteProduct.getProductId();
        if (productId == null) {
            throw new IllegalArgumentException("Product Id is null");
        }
        Product product = productRepository.findById(productId).
                orElseThrow(()-> new IllegalArgumentException("Product not found"));
        if(product.getId().equals(user.getId())){
            productRepository.delete(product);
        }
        DeleteProductResponse response = new DeleteProductResponse();
        response.setMessage("Product deleted successfully");
        return response;
    }

    @Override
    public UpdateProductResponse update(UpdateProductRequest updateProduct) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalArgumentException("User is not authenticated");
        }
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        if(principal == null){
            throw new IllegalArgumentException("User is not authenticated");
        }
//        Long product = updateProduct.ge
        Users user = principal.getUsers();

//        Product product = user.getProducts()
//        if(user.getId().equals(product.getId())){
//            product.setName(updateProduct.getProductName());
//            product.setDescription(updateProduct.getProductDescription());
//            product.setQuantity(updateProduct.getProductQuantity());
//            product.setPrice(updateProduct.getProductPrice());
//            product.setId(updateProduct.getId());
//            productRepository.save(product);
//        }

        return null;
    }

    @Override
    public List<GetProductResponse> getAll(GetAllProductsRequest getAll) {
        return List.of();
    }

    @Override
    public GetProductResponse getSingleProduct(GetSingleSellerRequest getSingle) {
        return null;
    }
}
