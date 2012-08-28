package uk.co.eelpieconsulting.landregistry.parsing;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.co.eelpieconsulting.landregistry.daos.PricePaidDAO;
import uk.co.eelpieconsulting.landregistry.model.PricePaid;
import uk.co.eelpieconsulting.landregistry.model.PricePaidLine;

@Component
public class PricePaidImportService {
	
	private PricePaidFileFinder pricePaidFileFinder;
	private PricePaidFileParser pricePaidFileParser;
	private PricePaidDAO pricePaidDAO;
	
	@Autowired
	public PricePaidImportService(PricePaidFileFinder pricePaidFileFinder, PricePaidFileParser pricePaidFileParser, PricePaidDAO pricePaidDAO) {
		this.pricePaidFileFinder = pricePaidFileFinder;
		this.pricePaidFileParser = pricePaidFileParser;
		this.pricePaidDAO = pricePaidDAO;
	}
	
	public void importPricePaidFiles() throws IOException, ParseException {		
		List<File> filesToParser = pricePaidFileFinder.getFilesInAscendingOrder();
		
		pricePaidDAO.removeAll();
		for (File file : filesToParser) {
			List<PricePaidLine> lines = pricePaidFileParser.parsePriceDataFile(file);
			for (PricePaidLine line : lines) {
				pricePaidDAO.save(new PricePaid(line.getId(), line.getPrice(), line.getDate(), line.getPostcode(), line.getType(), line.isNewBuild(), line.getDuration(), line.getPOAN(), line.getSOAN(), line.getStreet(), line.getLocality(), line.getDistrict(), line.getBorough(), line.getCounty()));
			}			
		}
	}
	
}
