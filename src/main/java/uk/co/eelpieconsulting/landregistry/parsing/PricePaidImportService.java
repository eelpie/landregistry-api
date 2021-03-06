package uk.co.eelpieconsulting.landregistry.parsing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.co.eelpieconsulting.common.geo.model.LatLong;
import uk.co.eelpieconsulting.landregistry.daos.PricePaidDAO;
import uk.co.eelpieconsulting.landregistry.model.PricePaid;
import uk.co.eelpieconsulting.landregistry.model.PricePaidLine;
import uk.co.eelpieconsulting.landregistry.model.RecordStatus;
import au.com.bytecode.opencsv.CSVReader;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;

@Component
public class PricePaidImportService {
	
	private final static Logger log = Logger.getLogger(PricePaidImportService.class);

	private final static Joiner dashJoiner = Joiner.on("-").skipNulls();
	
	private PricePaidFileFinder pricePaidFileFinder;
	private PostcodeService postcodeService;
	private PricePaidDAO pricePaidDAO;
	private PricePaidLineParser pricePaidLineParser;
	
	private final boolean resolvePostcodes = false;
	
	@Autowired
	public PricePaidImportService(PricePaidFileFinder pricePaidFileFinder, PostcodeService postcodeService,
			PricePaidDAO pricePaidDAO, PricePaidLineParser pricePaidLineParser) {
		this.pricePaidFileFinder = pricePaidFileFinder;
		this.postcodeService = postcodeService;
		this.pricePaidDAO = pricePaidDAO;
		this.pricePaidLineParser = pricePaidLineParser;			
	}
	
	public void importPricePaidFiles() throws IOException, ParseException {		
		log.info("Starting import");
		
		try {
			final List<File> filesToParse = pricePaidFileFinder.getFilesInAscendingOrder();
			log.info("Found " + filesToParse.size() + " files to import");
			
			pricePaidDAO.removeAll();	// TODO needs to be a drop - delete all is 10s per second slow
			
			int importedCount = 0;
			for (File file : filesToParse) {
				log.info("Processing file: " + file.getAbsolutePath());
				
				final CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(file)));
				String [] nextLine;
				while ((nextLine = reader.readNext()) != null) {
									
				 	final PricePaidLine line = pricePaidLineParser.parse(nextLine);			 	
					final PricePaid pricePaid = process(line);
					
					if (line.getRecordStatus() == RecordStatus.ADDED) {
						log.debug("Adding: " + pricePaid.toString());
						pricePaidDAO.save(pricePaid);
					
					} else if (line.getRecordStatus() == RecordStatus.CHANGED) { 
						log.debug("Changing: " + pricePaid.toString());
						pricePaidDAO.save(pricePaid);
						
					} else if (line.getRecordStatus() == RecordStatus.DELETED) {
						log.debug("Deleting: " + pricePaid.toString());
						pricePaidDAO.delete(pricePaid.getId());
					}
					
					importedCount ++;
					if (importedCount % 100 == 0) {
						log.info("Imported: " + importedCount);
					}
				}
				reader.close();
			}
			
		} catch (Exception e) {
			log.error("Prices paid import failed", e);
		}
		
		log.info("Import complete");
	}
	
	private PricePaid process(final PricePaidLine line) {
		final LatLong latLong = resolveLocation(line);
		final String property = dashJoiner.join(!Strings.isNullOrEmpty(line.getSOAN()) ? line.getSOAN() : null, 
				line.getPOAN(), line.getStreet(), line.getDistrict(), line.getBorough(), line.getCounty());

		return new PricePaid(line.getId(), property, line.getPrice(),
				line.getDate(), line.getPostcode(), line.getType(),
				line.isNewBuild(), line.getDuration(), line.getPOAN(),
				line.getSOAN(), line.getStreet(), line.getLocality(),
				line.getDistrict(), line.getBorough(),
				line.getCounty(), latLong);
	}

	private LatLong resolveLocation(PricePaidLine line) {
		if (resolvePostcodes) {
			return !Strings.isNullOrEmpty(line.getPostcode()) ? postcodeService.getLatLongFor(line.getPostcode()) : null;									
		}
		return null;
	}
	
}
