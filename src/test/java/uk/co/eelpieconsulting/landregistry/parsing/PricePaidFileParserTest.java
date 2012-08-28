package uk.co.eelpieconsulting.landregistry.parsing;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import uk.co.eelpieconsulting.landregistry.model.PricePaidLine;
import uk.co.eelpieconsulting.landregistry.model.PropertyType;
import uk.co.eelpieconsulting.landregistry.model.RecordStatus;
import uk.co.eelpieconsulting.landregistry.parsing.PricePaidFileParser;

public class PricePaidFileParserTest {

	private PricePaidFileParser parser;

	@Before
	public void setup() {
		parser = new PricePaidFileParser();
	}
	@Test
	public void canParseFile() throws Exception {		
	
		final File file = new File(this.getClass().getClassLoader().getResource("ppms-june-with-columns.csv").getFile());		
		final List<PricePaidLine> lines = parser.parsePriceDataFile(file);
		
		assertEquals(54675, lines.size());		
				
		final PricePaidLine line = lines.get(54422);
		
		assertEquals("D6FFE374-5325-43BC-AC99-793A4555149F", line.getId());
		assertEquals(32000, line.getPrice());
		assertEquals(new DateTime(2012, 6, 6, 0, 0 ,0).toDate(), line.getDate());
		assertEquals("LS8 3QN", line.getPostcode());
		assertEquals(PropertyType.TERRACED, line.getType());

		assertEquals("32", line.getPOAN());
		assertEquals("", line.getSOAN());
		assertEquals("SANDHURST GROVE", line.getStreet());
		assertEquals("", line.getLocality());
		assertEquals("LEEDS", line.getDistrict());
		assertEquals("WEST YORKSHIRE", line.getCounty());
		
		assertEquals(RecordStatus.ADDED, line.getRecordStatus());
		
		final PricePaidLine changedLine = lines.get(54424);
		assertEquals(RecordStatus.CHANGED, changedLine.getRecordStatus());
		
		final PricePaidLine lineWithAllAddressFields = lines.get(54415);
		
		assertEquals("GRANTCHESTER COURT", lineWithAllAddressFields.getPOAN());
		assertEquals("80", lineWithAllAddressFields.getSOAN());
		assertEquals("BIGNELL CROFT", lineWithAllAddressFields.getStreet());
		assertEquals("HIGHWOODS", lineWithAllAddressFields.getLocality());
		assertEquals("COLCHESTER", lineWithAllAddressFields.getDistrict());
		assertEquals("COLCHESTER", lineWithAllAddressFields.getBorough());
		assertEquals("ESSEX", lineWithAllAddressFields.getCounty());
	}
	
	@Test
	public void canParseFileContainingHourMinuteSecondDateFormat() throws Exception {
		final File file = new File(this.getClass().getClassLoader().getResource("PPMS_0312.csv").getFile());		
		parser.parsePriceDataFile(file);
	}
	
}
