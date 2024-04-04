package com.info.DynamicPdfGenerator;



import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.info.DynamicPdfGenerator.controller.PdfController;
import com.info.DynamicPdfGenerator.model.PdfData;
import com.info.DynamicPdfGenerator.service.PdfGeneratorService;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class DynamicPdfGeneratorApplicationTests {

	@Test
	public void testGeneratePdf() {
		PdfGeneratorService pdfGeneratorService = mock(PdfGeneratorService.class);
		when(pdfGeneratorService.generatePdf(any(PdfData.class))).thenReturn(new byte[0]);

		// Create an instance of PdfController with the mock service
		PdfController pdfController = new PdfController(pdfGeneratorService);

		// Call the generatePdf method
		ResponseEntity<byte[]> response = pdfController.generatePdf(new PdfData());

		// Verify that the service method was called
		verify(pdfGeneratorService, times(1)).generatePdf(any(PdfData.class));

		// Verify the response
		assertEquals(MediaType.APPLICATION_PDF, response.getHeaders().getContentType());
		assertNotNull(response.getBody());

	}

	@Test
	public void testGeneratePdfWhenServiceThrowsException() {

		PdfGeneratorService pdfGeneratorService = mock(PdfGeneratorService.class);
		when(pdfGeneratorService.generatePdf(any(PdfData.class))).thenThrow(RuntimeException.class);

		// Create an instance of PdfController with the mock service
		PdfController pdfController = new PdfController(pdfGeneratorService);

		// Call the generatePdf method
		assertThrows(RuntimeException.class, () -> pdfController.generatePdf(new PdfData()));

		// Verify that the service method was called
		verify(pdfGeneratorService, times(1)).generatePdf(any(PdfData.class));
	}

	@Test
	public void testGeneratePdfWithEmptyData() {

		PdfGeneratorService pdfGeneratorService = mock(PdfGeneratorService.class);
		when(pdfGeneratorService.generatePdf(any(PdfData.class))).thenReturn(new byte[0]);

		// Create an instance of PdfController with the mock service
		PdfController pdfController = new PdfController(pdfGeneratorService);

		// Call the generatePdf method with empty data
		ResponseEntity<byte[]> response = pdfController.generatePdf(new PdfData());

		// Verify that the service method was called
		verify(pdfGeneratorService, times(1)).generatePdf(any(PdfData.class));

		// Verify the response
		assertEquals(MediaType.APPLICATION_PDF, response.getHeaders().getContentType());
		assertNotNull(response.getBody());
	}
}



