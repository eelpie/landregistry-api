package uk.co.eelpieconsulting.landregistry.daos;

import java.net.UnknownHostException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.co.eelpieconsulting.landregistry.zoopla.Image;
import uk.co.eelpieconsulting.landregistry.zoopla.Listing;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.mongodb.MongoException;

@Component
public class ZooplaDAO {
					
	private final Datastore datastore;
	
	@Autowired
	public ZooplaDAO(DataSourceFactory dataSourceFactory) throws UnknownHostException, MongoException {
		this.datastore = dataSourceFactory.getDatastore();
	}
	
	public List<Listing> getAll() {
		return datastore.find(Listing.class).limit(1000).asList();
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
	
}
