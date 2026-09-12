package com.carlos.apifacturacion.dto.request;

import com.carlos.apifacturacion.dto.enums.VoucherType;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class InvoiceRequest {

    @NotNull(message = "El tipo de comprobante es obligatorio")
    private VoucherType voucherType;

    @NotBlank(message = "El nombre o razón social es obligatorio")
    private String clientName;

    @NotBlank(message = "El número de documento (DNI/RUC) es obligatorio")
    private String clientDocument;

    @NotEmpty(message = "Debe agregar al menos un ítem al comprobante")
    private List<InvoiceDetailRequest> items;

    @Data
    public static class InvoiceDetailRequest {
        @NotNull(message = "El ID del producto es obligatorio")
        private Long productId;

        @NotNull(message = "La cantidad es obligatoria")
        @Min(value = 1, message = "La cantidad mínima es 1")
        private Integer quantity;
    }
}