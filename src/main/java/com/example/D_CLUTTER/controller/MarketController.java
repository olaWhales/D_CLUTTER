package com.example.D_CLUTTER.controller;

import com.example.D_CLUTTER.dto.request.productRequest.CreateProductRequest;
import com.example.D_CLUTTER.dto.request.productRequest.DeleteProductRequest;
import com.example.D_CLUTTER.service.ProductService;
import jakarta.servlet.http.HttpServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/product")
public class MarketController {
    @Autowired
    private ProductService productService;

    @PostMapping(value = "/upload/" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> Upload(@RequestBody CreateProductRequest createProductRequest) {
        try{
            return new ResponseEntity<>(productService.createProduct(createProductRequest), HttpStatus.CREATED);
        }catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//
//    try {
//        return new ResponseEntity<>(productService.createProduct(createProductRequest), HttpStatus.CREATED);
//    } catch (Exception exception) {
//        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//}



    @DeleteMapping("/deletion/")
    public ResponseEntity<?> Delete(@RequestBody DeleteProductRequest deleteProductRequest) {
        try{
            return new ResponseEntity<>(productService.delete(deleteProductRequest), HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
