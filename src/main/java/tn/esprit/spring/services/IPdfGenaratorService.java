package tn.esprit.spring.services;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

public interface IPdfGenaratorService {
	void generatePdfFile(String templateName, Map<String, Object> data, String pdfFileName);

}
