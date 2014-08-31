package uk.co.eelpieconsulting.landregistry.daos;

import java.net.UnknownHostException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import uk.co.eelpieconsulting.landregistry.model.PricePaid;

import com.mongodb.MongoException;

public class PricePaidDAOIT {

	private PricePaidDAO pricePaidDAO;
	
	@Before
	public void setup() throws UnknownHostException, MongoException {
		DataSourceFactory dataSourceFactory = new DataSourceFactory("ubuntu.local", "landreg");
		pricePaidDAO = new PricePaidDAO(dataSourceFactory);
	}

	@Test
	public void canFetchPricesPaidForStreet() throws UnknownHostException, MongoException {
		
		final List<PricePaid> pricesPaid = pricePaidDAO.find("RICHMOND UPON THAMES", "TWICKENHAM", "HEATHFIELD NORTH");		
		for (PricePaid string : pricesPaid) {
			System.out.println(string.getPOAN() + ", " + string.getDate() + ", " + string.getPrice());
		}
		
		System.out.println(pricesPaid.size());
	}
	
	@Test
	public void canGetDistinctCounties() throws Exception {
		System.out.println(pricePaidDAO.getCounties());
	}
		
	@Test
	public void canGetDistinctBoroughsWithInCounty() throws Exception {
		System.out.println(pricePaidDAO.getBoroughs("GREATER LONDON"));
	}
	

}
