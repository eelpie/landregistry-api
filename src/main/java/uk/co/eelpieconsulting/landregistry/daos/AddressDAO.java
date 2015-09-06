package uk.co.eelpieconsulting.landregistry.daos;

import java.net.UnknownHostException;

import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.co.eelpieconsulting.landregistry.model.Address;

import com.mongodb.MongoException;

@Component
public class AddressDAO {
					
	private final Datastore datastore;
	
	@Autowired
	public AddressDAO(DataStoreFactory dataStoreFactory) throws UnknownHostException, MongoException {
		this.datastore = dataStoreFactory.getDs();
	}
	
	public void save(Address address) {
		datastore.save(address);		
	}
	
}
