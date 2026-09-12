package com.carlos.apifacturacion.controller;

import com.carlos.apifacturacion.dto.request.ProductRequest;
import com.carlos.apifacturacion.dto.response.ProductResponse;
import com.carlos.apifacturacion.dto.update.ProductUpdate;
import com.carlos.apifacturacion.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "${app.cors.allowed-origin}")
@RequestMapping("/apiFA/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/created")
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        return new ResponseEntity<>(productService.create(request), HttpStatus.CREATED);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping("/listAll")
    public ResponseEntity<List<ProductResponse>> getAllActiveProducts() {
        return ResponseEntity.ok(productService.findAllActive());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductUpdate request) {
        return ResponseEntity.ok(productService.update(id, request));
    }

    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<Void> toggleStatus(@PathVariable Long id) {
        productService.deleteLogical(id);
        return ResponseEntity.noContent().build();
    }
}