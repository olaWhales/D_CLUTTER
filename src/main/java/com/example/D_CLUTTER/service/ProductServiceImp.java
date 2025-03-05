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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImp implements ProductService {
    @Autowired
    private ProductRepository productRepository;


    @Override
    public CreateProductResponse createProduct(CreateProductRequest productRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalArgumentException("Authentication required");
        }
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof UserPrincipal)) {
            System.out.println(" is not a user " + principal);
            throw new IllegalArgumentException("Invalid user authentication");
        }
        Users user = ((UserPrincipal) principal).getUsers();
        Product product = new Product();
        product.setUsers(user);
        product.setName(productRequest.getProductName());
        product.setDescription(productRequest.getProductDescription());
        product.setPrice(productRequest.getProductPrice());

//        if (productRequest.getUploadImage() == null || productRequest.getUploadImage().isEmpty()) {
//            throw new IllegalArgumentException("No file uploaded");
//        }
//        try(InputStream inputStream = productRequest.getUploadImage().getInputStream()){
//            byte[] fileByte = inputStream.readAllBytes();
//            product.setImage(fileByte);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        if (productRequest.getUploadImage() == null || productRequest.getUploadImage().isEmpty()) {
            throw new IllegalArgumentException("No file uploaded");
        }

        // Define the local storage path
        String uploadDir = "uploads/";
        File uploadPath = new File(uploadDir);
        if (!uploadPath.exists()) {
            uploadPath.mkdirs(); // Create the directory if it doesn't exist
        }

        try {
            MultipartFile file = productRequest.getUploadImage();
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            String filePath = uploadDir + fileName;

            // Save file to local storage
            File destinationFile = new File(filePath);
            file.transferTo(destinationFile);

            // Save file path in database
            product.setImagePath(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Error saving file", e);
        }

        product.setId(product.getId());
        productRepository.save(product);
        return getResponse(product);
    }

    CreateProductResponse getResponse(Product product) {
        CreateProductResponse response = new CreateProductResponse();
        response.setProductName(product.getName());
        response.setProductDescription(product.getDescription());
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
                orElseThrow(() -> new IllegalArgumentException("Product not found"));
        if (product.getId().equals(user.getId())) {
            productRepository.delete(product);
        }
        DeleteProductResponse response = new DeleteProductResponse();
        response.setMessage("Product deleted successfully");
        return response;
    }

    @Override
    public UpdateProductResponse update(UpdateProductRequest updateProduct) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if(authentication == null || !authentication.isAuthenticated()) {
//            throw new IllegalArgumentException("User is not authenticated");
//        }
//        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
//        if(principal == null){
//            throw new IllegalArgumentException("User is not authenticated");
//        }
//        Long product = updateProduct.getId();
//        if(product == null){
//            throw new IllegalArgumentException("Product Id is null,please provide a valid product identity");
//        }
//        Product product1 = productRepository.findById(product).
//                orElseThrow(()-> new IllegalArgumentException("Product not found"));
//        if(!principal.getUsers().getId().equals(product1.getId())) {
//            throw new IllegalArgumentException("User and the product id is not match");
//        }
//        product1.setName(updateProduct.getProductName());
//        product1.setDescription(updateProduct.getProductDescription());
//        product1.setPrice(updateProduct.getProductPrice());
//        productRepository.save(product1);
//
//        UpdateProductResponse response = new UpdateProductResponse();
//        response.setMessage("Product updated successfully");

//        Users user = principal.getUsers();

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
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if(authentication == null || !authentication.isAuthenticated()) {
//            throw new IllegalArgumentException("User is not authenticated");
//        }
//        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
//        if(principal == null){
//            throw new IllegalArgumentException("User is not authenticated");
//        }
//
//        return List.of();
//    }

        return null ;
    }

    @Override
    public GetProductResponse getSingleProduct (GetSingleSellerRequest getSingleSeller) {
        return null;
    }
}
