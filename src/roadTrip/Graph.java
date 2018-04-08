package roadTrip;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
/**
 * This class implements the Directed Graph ADT (Referenced from the textbook and Lab Walk-through)
 * @author Gurnaindeep Saini
 *
 */
public class Graph {
	private Map<String, LinkedList<String>> adjUnweighted;
	private Map<String, LinkedList<Edge>> adjWeighted;
	private Map<String, String> mealTaken;
	private Map<String, Meal> choices;
	
	/**
	 * This constructs a Graph object
	 */
	public Graph() {
		adjUnweighted = new HashMap<String, LinkedList<String>>();
		adjWeighted = new HashMap<String, LinkedList<Edge>>();
		mealTaken = new HashMap<String, String>();
		choices = new HashMap<String, Meal>();
	}
	
	
	/**
	 * This is used to insert nodes into the two unweighted and weighted directed graphs
	 * @param n the String node to be inserted
	 */
	public void insertCityNode(String node) {
		adjUnweighted.putIfAbsent(node, new LinkedList<String>());
		adjWeighted.putIfAbsent(node, new LinkedList<Edge>());
	}
	
	
	/**
	 * This is used to insert the city name and previous meal name into the HashMap mealTaken
	 * @param city the city at which the given meal was taken
	 * @param meal the meal that was taken at that city
	 */
	public void insertMealTaken(String city, String meal) {
		mealTaken.putIfAbsent(city, meal);
	}
	
	
	/**
	 * This is used to insert the city name and the final meal taken (including name and price) into
	 * the HashMap choices
	 * @param city the city at which the given meal was taken
	 * @param m the meal that was taken at that city
	 */
	public void insertChoice(String city, Meal m) {
		choices.putIfAbsent(city, m);
	}
	
	
	/**
	 * This adds a neighbor city to a particular city in the adjacency list 
	 * @param city1 the first city or the starting city
	 * @param city2 the second city or the ending city
	 */
	public void addNeighborCity(String city1, String city2) {
		adjUnweighted.get(city1).add(city2);
	}
	
	
	/**
	 * This adds a weighted directed edge from one city to another city in the adjacency list
	 * @param city1 the starting city
	 * @param city2 edge connecting the starting city to the next city
	 */
	public void addEdgeWeighted(String city1, Edge city2) {
		adjWeighted.get(city1).add(city2);
	}
	
	
	/**
	 * This returns all the neighboring cities adjacent to a city as a List of Strings
	 * @param city the city whose adjacent neighboring cities are requested
	 * @return the neighboring cities as a List of Strings
	 */
	public List<String> getNeighbors(String city) {
		return adjUnweighted.get(city);
	}
	
	
	/**
	 * This returns all the neighboring cities adjacent to a city as a List of Edge objects
	 * @param city the city whose adjacent edges are requested
	 * @return the neighboring cities as a List of Edge objects
	 */
	public List<Edge> getNeighborsWeighted(String city) {
		return adjWeighted.get(city);
	}
	
	
	/**
	 * This returns the name of the meal that was taken at a particular city
	 * @param city the city at which the meal was taken
	 * @return name of the meal taken at that city
	 */
	public String getMealTaken(String city) {
		return mealTaken.get(city);
	}
	
	
	/**
	 * This returns the meal taken at a particular city as a Meal object
	 * @param city the city at which the meal was taken
	 * @return the meal taken at that city as a Meal object
	 */
	public Meal getChoice(String city) {
		return choices.get(city);
	}
	
	
	/**
	 * This returns the interator for the Unweighted graph
	 * @return the interator for the Unweighted graph
	 */
	public Iterator<String> getUnweightedIter(){
		return this.adjUnweighted.keySet().iterator();
	}
	
	
	/**
	 * This returns the interator for the Weighted graph
	 * @return the interator for the Weighted graph
	 */
	public Iterator<String> getWeightedIter(){
		return this.adjWeighted.keySet().iterator();
	}
	
	
	/**
	 * This calculates and returns the edge weight between two cities based on the constraints
	 * @param startCity the first city or the starting city
	 * @param endCity the second city or the ending city
	 * @param g the Graph object
	 * @param cities ArrayList of City objects
	 * @return the edge weight between startCity and endCity as a double value
	 */
	public double getWeight(String startCity, String endCity, Graph g, List<City> cities) {
		double price = Double.POSITIVE_INFINITY;
		Meal cheap = null;
		String lastMeal;
		if (startCity.equalsIgnoreCase("Boston")) {
			City end = null;
			for (City c : cities) {
				if (c.cityName().equalsIgnoreCase(endCity)) {
					end = c;
					break;
				}
			}
			for (Restaurant r : end.getRest()) {
				for (Meal meal : r.getMeal()) {
					if (meal.getPrice() < price) {
						price = meal.getPrice();
						cheap = meal;
					}
				}
			}
			g.insertMealTaken(endCity, cheap.getName());
			g.insertChoice(endCity,new Meal(cheap.getName(),cheap.getPrice()));
			return price;
		} else {
			lastMeal = g.getMealTaken(startCity);
			City end = null;
			for (City c : cities) {
				if (c.cityName().equalsIgnoreCase(endCity)) {
					end = c;
					break;
				}
			}
			for (Restaurant r : end.getRest()) {
				for (Meal meal : r.getMeal()) {
					if (meal.getPrice() < price && meal.getName() != lastMeal) {
						price = meal.getPrice();
						cheap = meal;
					}
				}
			}
			g.insertMealTaken(endCity, cheap.getName());
			g.insertChoice(endCity,new Meal(cheap.getName(),cheap.getPrice()));
			return price;
		}
	}
}
