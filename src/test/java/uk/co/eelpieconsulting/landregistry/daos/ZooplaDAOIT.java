package uk.co.eelpieconsulting.landregistry.daos;

import java.net.UnknownHostException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.mongodb.MongoException;

public class ZooplaDAOIT {

	private ZooplaDAO zooplaDAO;
	
	@Before
	public void setup() throws UnknownHostException, MongoException {
		DataStoreFactory dataSourceFactory = new DataStoreFactory("ubuntu.local", "landreg", "", "");
		zooplaDAO = new ZooplaDAO(dataSourceFactory);
	}

	@Test
	public void canFetchPricesPaidForStreet() throws UnknownHostException, MongoException {		
		final List<String> streets = zooplaDAO.addresses();
		System.out.println(streets.size());
		for (String street : streets) {
			System.out.println(street);
		}
	}
	
}
