package uk.co.eelpieconsulting.landregistry.model;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import uk.co.eelpieconsulting.landregistry.views.DateOnlySerializer;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.utils.IndexDirection;

@Entity("pricepaid")
public class PricePaid {

	@Id
	private String id;
	
	private int price;
	
	@JsonSerialize(using = DateOnlySerializer.class)
	private Date date;
	
	private String postcode;
	private PropertyType type;
	private boolean newBuild;
	private Duration duration;
	private String POAN;
	private String SOAN;
	private String street;
	private String locality;
	private String district;
	private String borough;
	private String county;
	
	@Indexed(IndexDirection.GEO2D)
    private double[] location;

	private Double latitude;
	private Double longitude;
	
	public PricePaid() {
	}
	
	public PricePaid(String id, int price, Date date, String postcode,
			PropertyType type, boolean newBuild, Duration duration,
			String POAN, String SOAN, String street, String locality,
			String district, String borough, String county, Double latitude, Double longitude) {
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
		this.latitude = latitude;
		this.longitude = longitude;
		
		if (latitude != null && longitude != null) {
		  this.location = new double[2];
          this.location[0] = latitude;
          this.location[1] = longitude;
		}
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
	
	public Double getLatitude() {
		return latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	@Override
	public String toString() {
		return "PricePaid [id=" + id + ", price=" + price + ", date=" + date
				+ ", postcode=" + postcode + ", type=" + type + ", newBuild="
				+ newBuild + ", duration=" + duration + ", POAN=" + POAN
				+ ", SOAN=" + SOAN + ", street=" + street + ", locality="
				+ locality + ", district=" + district + ", borough=" + borough
				+ ", county=" + county + ", latitude=" + latitude
				+ ", longitude=" + longitude + "]";
	}
	
}
