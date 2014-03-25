package uk.co.eelpieconsulting.landregistry.zoopla;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import uk.co.eelpieconsulting.common.http.HttpBadRequestException;
import uk.co.eelpieconsulting.common.http.HttpFetchException;
import uk.co.eelpieconsulting.common.http.HttpFetcher;
import uk.co.eelpieconsulting.common.http.HttpForbiddenException;
import uk.co.eelpieconsulting.common.http.HttpNotFoundException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.common.collect.Lists;

@Component
public class ZooplaApiClient {

	private static final int MAX_PAGINATION_DEPTH = 10;

	private static final String ZOOPLA_API_BASE_URL = "http://api.zoopla.co.uk/api/v1/";
	
	private final String apiKey;
	
	private final HttpFetcher httpFetcher;
	private final XmlMapper mapper;

	@Autowired
	public ZooplaApiClient(@Value("${zooplaApiKey}") String apiKey) {
		this.apiKey = apiKey;
		this.httpFetcher = new HttpFetcher();
		this.mapper = new XmlMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	public List<Listing> getListingsForArea(String area) throws HttpNotFoundException, HttpBadRequestException, HttpForbiddenException, HttpFetchException, JsonParseException, JsonMappingException, IOException {
		final List<Listing> listings = Lists.newArrayList();
		int page_number = 1;
		
		while (page_number <= MAX_PAGINATION_DEPTH) {
			final String xml = httpFetcher.get(buildPropertyListingsUrl(area, page_number));
			System.out.println(xml);
			Response response = mapper.readValue(xml, Response.class);
			listings.addAll(response.getListing());
			
			final int page_limit = page_number * 100;
			if (response.getResult_count() <= page_limit) {
				return listings;
			}
			page_number++;
		}
		
		return listings;
	}
	
	public Listing getListing() throws HttpNotFoundException, HttpBadRequestException, HttpForbiddenException, HttpFetchException, JsonParseException, JsonMappingException, IOException {
		final String xml = httpFetcher.get(buildListingUrl(31996237));
		System.out.println(xml);
		Response response = mapper.readValue(xml, Response.class);
		if (!response.getListing().isEmpty()) {
			return response.getListing().get(0);
		}
		return null;
	}
	
	private String buildListingUrl(int listing_id) {
		return ZOOPLA_API_BASE_URL + "property_listings.xml?listing_id=" + Integer.toString(listing_id) + "&" +
			"include_sold=1" + "&" +
			"api_key=" + apiKey;
	}
	
	private String buildPropertyListingsUrl(String area, int page_number) throws UnsupportedEncodingException {
		return ZOOPLA_API_BASE_URL + "property_listings.xml?area=" + URLEncoder.encode(area, "UTF8") + "&" +
				"order_by=age&property_type=houses&listing_status=sale&" +
				"include_sold=1" + "&" +
				"page_size=100&page_number=" + page_number + "&" +
				"api_key=" + apiKey;
	}
	
}
