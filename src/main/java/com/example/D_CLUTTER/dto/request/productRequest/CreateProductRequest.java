package com.example.D_CLUTTER.dto.request.productRequest;

import com.example.D_CLUTTER.data.Users;
import lombok.Data;

@Data
public class CreateProductRequest {
    private String productName;
    private String productDescription;
    private Double productPrice;


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

}


