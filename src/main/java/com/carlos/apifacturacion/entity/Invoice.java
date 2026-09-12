package com.carlos.apifacturacion.entity;

import com.carlos.apifacturacion.dto.enums.VoucherType;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "invoices")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private VoucherType voucherType; // BOLETA o FACTURA

    @Column(unique = true, nullable = false, length = 20)
    private String invoiceNumber; // B001-00000001 o F001-00000001

    @Column(nullable = false)
    private String clientName;

    @Column(length = 20)
    private String clientDocument; // DNI o RUC

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal tax; // IGV (18%)

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(nullable = false)
    @Builder.Default
    private String status = "EMITIDO";

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<InvoiceDetail> details = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
