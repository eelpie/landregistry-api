package uk.co.eelpieconsulting.landregistry.daos;

import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.co.eelpieconsulting.landregistry.zoopla.Image;
import uk.co.eelpieconsulting.landregistry.zoopla.Listing;

import com.mongodb.DBCollection;
import com.mongodb.MongoException;

@Component
public class ZooplaDAO {
					
	private final Datastore datastore;
	
	@Autowired
	public ZooplaDAO(DataStoreFactory dataStoreFactory) throws UnknownHostException, MongoException {
		this.datastore = dataStoreFactory.getDs();
	}
	
	public List<Listing> find(String q) {
		final Query<Listing> all = datastore.find(Listing.class, "displayable_address", q);
		return all.asList();
	}
	
	public void save(Listing listing) {
		datastore.save(listing);		
	}

	public void removeAll() {
		final Query<Listing> all = datastore.createQuery(Listing.class);
		datastore.delete(all);
	}

	public boolean imageExists(String imageUrl) {
		return datastore.find(Image.class, "url", imageUrl).get() != null;
	}
	
	public void saveImage(Image image) {
		datastore.save(image);		
	}

	public List<String> addresses() {
		DBCollection collection = datastore.getCollection(Listing.class);
		List<String> distinct = (List<String>) collection.distinct("displayable_address");
		Collections.sort(distinct);
		return distinct;
	}
	
}
