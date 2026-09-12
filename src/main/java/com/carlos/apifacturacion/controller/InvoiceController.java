package com.carlos.apifacturacion.controller;

import com.carlos.apifacturacion.dto.request.InvoiceRequest;
import com.carlos.apifacturacion.dto.response.InvoiceResponse;
import com.carlos.apifacturacion.service.impl.InvoiceServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apiFA/invoices")
@RequiredArgsConstructor
@CrossOrigin(origins = "${app.cors.allowed-origin}")
public class InvoiceController {

    private final InvoiceServiceImpl invoiceService;

    @PostMapping("/create")
    public ResponseEntity<InvoiceResponse> createInvoice(@Valid @RequestBody InvoiceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(invoiceService.createInvoice(request));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<InvoiceResponse>> getAllInvoices() {
        return ResponseEntity.ok(invoiceService.getAllInvoices());
    }

    @PatchMapping("/{id}/anular")
    public ResponseEntity<InvoiceResponse> anularInvoice(@PathVariable Long id) {
        return ResponseEntity.ok(invoiceService.anularInvoice(id));
    }
}
