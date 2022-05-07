package tn.esprit.spring.controllers;

import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.services.IPdfGenaratorService;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
@RestController
@RequestMapping("/pdf")
public class PdfGeneratorRestController {
	private IPdfGenaratorService pdfGeneratorService;

    public PdfGeneratorRestController(IPdfGenaratorService pdfGeneratorService) {
        this.pdfGeneratorService = pdfGeneratorService;
    }

    @GetMapping("/pdf/generate")
    public void generatePDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

       // this.pdfGeneratorService.export(response);
    }
}
