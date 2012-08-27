package uk.co.eelpieconsulting.landregistry.model;

import java.util.Date;

public class PricePaidLine {

	private final String id;
	private final int price;
	private final Date date;
	private final String postcode;
	private final PropertyType type;
	private final boolean newBuild;
	private final Duration duration;
	private final String POAN;
	private final String SOAN;
	private final String street;
	private final String locality;
	private final String district;
	private final String borough;
	private final String county;
	private final RecordStatus recordStatus;
	
	public PricePaidLine(String id, int price, Date date, String postcode,
			PropertyType type, boolean newBuild, Duration duration,
			String POAN, String SOAN, String street, String locality,
			String district, String borough, String county, RecordStatus recordStatus) {
		this.id = id;
		this.price = price;
		this.date = date;
		this.postcode = postcode;
		this.type = type;
		this.newBuild = newBuild;
		this.duration = duration;
		this.POAN = POAN;
		this.SOAN = SOAN;
		this.street = street;
		this.locality = locality;
		this.district = district;
		this.borough = borough;
		this.county = county;
		this.recordStatus = recordStatus;
	}

	public String getId() {
		return id;
	}

	public int getPrice() {
		return price;
	}

	public Date getDate() {
		return date;
	}

	public String getPostcode() {
		return postcode;
	}

	public PropertyType getType() {
		return type;
	}

	public boolean isNewBuild() {
		return newBuild;
	}

	public Duration getDuration() {
		return duration;
	}

	public String getPOAN() {
		return POAN;
	}

	public String getSOAN() {
		return SOAN;
	}

	public String getStreet() {
		return street;
	}

	public String getLocality() {
		return locality;
	}

	public String getDistrict() {
		return district;
	}
	
	public String getBorough() {
		return borough;
	}

	public String getCounty() {
		return county;
	}

	public RecordStatus getRecordStatus() {
		return recordStatus;
	}

	@Override
	public String toString() {
		return "PricePaidLine [POAN=" + POAN + ", SOAN=" + SOAN + ", county="
				+ county + ", date=" + date + ", district=" + district
				+ ", duration=" + duration + ", id=" + id + ", locality="
				+ locality + ", newBuild=" + newBuild + ", postcode="
				+ postcode + ", price=" + price + ", recordStatus="
				+ recordStatus + ", street=" + street + ", type=" + type + "]";
	}
	
}
