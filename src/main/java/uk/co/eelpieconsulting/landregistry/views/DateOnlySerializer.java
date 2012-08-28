package uk.co.eelpieconsulting.landregistry.views;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public class DateOnlySerializer extends JsonSerializer<Date> {
	
	private final static DateTimeFormatter dateFormat = ISODateTimeFormat.date();
	
	@Override
	public Class<Date> handledType() {
		return Date.class;
	}
	
	@Override
	public void serialize(Date date, JsonGenerator generator, SerializerProvider provider) throws IOException, JsonProcessingException {		
		generator.writeString(dateFormat.print(new DateTime(date)));
	}

}
