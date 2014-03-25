package uk.co.eelpieconsulting.landregistry.zoopla;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

@Entity("zooplaListing")
public class Listing {
	
	public Listing() {
	}
	
	@Id
	private String id;
	
	private long listing_id;
	
	private String displayable_address;
	private String listing_status;
	private String description;
	private String details_url;
	private String image_url;
	private String floor_plan;	
	private int price;	
	private String first_published_date;
	private String last_published_date;
	private Double latitude;
	private Double longitude;
	
	@JacksonXmlElementWrapper(useWrapping=false)
	private List<PriceChange> price_change;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public long getListing_id() {
		return listing_id;
	}
	public void setListing_id(long listingId) {
		listing_id = listingId;
	}
	public String getDisplayable_address() {
		return displayable_address;
	}
	public void setDisplayable_address(String displayableAddress) {
		displayable_address = displayableAddress;
	}
	public String getListing_status() {
		return listing_status;
	}
	public void setListing_status(String listingStatus) {
		listing_status = listingStatus;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDetails_url() {
		return details_url;
	}
	public void setDetails_url(String detailsUrl) {
		details_url = detailsUrl;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String imageUrl) {
		image_url = imageUrl;
	}
	public String getFloor_plan() {
		return floor_plan;
	}
	public void setFloor_plan(String floorPlan) {
		floor_plan = floorPlan;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getFirst_published_date() {
		return first_published_date;
	}
	public void setFirst_published_date(String firstPublishedDate) {
		first_published_date = firstPublishedDate;
	}
	public String getLast_published_date() {
		return last_published_date;
	}
	public void setLast_published_date(String lastPublishedDate) {
		last_published_date = lastPublishedDate;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}	
	public List<PriceChange> getPrice_change() {
		return price_change;
	}
	public void setPrice_change(List<PriceChange> priceChange) {
		price_change = priceChange;
	}
	
	@Override
	public String toString() {
		return "Listing [description=" + description + ", details_url="
				+ details_url + ", displayable_address=" + displayable_address
				+ ", first_published_date=" + first_published_date
				+ ", floor_plan=" + floor_plan + ", id=" + id + ", image_url="
				+ image_url + ", last_published_date=" + last_published_date
				+ ", latitude=" + latitude + ", listing_id=" + listing_id
				+ ", listing_status=" + listing_status + ", longitude="
				+ longitude + ", price=" + price + ", price_change="
				+ price_change + "]";
	}
	
}