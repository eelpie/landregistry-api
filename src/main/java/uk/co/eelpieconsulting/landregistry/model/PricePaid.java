package uk.co.eelpieconsulting.landregistry.model;

import java.util.Arrays;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import uk.co.eelpieconsulting.common.geo.model.LatLong;
import uk.co.eelpieconsulting.common.views.rss.RssFeedable;
import uk.co.eelpieconsulting.landregistry.views.DateOnlySerializer;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.utils.IndexDirection;

@Entity("pricepaid")
public class PricePaid implements RssFeedable {

	@Id
	private String id;
	
	@Indexed
	private String property;
	
	private int price;
	
    @Indexed
	@JsonSerialize(using = DateOnlySerializer.class)
	private Date date;
	
    @Indexed
	private String postcode;
    @Indexed
	private PropertyType type;
    @Indexed
	private boolean newBuild;
	private Duration duration;
    @Indexed
	private String POAN;
    @Indexed
	private String SOAN;
    @Indexed
	private String street;
    @Indexed
    private String locality;
    @Indexed
	private String district;
    @Indexed
	private String borough;
    @Indexed
	private String county;
	private LatLong latLong;
	
	@Indexed(IndexDirection.GEO2D)
    private double[] location;
	
	public PricePaid() {
	}
	
	public PricePaid(String id, String property, int price, Date date, String postcode,
			PropertyType type, boolean newBuild, Duration duration,
			String POAN, String SOAN, String street, String locality,
			String district, String borough, String county, LatLong latLong) {
		this.id = id;
		this.property = property;
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
		this.latLong = latLong;
		
		if (latLong != null) {
		  this.location = new double[2];
          this.location[0] = latLong.getLatitude();
          this.location[1] = latLong.getLongitude();
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
	
	@JsonIgnore
	@Override
	public String getDescription() {
		return SOAN + " " + POAN + " " + street + " " + locality + " " + district + " " + borough + " " + county + " " + postcode;
	}

	@JsonIgnore
	@Override
	public String getHeadline() {
		return SOAN + " " + POAN + " " + street + " " + locality + " " + district + " - " + price;
	}

	@JsonIgnore
	@Override
	public String getWebUrl() {
		return "http://localhost:8080/landregistry-api-1.0/pricepaid/" + id;
	}
	
	@JsonIgnore
	@Override
	public String getImageUrl() {
		return null;
	}
	
	public LatLong getLatLong() {
		return latLong;
	}
	
	@Override
	public String getAuthor() {
		return null;
	}

	public String getProperty() {
		return property;
	}

	@Override
	public String toString() {
		return "PricePaid [POAN=" + POAN + ", SOAN=" + SOAN + ", borough="
				+ borough + ", county=" + county + ", date=" + date
				+ ", district=" + district + ", duration=" + duration + ", id="
				+ id + ", latLong=" + latLong + ", locality=" + locality
				+ ", location=" + Arrays.toString(location) + ", newBuild="
				+ newBuild + ", postcode=" + postcode + ", price=" + price
				+ ", property=" + property + ", street=" + street + ", type="
				+ type + "]";
	}
	
}
