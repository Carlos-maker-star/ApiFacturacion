package com.carlos.apifacturacion.repository;

import com.carlos.apifacturacion.dto.enums.VoucherType;
import com.carlos.apifacturacion.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    // Buscar por número de comprobante (Ej: B001-00000001)
    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);

    // Filtrar por tipo (BOLETA o FACTURA) para las pestañas de la UI
    List<Invoice> findByVoucherType(VoucherType voucherType);
}