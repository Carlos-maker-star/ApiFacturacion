package com.carlos.apifacturacion.mapper;
import com.carlos.apifacturacion.dto.response.InvoiceDetailResponse;
import com.carlos.apifacturacion.dto.response.InvoiceResponse;
import com.carlos.apifacturacion.entity.Invoice;
import com.carlos.apifacturacion.entity.InvoiceDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productCode", source = "product.code")
    @Mapping(target = "productName", source = "product.name")
    InvoiceDetailResponse toDetailResponse(InvoiceDetail detail);

    InvoiceResponse toResponse(Invoice invoice);

    List<InvoiceResponse> toResponseList(List<Invoice> invoices);
}
