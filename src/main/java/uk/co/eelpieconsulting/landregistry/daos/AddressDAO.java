package uk.co.eelpieconsulting.landregistry.daos;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.co.eelpieconsulting.landregistry.model.Address;

import com.google.code.morphia.Datastore;
import com.mongodb.MongoException;

@Component
public class AddressDAO {
					
	private final Datastore datastore;
	
	@Autowired
	public AddressDAO(DataSourceFactory dataSourceFactory) throws UnknownHostException, MongoException {
		this.datastore = dataSourceFactory.getDatastore();
	}
	
	public void save(Address address) {
		datastore.save(address);		
	}
	
}
