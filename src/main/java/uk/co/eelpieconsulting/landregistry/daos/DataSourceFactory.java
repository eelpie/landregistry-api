package uk.co.eelpieconsulting.landregistry.daos;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

@Component
public class DataSourceFactory {
	
	@Value("${mongoHost}")
    private String mongoHost;
    
	@Value("${mongoDatabase}")
    private String mongoDatabase;
        
	public DataSourceFactory() {
	}

	public DataSourceFactory(String mongoHost, String mongoDatabase) {
		this.mongoHost = mongoHost;
		this.mongoDatabase = mongoDatabase;
	}
	
	public Datastore getDatastore() throws UnknownHostException, MongoException {	
		Morphia morphia = new Morphia();
		
		Mongo m = new Mongo(mongoHost);
		final Datastore dataStore = morphia.createDatastore(m, mongoDatabase);
		dataStore.ensureIndexes();
		return dataStore;
	}
	
}
