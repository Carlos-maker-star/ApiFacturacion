package com.carlos.apifacturacion.mapper;


import com.carlos.apifacturacion.dto.request.ProductRequest;
import com.carlos.apifacturacion.dto.response.ProductResponse;
import com.carlos.apifacturacion.dto.update.ProductUpdate;
import com.carlos.apifacturacion.entity.Product;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductRequest request);

    ProductResponse toResponse(Product entity);

    List<ProductResponse> toResponseList(List<Product> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ProductUpdate dto, @MappingTarget Product entity);
}
