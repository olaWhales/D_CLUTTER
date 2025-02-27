package com.example.D_CLUTTER.service;

import com.example.D_CLUTTER.dto.Response.productResponse.CreateProductResponse;
import com.example.D_CLUTTER.dto.Response.productResponse.DeleteProductResponse;
import com.example.D_CLUTTER.dto.Response.productResponse.GetProductResponse;
import com.example.D_CLUTTER.dto.Response.productResponse.UpdateProductResponse;
import com.example.D_CLUTTER.dto.request.productRequest.*;

import java.util.List;

public interface ProductService {
    CreateProductResponse createProduct(CreateProductRequest productRequest);
    DeleteProductResponse delete(DeleteProductRequest deleteProduct);
    UpdateProductResponse update (UpdateProductRequest updateProduct);
    List<GetProductResponse> getAll(GetAllProductsRequest getAll);
    GetProductResponse getSingleProduct(GetSingleSellerRequest getSingle);
}
