package com.info.DynamicPdfGenerator.service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.info.DynamicPdfGenerator.model.Item;
import com.info.DynamicPdfGenerator.model.PdfData;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.google.common.hash.Hashing;

@Service
public class ThymeleafPdfGeneratorService implements PdfGeneratorService {

//    private static final String PDF_DIRECTORY = "F:\\DynamicPdfGenerator\\pdf\\directory";

    @Value("${pdf.directory}")
    private String pdfDirectory;


    private final TemplateEngine templateEngine;

    public ThymeleafPdfGeneratorService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }


    @Override
    public byte[] generatePdf(PdfData data) {
        String pdfFilePath = getFilePath(data);
        File pdfFile = new File(pdfFilePath);

        if (!pdfFile.exists()) {
            // Create a Thymeleaf context and add data to it
            Context context = new Context();
            context.setVariable("data", data);

            // Process the Thymeleaf template to HTML
            String html = templateEngine.process("pdf-template", context);

            // Generate PDF from HTML using Flying Saucer
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            try {
                renderer.createPDF(outputStream);
                savePdfToStorage(pdfFilePath, outputStream.toByteArray());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return loadPdfFromStorage(pdfFilePath);
    }






    private String getFilePath(PdfData data) {
        StringBuilder itemsHash = new StringBuilder();
        for (Item item : data.getItems()) {
            String itemHash = Hashing.sha256()
                    .hashString(item.getName() + item.getQuantity() + item.getRate() + item.getAmount(),
                            StandardCharsets.UTF_8)
                    .toString();
            itemsHash.append(itemHash);
        }
        String combinedHash = Hashing.sha256()
                .hashString(itemsHash.toString(), StandardCharsets.UTF_8)
                .toString();

        String fileName = String.format("pdf_%s_%s_%s_%s_%s.pdf",
                data.getSeller().replace(" ", "_"),
                data.getBuyer().replace(" ", "_"),
                data.getSellerGstin(),
                data.getBuyerGstin(),
                combinedHash);
        return pdfDirectory + File.separator + fileName;
    }





    private void generateAndSavePdf(PdfData data, String pdfFilePath) {
        try {
            // Create a Thymeleaf context and add data to it
            Context context = new Context();
            context.setVariable("data", data);

            // Process the Thymeleaf template to HTML
            String html = templateEngine.process("pdf-template", context);

            // Generate PDF from HTML using Flying Saucer
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream);

            // Save the PDF to the storage directory
            savePdfToStorage(pdfFilePath, outputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void savePdfToStorage(String filePath, byte[] pdfBytes) {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(pdfBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] loadPdfFromStorage(String filePath) {
        try {
            return Files.readAllBytes(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }


}
