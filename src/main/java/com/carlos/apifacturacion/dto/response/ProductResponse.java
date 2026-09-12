package com.carlos.apifacturacion.dto.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductResponse {

    private Long id;
    private String code;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private Boolean active;
}
