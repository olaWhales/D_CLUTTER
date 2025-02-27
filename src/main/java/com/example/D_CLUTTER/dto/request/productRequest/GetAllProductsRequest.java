package com.example.D_CLUTTER.dto.request.productRequest;

import lombok.Data;

@Data
public class GetAllProductsRequest {
    private Long sellerId;


    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }
}
