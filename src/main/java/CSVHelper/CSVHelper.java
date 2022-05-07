package CSVHelper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.springframework.web.multipart.MultipartFile;

import tn.esprit.spring.entities.Donationuser;



public class CSVHelper {
	public static String TYPE = "text/csv";
	  static String[] HEADERs = {"firstName", "lastName", "monthlyincome","nbPersFamily","nbStudentsInFamily","medicalNeed","socialNeed","needy","unemployed","amoutwon"};

	  public static boolean hasCSVFormat(MultipartFile file) {
	    if (TYPE.equals(file.getContentType())
	    		|| file.getContentType().equals("application/vnd.ms-excel")) {
	      return true;
	    }

	    return false;
	  }

	  public static List<Donationuser> csvToTutorials(InputStream is) {
	    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        CSVParser csvParser = new CSVParser(fileReader,
	            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

	      List<Donationuser> dlist = new ArrayList<>();

	      Iterable<CSVRecord> csvRecords = csvParser.getRecords();

	      for (CSVRecord csvRecord : csvRecords) {
	    	  Donationuser d = new Donationuser();
	            // d.setId(Integer.valueOf(csvRecord.get("id")));
	              d.setFirstName(csvRecord.get("firstName"));
	              d.setLastName(csvRecord.get("lastName"));
	              d.setMonthlyincome(Integer.valueOf(csvRecord.get("monthlyincome")));
	              d.setNbPersFamily(Integer.valueOf(csvRecord.get("nbPersFamily")));
	              
	              d.setNbStudentsInFamily(Integer.valueOf(csvRecord.get("nbStudentsInFamily")));
	              d.setMedicalNeed(csvRecord.get("medicalNeed"));
	              d.setSocialNeed(csvRecord.get("socialNeed"));
	              d.setAmoutwon(Integer.valueOf(csvRecord.get("amoutwon")));
	              d.setNeedy(csvRecord.get("needy"));
	              d.setUnemployed(csvRecord.get("unemployed"));
	              dlist.add(d);
	      }

	      return dlist;
	    } catch (IOException e) {
	      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
	    }
	  }

	  public static ByteArrayInputStream tutorialsToCSV(List<Donationuser> dlist) {
	    final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

	    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
	        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
	      for (Donationuser d : dlist) {
	        List<String> data = Arrays.asList(
	              String.valueOf(d.getId()),
	              d.getFirstName(),
	              d.getLastName(),
	             d.getMedicalNeed(),
	             d.getNeedy(),
	             d.getUnemployed(),
	             d.getSocialNeed(),
	             String.valueOf( d.getMonthlyincome()),
	             String.valueOf(  d.getAmoutwon()),
	             String.valueOf( d.getNbPersFamily()),
	             String.valueOf(  d.getNbStudentsInFamily())
	            );

	        csvPrinter.printRecord(data);
	      }

	      csvPrinter.flush();
	      return new ByteArrayInputStream(out.toByteArray());
	    } catch (IOException e) {
	      throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
	    }
	  }
}
