package com.carlos.apifacturacion.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class InvoiceDetailResponse {
    private Long id;
    private Long productId;
    private String productCode;
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
}