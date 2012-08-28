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
	
}
