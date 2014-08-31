package uk.co.eelpieconsulting.landregistry.daos;

import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.co.eelpieconsulting.common.geo.model.LatLong;
import uk.co.eelpieconsulting.landregistry.model.PricePaid;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.MongoException;

@Component
public class PricePaidDAO {
				
	private static final String BOROUGH = "borough";
	private static final String COUNTY = "county";
	private static final String DATE_DESCENDING = "-date";
	private static final String DISTRICT = "district";
	private static final String LOCALITY = "locality";
	private static final String STREET = "street";
	
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
	
	public List<PricePaid> find(String borough, String street) {
		final Query<PricePaid> byStreet = datastore.createQuery(PricePaid.class).
				filter(BOROUGH, borough).
				filter(STREET, street).
                order(DATE_DESCENDING);
		
		return byStreet.asList();
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

	public List<String> getCounties() {		
		DBCollection collection = datastore.getCollection(PricePaid.class);
		return (List<String>) collection.distinct(COUNTY);
	}
	
	public List<String> getBoroughs(String county) {		
		final BasicDBObject dbObject=new BasicDBObject().append(COUNTY, county);
		return (List<String>) datastore.getCollection(PricePaid.class).distinct(BOROUGH, dbObject);
	}
	
	public List<String> getLocalities(String district) {
		final BasicDBObject dbObject=new BasicDBObject().append(DISTRICT, district);
		return (List<String>) datastore.getCollection(PricePaid.class).distinct(LOCALITY, dbObject);
	}
	
	public List<String> getStreets(String borough) {
		final BasicDBObject dbObject=new BasicDBObject().append(BOROUGH, borough);
		List<String> distinct = datastore.getCollection(PricePaid.class).distinct(STREET, dbObject);
		Collections.sort(distinct);
		return distinct;
	}
	
}
