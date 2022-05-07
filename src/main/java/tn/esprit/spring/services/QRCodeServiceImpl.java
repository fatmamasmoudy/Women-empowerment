package tn.esprit.spring.services;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import tn.esprit.spring.entities.Training;
import tn.esprit.spring.repository.TrainingRepository;

//import dev.simplesolution.generateqr.service.QRCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;




import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
@Service
public class QRCodeServiceImpl implements IQRCodeService {
	
	
	private Logger logger = LoggerFactory.getLogger(QRCodeServiceImpl.class);

    @Override
    public boolean generateQRCode(String qrCodeContent, String filePath, int width, int height) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeContent, BarcodeFormat.QR_CODE, width, height);
            Path path = Paths.get(filePath);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
            return true;
        } catch (WriterException e) {
            logger.error("Error", e);
        } catch (IOException e) {
            logger.error("Error", e);
        }
        return false;
    }

//	@Override
//	public byte[] generateQRCode(String qrContent, int width, int height, int idt) {
//		try {
//            QRCodeWriter qrCodeWriter = new QRCodeWriter();
//            BitMatrix bitMatrix = qrCodeWriter.encode(qrContent, BarcodeFormat.QR_CODE, width, height);
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", byteArrayOutputStream);
//            
//            System.out.println(byteArrayOutputStream.toByteArray().toString());
//            return byteArrayOutputStream.toByteArray();
//            
//        } catch (WriterException e) {
//            logger.error(e.getMessage(), e);
//        } catch (IOException e) {
//            logger.error(e.getMessage(), e);
//        }
//        return null;
//	}

}
