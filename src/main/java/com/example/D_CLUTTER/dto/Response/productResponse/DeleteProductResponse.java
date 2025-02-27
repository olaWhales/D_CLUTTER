package com.example.D_CLUTTER.dto.Response.productResponse;

import lombok.Data;

@Data
public class DeleteProductResponse {
    private String message ;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
