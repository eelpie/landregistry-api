package uk.co.eelpieconsulting.landregistry.daos;

import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import uk.co.eelpieconsulting.landregistry.model.PricePaid;
import uk.co.eelpieconsulting.landregistry.zoopla.BigDecimalConvertor;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

@Component
public class DataSourceFactory {
	
	private final static Logger log = Logger.getLogger(DataSourceFactory.class);
	
	private final Datastore datastore;
	
	@Autowired
	public DataSourceFactory(@Value("${mongoHost}") String mongoHost, @Value("${mongoDatabase}") String mongoDatabase) throws UnknownHostException {
		this.datastore = connect(mongoHost,  mongoDatabase);
	}
	
	public Datastore getDatastore() throws UnknownHostException, MongoException {	
		return datastore;
	}

	private Datastore connect(String mongoHost, String mongoDatabase) throws UnknownHostException {
		Morphia morphia = new Morphia();
		morphia.getMapper().getConverters().addConverter(BigDecimalConvertor.class);
		
		log.info("Connecting to mongo host: " + mongoHost);
		Mongo m = new Mongo(mongoHost);
		
		log.info("Creating datastore with database: " + mongoDatabase);
		final Datastore dataStore = morphia.createDatastore(m, mongoDatabase);
		
		log.info("Mapping classes");
		morphia.map(PricePaid.class);
		
		log.info("Ensuring indexes");
		dataStore.ensureIndexes();
		
		log.info("Returning datastore");
		return dataStore;
	}
	
}
