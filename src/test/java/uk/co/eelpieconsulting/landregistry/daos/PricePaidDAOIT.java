package uk.co.eelpieconsulting.landregistry.daos;

import java.net.UnknownHostException;
import java.util.List;

import org.junit.Test;

import uk.co.eelpieconsulting.landregistry.model.PricePaid;

import com.mongodb.MongoException;

public class PricePaidDAOIT {

	@Test
	public void canFetchPricesPaidForStreet() throws UnknownHostException, MongoException {
		DataSourceFactory dataSourceFactory = new DataSourceFactory("ubuntu.local", "landreg");
		PricePaidDAO pricePaidDAO = new PricePaidDAO(dataSourceFactory);
		
		final List<PricePaid> pricesPaid = pricePaidDAO.find("RICHMOND UPON THAMES", "TWICKENHAM", "HEATHFIELD NORTH");		
		for (PricePaid string : pricesPaid) {
			System.out.println(string.getPOAN() + ", " + string.getDate() + ", " + string.getPrice());
		}
		
		System.out.println(pricesPaid.size());
	}

}
