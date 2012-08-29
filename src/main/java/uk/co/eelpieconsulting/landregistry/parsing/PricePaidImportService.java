package uk.co.eelpieconsulting.landregistry.parsing;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.co.eelpieconsulting.common.geo.LatLong;
import uk.co.eelpieconsulting.landregistry.daos.PricePaidDAO;
import uk.co.eelpieconsulting.landregistry.model.PricePaid;
import uk.co.eelpieconsulting.landregistry.model.PricePaidLine;

@Component
public class PricePaidImportService {
	
	private final static Logger log = Logger.getLogger(PricePaidImportService.class);
	
	private PricePaidFileFinder pricePaidFileFinder;
	private PricePaidFileParser pricePaidFileParser;
	private PostcodeService postcodeService;
	private PricePaidDAO pricePaidDAO;
	
	@Autowired
	public PricePaidImportService(PricePaidFileFinder pricePaidFileFinder, PricePaidFileParser pricePaidFileParser, PostcodeService postcodeService, PricePaidDAO pricePaidDAO) {
		this.pricePaidFileFinder = pricePaidFileFinder;
		this.pricePaidFileParser = pricePaidFileParser;
		this.postcodeService = postcodeService;
		this.pricePaidDAO = pricePaidDAO;
	}
	
	public void importPricePaidFiles() throws IOException, ParseException {		
		log.info("Starting import");
		
		final List<File> filesToParse = pricePaidFileFinder.getFilesInAscendingOrder();
		log.info("Found " + filesToParse.size() + " files to import");
		
		pricePaidDAO.removeAll();
		for (File file : filesToParse) {
			log.info("Processing file: " + file.getAbsolutePath());
			List<PricePaidLine> lines = pricePaidFileParser.parsePriceDataFile(file);
			log.info("Parsed " + lines.size() + " lines from file");
	
			log.info("Resolving postcodes and saving records");
			for (PricePaidLine line : lines) {
				
				final LatLong latLong = postcodeService.getLatLongFor(line.getPostcode());
				final Double latitude = latLong != null ? latLong.getLatitude() : null;
				final Double longitude =  latLong != null ? latLong.getLongitude() : null;
				
				pricePaidDAO.save(new PricePaid(line.getId(), line.getPrice(),
						line.getDate(), line.getPostcode(), line.getType(),
						line.isNewBuild(), line.getDuration(), line.getPOAN(),
						line.getSOAN(), line.getStreet(), line.getLocality(),
						line.getDistrict(), line.getBorough(),
						line.getCounty(), latitude, longitude));
			}
		}
		
		log.info("Import complete");

	}
	
}
