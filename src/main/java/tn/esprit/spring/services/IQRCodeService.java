package tn.esprit.spring.services;

public interface IQRCodeService {
//	byte[] generateQRCode(String qrContent, int width, int height, int idt);
	
	boolean generateQRCode(String qrCodeContent, String filePath, int width, int height);

}
