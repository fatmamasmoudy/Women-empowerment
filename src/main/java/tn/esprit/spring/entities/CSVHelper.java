package tn.esprit.spring.entities;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

public class CSVHelper {
	public static ByteArrayInputStream CvToCSV(List<CV> cvs) {
	    final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);
	    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
	        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
	      for (CV cv : cvs) {
	        List<String> data = Arrays.asList(
	              String.valueOf(cv.getIdCv()),
	             cv.getProfileTitle(),
	            cv.getAboutMe(),
	             cv.getInternetSite(),
	              cv.getDrivingLesence(),
	              cv.getLinkdIn(),
	             cv.getProfileDescription()
	             // cv.getCenterOfInterest(),
	            //  cv.getStages(),
	            //  cv.getSkills(),
	             // cv.getLanguages(),
	             // cv.getEducations(),
	             // cv.getWorkExperiences()
	              );
	               //      String.valueOf(cv.isPublished())
	      // );
	       csvPrinter.printRecord(data);
	      }
	               csvPrinter.flush();
	      return new ByteArrayInputStream(out.toByteArray());
	  
	    } catch (IOException e) {
	      throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
	    }
	  }
	public static String TYPE = "text/csv";
	  static String[] HEADERs = { "idCv", "profileTitle", "aboutMe", "internetSite" ,"drivingLesence","linkdIn","profileDescription","centerOfInterest"};
	  public static boolean hasCSVFormat(MultipartFile file) {
	    if (!TYPE.equals(file.getContentType())) {
	      return false;
	    }
	    return true;
	  }
	  public static List<CV> csvToCv(InputStream is) {
	    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        CSVParser csvParser = new CSVParser(fileReader,
	            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
	      List<CV> cvs = new ArrayList<CV>();
	      Iterable<CSVRecord> csvRecords = csvParser.getRecords();
	      for (CSVRecord csvRecord : csvRecords) {
	        CV cv = new CV(
	              Integer.parseInt(csvRecord.get("idCv")),
	              csvRecord.get("profileTitle"),
	              csvRecord.get("aboutMe"),
	             csvRecord.get("internetSite"),
	            csvRecord.get("drivingLesence"),
	            csvRecord.get("linkdIn"),
	            csvRecord.get("profileDescription"),
	            csvRecord.get("centerOfInterest"), null, null, null, null, null, null, null
	            );
	        cvs.add(cv);
	      }
	      return cvs;
	    } catch (IOException e) {
	      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
	    }
}
}