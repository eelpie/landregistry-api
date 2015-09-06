package uk.co.eelpieconsulting.landregistry.zoopla;

import java.util.List;

import org.junit.Test;

public class ZooplaApiClientIT {

	private ZooplaApiClient client = new ZooplaApiClient("");
	
	@Test
	public void canRetrieveListingForArea() throws Exception {
		List<Listing> listingsForArea = client.getListingsForArea("Hampton, London");
		for (Listing listing : listingsForArea) {
			System.out.println(listing.getDisplayable_address() + " - " + listing.getPrice());
		}
		System.out.println(listingsForArea.size());
	}
	
}
