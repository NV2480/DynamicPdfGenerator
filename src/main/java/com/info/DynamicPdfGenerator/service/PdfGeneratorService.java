package com.info.DynamicPdfGenerator.service;

import com.info.DynamicPdfGenerator.model.PdfData;

public interface PdfGeneratorService {
    byte[] generatePdf(PdfData data);
}
