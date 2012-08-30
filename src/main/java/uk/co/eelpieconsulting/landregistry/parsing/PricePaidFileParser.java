package uk.co.eelpieconsulting.landregistry.parsing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

import uk.co.eelpieconsulting.landregistry.model.Duration;
import uk.co.eelpieconsulting.landregistry.model.PricePaidLine;
import uk.co.eelpieconsulting.landregistry.model.PropertyType;
import uk.co.eelpieconsulting.landregistry.model.RecordStatus;
import au.com.bytecode.opencsv.CSVReader;

@Component
public class PricePaidFileParser {
	
	private final static Logger log = Logger.getLogger(PricePaidFileParser.class);
	
	private final static DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
	private final static DateTimeFormatter ALTERNATIVE_DATE_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
	private final static DateTimeFormatter YET_ANOTHER_DATE_FORMAT = DateTimeFormat.forPattern("dd/MM/yyyy");


	private Map<String, PropertyType> propertyTypes;
	private Map<String, Duration> durationTypes;
	private Map<String, RecordStatus> recordStatuses;
	
	public PricePaidFileParser() {
		propertyTypes = new HashMap<String, PropertyType>();
		propertyTypes.put("D", PropertyType.DETACHED);
		propertyTypes.put("S", PropertyType.SEMIDETACHED);
		propertyTypes.put("T", PropertyType.TERRACED);
		propertyTypes.put("F", PropertyType.FLATSMAISONETTES);
		
		durationTypes = new HashMap<String, Duration>();
		durationTypes.put("F", Duration.FREEHOLD);
		durationTypes.put("L", Duration.LEASEHOLD);
		
		recordStatuses = new HashMap<String, RecordStatus>();
		recordStatuses.put("A", RecordStatus.ADDED);
		recordStatuses.put("C", RecordStatus.CHANGED);
		recordStatuses.put("D", RecordStatus.DELETED);
	}
	
	public List<PricePaidLine> parsePriceDataFile(File file) throws IOException, ParseException {
		log.info("Parsing input: " + file.getAbsolutePath());		
		final List<PricePaidLine> lines = new ArrayList<PricePaidLine>();
		
	    final CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(file)));
	    String [] nextLine;
	    while ((nextLine = reader.readNext()) != null) {
	    	
	    	final PricePaidLine pricePaidLine = new PricePaidLine(
	    			parseId(nextLine[0]),
	    			parsePrice(nextLine[1]), 
	    			parseDate(nextLine[2]),
	    			nextLine[3],
	    			parseType(nextLine[4]), 
	    			nextLine[5].equals("Y") ? true : false, 
	    			parseDuration(nextLine[6]),
	    			nextLine[7],
	    			nextLine[8],
	    			nextLine[9],
	    			nextLine[10],
	    			nextLine[11],
	    			nextLine[12],
	    			nextLine[13],
	    			parseRecordStatus(nextLine[14])	    	
	    	);
	    	
	    	lines.add(pricePaidLine);
	    }
				
		log.info("Parsed " + lines.size() + " lines");
		return lines;
	}

	private String parseId(String idWithBrackets) {		
		return idWithBrackets.replace("{", "").replace("}", "");
	}

	private Date parseDate(String dateString) {
		DateTimeFormatter[] formats = new DateTimeFormatter[]{DATE_FORMAT, ALTERNATIVE_DATE_FORMAT, YET_ANOTHER_DATE_FORMAT};
		for (DateTimeFormatter dateTimeFormatter : formats) {
			try {
				DateTime dateTime = dateTimeFormatter.parseDateTime(dateString);
				return dateTime.toDate();
			} catch (IllegalArgumentException e) {			
			}
		}
		
		throw new IllegalArgumentException("Date is not in any of the expected formats");
	}
	
	private int parsePrice(String priceWithCommas) {
		return Integer.parseInt(priceWithCommas.replaceAll(",", "").replaceAll("\\D", ""));
	}
	
	private PropertyType parseType(String typeString) {
		return propertyTypes.get(typeString);
	}
	
	private Duration parseDuration(String durationString) {		
		return durationTypes.get(durationString);
	}
	
	private RecordStatus parseRecordStatus(String recordStatusString) {		
		return recordStatuses.get(recordStatusString);
	}
	
}
