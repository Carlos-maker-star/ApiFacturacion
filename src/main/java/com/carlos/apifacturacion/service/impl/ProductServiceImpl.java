package com.carlos.apifacturacion.service.impl;

import com.carlos.apifacturacion.dto.request.ProductRequest;
import com.carlos.apifacturacion.dto.response.ProductResponse;
import com.carlos.apifacturacion.dto.update.ProductUpdate;
import com.carlos.apifacturacion.entity.Product;
import com.carlos.apifacturacion.mapper.ProductMapper;
import com.carlos.apifacturacion.repository.ProductRepository;
import com.carlos.apifacturacion.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public ProductResponse create(ProductRequest request) {
        if (productRepository.existsByCode(request.getCode())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El código de producto ya existe");
        }
        Product product = productMapper.toEntity(request);
        Product savedProduct = productRepository.save(product);
        return productMapper.toResponse(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con el ID: " + id));
        return productMapper.toResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> findAllActive() {
        return productMapper.toResponseList(productRepository.findAll());
    }

    @Override
    @Transactional
    public ProductResponse update(Long id, ProductUpdate request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con el ID: " + id));

        productMapper.updateEntityFromDto(request, product);
        Product updatedProduct = productRepository.save(product);
        return productMapper.toResponse(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteLogical(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con el ID: " + id));

        product.setActive(!product.getActive());
        productRepository.save(product);
    }
}