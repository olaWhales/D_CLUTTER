package com.example.D_CLUTTER.dto.request.productRequest;

import com.example.D_CLUTTER.data.Users;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@Data
public class CreateProductRequest {
    private String productName;
    private String productDescription;
    private Double productPrice;

//    @NotNull
//    @RequestParam("uploadImage") // Ensure this matches Postman key
//    public MultipartFile uploadImage ;

    private MultipartFile uploadImage;

}


