package com.carlos.apifacturacion.dto.response;


import com.carlos.apifacturacion.dto.enums.VoucherType;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class InvoiceResponse {
    private Long id;
    private VoucherType voucherType;
    private String invoiceNumber;
    private String clientName;
    private String clientDocument;
    private LocalDateTime createdAt;
    private BigDecimal subtotal;
    private BigDecimal tax;
    private BigDecimal totalAmount;
    private String status;
    private List<InvoiceDetailResponse> details;
}