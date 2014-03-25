package uk.co.eelpieconsulting.landregistry.zoopla;

public class PriceChange {

	private String date;
	private int price;
	
	public String getDate() {
		return date;
	}
	public int getPrice() {
		return price;
	}
	@Override
	public String toString() {
		return "PriceChange [date=" + date + ", price=" + price + "]";
	}
	
}
