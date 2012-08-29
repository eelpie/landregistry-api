package uk.co.eelpieconsulting.landregistry.daos;

import java.net.UnknownHostException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.co.eelpieconsulting.landregistry.model.PricePaid;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.mongodb.MongoException;

@Component
public class PricePaidDAO {
			
	private static final double NEAR_RADIUS = 0.02;
	
	private final Datastore datastore;
	
	@Autowired
	public PricePaidDAO(DataSourceFactory dataSourceFactory) throws UnknownHostException, MongoException {
		this.datastore = dataSourceFactory.getDatastore();
	}
	
	public void save(PricePaid stop) {
		datastore.save(stop);		
	}

	public void removeAll() {
		final Query<PricePaid> allStops = datastore.createQuery(PricePaid.class);
		datastore.delete(allStops);
	}

	public List<PricePaid> getAll() {
		final Query<PricePaid> all = datastore.createQuery(PricePaid.class);
		return all.asList();
	}

	public List<PricePaid> near(double latitude, double longitude) {
	    final Query<PricePaid> query = datastore.createQuery(PricePaid.class).
                field("location").within(latitude, longitude, NEAR_RADIUS).
                order("-date");
	    return query.asList();
	}

	public void delete(String id) {
		datastore.delete(PricePaid.class, id);
	}
	
}
