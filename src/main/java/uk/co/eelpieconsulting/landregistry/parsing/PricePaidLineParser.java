package uk.co.eelpieconsulting.landregistry.parsing;

import java.util.Date;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;

import uk.co.eelpieconsulting.landregistry.model.Duration;
import uk.co.eelpieconsulting.landregistry.model.PricePaidLine;
import uk.co.eelpieconsulting.landregistry.model.PropertyType;
import uk.co.eelpieconsulting.landregistry.model.RecordStatus;

@Component
public class PricePaidLineParser {
	
	private final static DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");

	private final Map<String, PropertyType> propertyTypes;
	private final Map<String, Duration> durationTypes;
	private final Map<String, RecordStatus> recordStatuses;
	
	public PricePaidLineParser() {
		propertyTypes = Maps.newHashMap();
		propertyTypes.put("D", PropertyType.DETACHED);
		propertyTypes.put("S", PropertyType.SEMIDETACHED);
		propertyTypes.put("T", PropertyType.TERRACED);
		propertyTypes.put("F", PropertyType.FLATSMAISONETTES);
		
		durationTypes = Maps.newHashMap();
		durationTypes.put("F", Duration.FREEHOLD);
		durationTypes.put("L", Duration.LEASEHOLD);
		
		recordStatuses = Maps.newHashMap();
		recordStatuses.put("A", RecordStatus.ADDED);
		recordStatuses.put("C", RecordStatus.CHANGED);
		recordStatuses.put("D", RecordStatus.DELETED);	
	}
	
	public PricePaidLine parse(String[] line) {		
		return new PricePaidLine(
				parseId(line[0]),
				parsePrice(line[1]), 
				parseDate(line[2]),
				line[3],
				parseType(line[4]), 
				line[5].equals("Y") ? true : false, 
				parseDuration(line[6]),
				line[7],
				line[8],
				line[9],
				line[10],
				line[11],
				line[12],
				line[13],
				parseRecordStatus(line[14])	    	
		);
	}
	
	private String parseId(String idWithBrackets) {		
		return idWithBrackets.replace("{", "").replace("}", "");
	}

	private Date parseDate(String dateString) {
		DateTimeFormatter[] formats = new DateTimeFormatter[]{DATE_FORMAT};
		for (DateTimeFormatter dateTimeFormatter : formats) {
			try {
				DateTime dateTime = dateTimeFormatter.parseDateTime(dateString);
				return dateTime.toDate();
			} catch (IllegalArgumentException e) {			
			}
		}
		
		throw new IllegalArgumentException("Date is not in any of the expected formats: " + dateString);
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
