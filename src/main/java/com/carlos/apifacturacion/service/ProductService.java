package com.carlos.apifacturacion.service;

import com.carlos.apifacturacion.dto.request.ProductRequest;
import com.carlos.apifacturacion.dto.response.ProductResponse;
import com.carlos.apifacturacion.dto.update.ProductUpdate;

import java.util.List;

public interface ProductService {

    ProductResponse create(ProductRequest request);

    ProductResponse findById(Long id);

    List<ProductResponse> findAllActive();

    ProductResponse update(Long id, ProductUpdate request);

    void deleteLogical(Long id);
}