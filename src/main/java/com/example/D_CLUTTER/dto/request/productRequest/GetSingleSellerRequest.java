package com.example.D_CLUTTER.dto.request.productRequest;

import lombok.Data;

@Data
public class GetSingleSellerRequest {
    private Long productId ;


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
