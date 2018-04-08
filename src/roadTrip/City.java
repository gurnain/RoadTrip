package roadTrip;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements a city ADT
 * @author Gurnaindeep Saini
 *
 */
public class City {
	private final String name;
	private final double Longitude;
	private final double Latitude;
	private List<Restaurant> restaurants;
	
	/**
	 * This constructs a city with the given name, Longitude and Latitude parameters
	 * @param name the name of the city
	 * @param Longitude longitude of the city
	 * @param Latitude latitude of the city
	 */
	public City(String name, double Longitude, double Latitude) {
		this.name = name;
		this.Longitude = Longitude;
		this.Latitude = Latitude;
		restaurants = new ArrayList<Restaurant>();
	}
	
	/**
	 * This returns the city name as a String
	 * @return the name of the city
	 */
	public String cityName() {
		return this.name;
	}
	
	/**
	 * This returns the longitude of the city as a double value
	 * @return the longitude of the city
	 */
	public double cityLongitude() {
		return this.Longitude;
	}
	
	/**
	 * This returns the latitude of the city as a double value
	 * @return the latitude of the city
	 */
	public double cityLatitude() {
		return this.Latitude;
	}
	
	/**
	 * This method adds a Restaurant object to the restaurants ArrayList
	 * @param r the restaurant to be added into the restaurants ArrayList
	 */
	public void addRest(Restaurant r) {
		restaurants.add(r);
	}
	
	/**
	 * This returns the restaurants ArrayList
	 * @return the restaurants ArrayList
	 */
	public List<Restaurant> getRest() {
		return restaurants;
	}
}
