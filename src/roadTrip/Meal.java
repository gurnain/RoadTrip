package roadTrip;

/**
 * This class implements a Meal ADT
 * @author Gurnaindeep Saini
 *
 */
public class Meal {
	private String mealName;
	private double price;
	
	/**
	 * This constructs a Meal with given the given name and price parameters
	 * @param name name of the meal
	 * @param price price of the meal
	 */
	public Meal(String name, double price) {
		String[] meal = name.split(" - ");
		this.mealName = meal[0];
		this.price = price;
	}
	
	/**
	 * This returns the name of the meal
	 * @return the name of the meal
	 */
	public String getName() {
		return this.mealName;
	}
	
	/**
	 * This returns the price of the meal
	 * @return the price of the meal
	 */
	public double getPrice() {
		return this.price;
	}
	
	/**
	 * Returns a string representation of the Meal object.
	 * @return a string representation of the Meal object
	 */
	public String toString() {
		return String.format("%-25s%-20s", this.mealName,"$"+this.price);
	}
}