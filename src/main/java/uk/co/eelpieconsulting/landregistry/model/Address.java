package uk.co.eelpieconsulting.landregistry.model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

@Entity("address")
public class Address {

	@Id
	private String id;
	
	private String poan;
	private String street;
	private String borough;
	private String county;
	private long listing;
	
	public Address() {
	}
	
	public Address(String id, String poan, String street, String borough, String county, long listing) {
		this.id = id;
		this.poan = poan;
		this.street = street;
		this.borough = borough;
		this.county = county;
		this.listing = listing;
	}
	
	public String getPoan() {
		return poan;
	}
	public void setPoan(String poan) {
		this.poan = poan;
	}

	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}

	public String getBorough() {
		return borough;
	}
	public void setBorough(String borough) {
		this.borough = borough;
	}
	
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	
	public long getListing() {
		return listing;
	}
	public void setListing(long listing) {
		this.listing = listing;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", poan=" + poan + ", street=" + street
				+ ", borough=" + borough + ", county=" + county + ", listing="
				+ listing + "]";
	}
	
	
}