package roadTrip;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements a Restaurant ADT
 * @author Gurnaindeep Saini
 *
 */
public class Restaurant {
	private final String name;
	private List<Meal> meals;
	
	/**
	 * This constructs a restaurant with the given name parameter
	 * @param name name of the restaurant
	 */
	public Restaurant(String name) {
		this.name = name;
		meals = new ArrayList<Meal>();
	}
	
	/**
	 * This returns the restaurant's name
	 * @return the restaurant's name
	 */
	public String restName() {
		return this.name;
	}
	
	/**
	 * This adds a meal into the ArrayList of Meal objects
	 * @param m the meal to be added into the meals ArrayList
	 */
	public void addMeal(Meal m) {
		meals.add(m);
	}
	
	/**
	 * Returns the meals ArrayList
	 * @return the meals ArrayList
	 */
	public List<Meal> getMeal() {
		return meals;
	}
}
