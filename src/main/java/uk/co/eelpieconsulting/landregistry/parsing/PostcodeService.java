package uk.co.eelpieconsulting.landregistry.parsing;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import uk.co.eelpieconsulting.common.geo.model.LatLong;
import uk.co.eelpieconsulting.common.http.HttpFetchException;
import uk.co.eelpieconsulting.common.http.HttpFetcher;
import uk.co.eelpieconsulting.common.http.HttpNotFoundException;

@Component
public class PostcodeService {
	
	private final static Logger log = Logger.getLogger(PostcodeService.class);
	
	private HttpFetcher httpFetcher;
	
	@Value("${postcodeApiUrl}")
	private String apiUrl;
	
	public PostcodeService() {
		httpFetcher = new HttpFetcher();
	}
	
	public LatLong getLatLongFor(String postcode) {
		final String id = postcode.replaceAll(" ", "");
		try {
			final String json = httpFetcher.get(apiUrl + "/postcode/" + id);			
			final JSONObject jsonObject = new JSONObject(json);
			return new LatLong(jsonObject.getDouble("latitude"), jsonObject.getDouble("longitude"));
			
		} catch (HttpNotFoundException e) {	
			log.warn("Unknown postcode; ignoring: " + postcode);			
			
		} catch (HttpFetchException e) {
			log.error(e);

		} catch (JSONException e) {
			log.error(e);
		}
		
		return null;
	}

}
