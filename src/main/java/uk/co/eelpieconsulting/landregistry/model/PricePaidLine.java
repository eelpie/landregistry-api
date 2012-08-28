package uk.co.eelpieconsulting.landregistry.model;

import java.util.Date;

public class PricePaidLine extends PricePaid {

	private final RecordStatus recordStatus;
	
	public PricePaidLine(String id, int price, Date date, String postcode,
			PropertyType type, boolean newBuild, Duration duration,
			String POAN, String SOAN, String street, String locality,
			String district, String borough, String county, RecordStatus recordStatus) {
		super(id, price, date, postcode, type, newBuild, duration, POAN, SOAN, street,
				locality, district, borough, county, null, null);
		this.recordStatus = recordStatus;
	}

	public RecordStatus getRecordStatus() {
		return recordStatus;
	}
	
}
