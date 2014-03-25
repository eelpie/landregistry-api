package uk.co.eelpieconsulting.landregistry.zoopla;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

public class Response {
	
	public Response() {
	}
	
	private Integer result_count;
	private String area_name;	
	@JacksonXmlElementWrapper(useWrapping=false)
	private List<Listing> listing;
	
	public Integer getResult_count() {
		return result_count;
	}
	public void setResult_count(Integer resultCount) {
		result_count = resultCount;
	}

	public String getArea_name() {
		return area_name;
	}
	public void setArea_name(String areaName) {
		area_name = areaName;
	}
	
	public List<Listing> getListing() {
		return listing;
	}
	public void setListing(List<Listing> listing) {
		this.listing = listing;
	}
	
	@Override
	public String toString() {
		return "Response [area_name=" + area_name + ", listing=" + listing
				+ ", result_count=" + result_count + "]";
	}
	
}