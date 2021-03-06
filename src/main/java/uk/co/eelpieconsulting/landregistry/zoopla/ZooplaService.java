package uk.co.eelpieconsulting.landregistry.zoopla;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import uk.co.eelpieconsulting.common.http.HttpBadRequestException;
import uk.co.eelpieconsulting.common.http.HttpFetchException;
import uk.co.eelpieconsulting.common.http.HttpFetcher;
import uk.co.eelpieconsulting.common.http.HttpForbiddenException;
import uk.co.eelpieconsulting.common.http.HttpNotFoundException;
import uk.co.eelpieconsulting.landregistry.daos.ZooplaDAO;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

@Component
public class ZooplaService {

	private final static Logger log = Logger.getLogger(ZooplaService.class);
	
	private ZooplaApiClient client;
	private ZooplaDAO zooplaDAO;
	
	@Autowired
	private ZooplaService(ZooplaDAO zooplaDAO, @Value("${zooplaApiKey}") String apiKey) {
		this.client = new ZooplaApiClient(apiKey);
		this.zooplaDAO = zooplaDAO;
	}
	
	@Scheduled(fixedRate= 43200000)
	public void query() throws JsonParseException, JsonMappingException, HttpNotFoundException, HttpBadRequestException, HttpForbiddenException, HttpFetchException, IOException {
		final List<String> areasOfInterest = Lists.newArrayList("Hampton, London", "Twickenham, London", "Bournemouth, Dorset", "Cambridge, Cambridgeshire");	
		
		log.info("Starting import for areas of interest: " + areasOfInterest);
		for (String area : areasOfInterest) {
			List<Listing> listings = client.getListingsForArea(area);
			for (Listing listing : listings) {
				final String listingSnapshotId = listing.getListing_id() + "-" + DateTime.now().getMillis();
				log.info("Saving listing snapshot: " + listingSnapshotId + " / " + listing.getDetails_url());
				listing.setId(listingSnapshotId);
				zooplaDAO.save(listing);
			}
			
			for (Listing listing : listings) {
				final String imageUrl = listing.getImage_url();
				if (!Strings.isNullOrEmpty(imageUrl) && !zooplaDAO.imageExists(imageUrl)) {
					try {
						zooplaDAO.saveImage(new Image(imageUrl, new HttpFetcher().getBytes(imageUrl)));
					} catch (Exception e) {
						log.error("Error while saving image", e);
					}
				}
				
				final String floorPlanUrl = listing.getFloor_plan();
				if (!Strings.isNullOrEmpty(floorPlanUrl) && !zooplaDAO.imageExists(floorPlanUrl.replaceAll(" ", ""))) {
					if (!floorPlanUrl.contains("metropix")) {	// Hangs?
						try {						
							zooplaDAO.saveImage(new Image(floorPlanUrl, new HttpFetcher().getBytes(floorPlanUrl.replaceAll(" ", ""))));
						} catch (Exception e) {
							log.error("Error while saving image", e);
						}
					}
				}
			}			
		}
		log.info("Import completed");
		
	}
	
}
