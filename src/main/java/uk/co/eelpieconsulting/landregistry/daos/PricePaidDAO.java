package uk.co.eelpieconsulting.landregistry.daos;

import java.net.UnknownHostException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.co.eelpieconsulting.common.geo.model.LatLong;
import uk.co.eelpieconsulting.landregistry.model.PricePaid;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.mongodb.MongoException;

@Component
public class PricePaidDAO {
				
	private static final String DATE_DESCENDING = "-date";
	
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
	
	public PricePaid getById(String id) {
		return datastore.find(PricePaid.class, "id", id).get();
	}
	
	public List<PricePaid> getForProperty(String property) {
		final Query<PricePaid> all = datastore.find(PricePaid.class, "property", property);
		return all.asList();
	}

	public List<PricePaid> getAll() {
		final Query<PricePaid> all = datastore.createQuery(PricePaid.class);
		return all.asList();
	}

	public List<PricePaid> near(LatLong latLong, double radius) {
	    final Query<PricePaid> query = datastore.createQuery(PricePaid.class).
                field("location").within(latLong.getLatitude(), latLong.getLongitude(), radius).
                order(DATE_DESCENDING);
	    return query.asList();
	}

	public void delete(String id) {
		datastore.delete(PricePaid.class, id);
	}
	
}
