package com.carlos.apifacturacion.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductRequest {

    @NotBlank(message = "El código no puede estar vacío")
    @Size(max = 50, message = "El código no debe superar 50 caracteres")
    private String code;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no debe superar 100 caracteres")
    private String name;

    private String description;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    private BigDecimal price;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;
}