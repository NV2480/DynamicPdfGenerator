package com.info.DynamicPdfGenerator.controller;

import com.info.DynamicPdfGenerator.model.PdfData;
import com.info.DynamicPdfGenerator.service.PdfGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pdf")
public class PdfController {

    private final PdfGeneratorService pdfGeneratorService;

    @Autowired
    public PdfController(PdfGeneratorService pdfGeneratorService) {
        this.pdfGeneratorService = pdfGeneratorService;
    }

    @PostMapping("/generate")
    public ResponseEntity<byte[]> generatePdf(@RequestBody PdfData data) {
        byte[] pdfBytes = pdfGeneratorService.generatePdf(data);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}

