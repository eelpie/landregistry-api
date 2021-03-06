package uk.co.eelpieconsulting.landregistry.daos;

import java.net.UnknownHostException;
import java.util.List;

import org.apache.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import uk.co.eelpieconsulting.landregistry.model.Address;
import uk.co.eelpieconsulting.landregistry.zoopla.Listing;
	  
import com.google.common.collect.Lists;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;

@Component
public class DataStoreFactory {

	private static final Logger log = Logger.getLogger(DataStoreFactory.class);
	    
	private final ServerAddress serverAddress;
	private final String mongoDatabase;
	
	private final List<MongoCredential> credentials;

	private Datastore datastore;

	@Autowired
	public DataStoreFactory(@Value("${mongoHost}") String mongoHost,
			@Value("${mongoDatabase}") String mongoDatabase,
			@Value("${mongoUser}") String mongoUser,
			@Value("${mongoPassword}") String mongoPassword) throws UnknownHostException, MongoException {
		this.serverAddress = new ServerAddress(mongoHost);
		this.mongoDatabase = mongoDatabase;
		
		MongoCredential createCredential = MongoCredential.createCredential(mongoUser, mongoDatabase, mongoPassword.toCharArray());
		this.credentials = Lists.newArrayList(createCredential);
	}
	
	public Datastore getDs() {
		if (datastore == null) {
			datastore = createDataStore(mongoDatabase);
			datastore.ensureIndexes();
		}
		return datastore;
	}
	
	private Datastore createDataStore(String database) {
		Morphia morphia = new Morphia();
		morphia.map(Address.class);
		morphia.map(Listing.class);
		
		try {
			MongoClient m = new MongoClient(serverAddress, credentials);
			return morphia.createDatastore(m, database);
			
		} catch (MongoException e) {
			log.error(e);
			throw new RuntimeException(e);
		}		
	}
	
}