package com.carlos.apifacturacion.service.impl;

import com.carlos.apifacturacion.dto.enums.VoucherType;
import com.carlos.apifacturacion.dto.request.InvoiceRequest;
import com.carlos.apifacturacion.dto.response.InvoiceResponse;
import com.carlos.apifacturacion.entity.Invoice;
import com.carlos.apifacturacion.entity.InvoiceDetail;
import com.carlos.apifacturacion.entity.Product;
import com.carlos.apifacturacion.mapper.InvoiceMapper;
import com.carlos.apifacturacion.repository.InvoiceRepository;
import com.carlos.apifacturacion.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl {

    private final InvoiceRepository invoiceRepository;
    private final ProductRepository productRepository;
    private final InvoiceMapper invoiceMapper;

    @Transactional
    public InvoiceResponse createInvoice(InvoiceRequest request) {
        String prefix = request.getVoucherType() == VoucherType.BOLETA ? "B001-" : "F001-";
        String generatedNumber = prefix + String.format("%08d", System.currentTimeMillis() % 100000000);

        Invoice invoice = Invoice.builder()
                .voucherType(request.getVoucherType())
                .invoiceNumber(generatedNumber)
                .clientName(request.getClientName())
                .clientDocument(request.getClientDocument())
                .status("EMITIDO")
                .details(new ArrayList<>())
                .build();

        BigDecimal total = BigDecimal.ZERO;

        for (InvoiceRequest.InvoiceDetailRequest item : request.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado con ID: " + item.getProductId()));

            if (!product.getActive()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El producto '" + product.getName() + "' está inactivo.");
            }

            if (product.getStock() < item.getQuantity()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stock insuficiente para '" + product.getName() + "'. Disponible: " + product.getStock());
            }

            // Descontar stock
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);

            BigDecimal lineSubtotal = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            total = total.add(lineSubtotal);

            InvoiceDetail detail = InvoiceDetail.builder()
                    .invoice(invoice)
                    .product(product)
                    .quantity(item.getQuantity())
                    .unitPrice(product.getPrice())
                    .subtotal(lineSubtotal)
                    .build();

            invoice.getDetails().add(detail);
        }

        // Cálculos de subtotal e IGV (18%)
        BigDecimal subtotal = total.divide(BigDecimal.valueOf(1.18), 2, RoundingMode.HALF_UP);
        BigDecimal tax = total.subtract(subtotal);

        invoice.setSubtotal(subtotal);
        invoice.setTax(tax);
        invoice.setTotalAmount(total);

        Invoice saved = invoiceRepository.save(invoice);
        return invoiceMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<InvoiceResponse> getAllInvoices() {
        return invoiceMapper.toResponseList(invoiceRepository.findAll());
    }

    @Transactional
    public InvoiceResponse anularInvoice(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Comprobante no encontrado con id: " + id));

        if (invoice.getStatus().equalsIgnoreCase("ANULADO")) {
            throw new IllegalStateException("El comprobante ya se encuentra anulado");
        }

        invoice.setStatus("ANULADO");
        Invoice invoiceActualizado = invoiceRepository.save(invoice);

        return invoiceMapper.toResponse(invoiceActualizado);
    }

}