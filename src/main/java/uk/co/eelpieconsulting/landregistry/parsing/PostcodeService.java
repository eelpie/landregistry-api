package uk.co.eelpieconsulting.landregistry.parsing;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import uk.co.eelpieconsulting.common.geo.LatLong;
import uk.co.eelpieconsulting.common.http.HttpFetchException;
import uk.co.eelpieconsulting.common.http.HttpFetcher;

@Component
public class PostcodeService {

	private HttpFetcher httpFetcher;

	public PostcodeService() {
		httpFetcher = new HttpFetcher();
	}
	
	public LatLong getLatLongFor(String postcode) {
		final String id = postcode.replaceAll(" ", "");
		try {
			final String json = httpFetcher.fetchContent("http://localhost:8080/postcodes-api-1.0/postcode/" + id);			
			final JSONObject jsonObject = new JSONObject(json);
			return new LatLong(jsonObject.getDouble("latitude"), jsonObject.getDouble("longitude"));
			
		} catch (HttpFetchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}

}
