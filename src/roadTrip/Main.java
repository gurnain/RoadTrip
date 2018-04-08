package roadTrip;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

/**
 * This class is used to parse data from all the files and test all the other classes.
 * @author Gurnaindeep Saini
 *
 */
public class Main {
	/**
	 * This method is used to parse data from all the files and test all the other classes.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("data/connectedCities.txt")));
			BufferedReader mealsReader = new BufferedReader(new FileReader(new File("data/menu.csv")));
			BufferedReader restReader;
			BufferedReader cityReader = new BufferedReader(new FileReader(new File("data/USCities.csv")));
			Formatter out = new Formatter(new FileOutputStream("a2_out.txt"));
			String[] tokens;
			String line;
			
			
			/*
			 * This is used to create an unweighted graph for the BFS and DFS.
			 */
			Graph g = new Graph();
			while((line = bufferedReader.readLine()) != null) {
				tokens = line.split(", ");
				g.insertCityNode(tokens[0]);
				g.insertCityNode(tokens[1]);
				g.addNeighborCity(tokens[0], tokens[1]);
			}
			
			
			/*
			 * This is used to read all the different meals from the 3 restaurant files
			 */
			Restaurant McDonalds = new Restaurant("McDonald's");
			Restaurant Wendys = new Restaurant("Wendy's");
			Restaurant BurgerKing = new Restaurant("Burger King");
			mealsReader.readLine();
			while((line = mealsReader.readLine()) != null) {
				tokens = line.split(",");
				if (tokens[0].contains("’"))
					tokens[0] = tokens[0].replace("’", "'");
				if (tokens[0].equals("McDonald's"))
					McDonalds.addMeal(new Meal(tokens[1],Double.parseDouble(tokens[2].substring(1))));
				else if (tokens[0].equals("Burger King"))
					BurgerKing.addMeal(new Meal(tokens[1],Double.parseDouble(tokens[2].substring(1))));
				else if (tokens[0].equals("Wendy's"))
					Wendys.addMeal(new Meal(tokens[1],Double.parseDouble(tokens[2].substring(1))));
			}
			
			
			/*
			 * This reads the city data (Name, Longitude and Latitude) from the USCities.csv
			 */
			List<City> cities = new ArrayList<City>();
			cityReader.readLine();
			while((line = cityReader.readLine()) != null) {
				tokens = line.split(",");
				cities.add(new City(tokens[3],Double.parseDouble(tokens[5]),Double.parseDouble(tokens[4])));
			}
			
			
			/*
			 * This is used to read the mcdonalds.csv file and add the restaurant into the cities if
			 * it satisfies the 0.5 degrees constraint
			 */
			for (City c : cities) {
				double Longitude = c.cityLongitude();
				double Latitude = c.cityLatitude();
				restReader = new BufferedReader(new FileReader(new File("data/mcdonalds.csv")));
				while((line = restReader.readLine()) != null) {
					tokens = line.split(",");
					if (Math.abs(Double.parseDouble(tokens[0]) - Longitude) <= 0.5 && Math.abs(Double.parseDouble(tokens[1]) - Latitude) <= 0.5) {
						c.addRest(McDonalds);
						break;
					}
				}
			}
			
			
			/*
			 * This is used to read the wendys.csv file and add the restaurant into the cities if
			 * it satisfies the 0.5 degrees constraint
			 */
			for (City c : cities) {
				double Longitude = c.cityLongitude();
				double Latitude = c.cityLatitude();
				restReader = new BufferedReader(new FileReader(new File("data/wendys.csv")));
				while((line = restReader.readLine()) != null) {
					tokens = line.split(",");
					if (Math.abs(Double.parseDouble(tokens[0]) - Longitude) <= 0.5 && Math.abs(Double.parseDouble(tokens[1]) - Latitude) <= 0.5) {
						c.addRest(Wendys);
						break;
					}
				}
			}
			
			
			/*
			 * This is used to read the burgerking.csv file and add the restaurant into the cities if
			 * it satisfies the 0.5 degrees constraint
			 */
			for (City c : cities) {
				double Longitude = c.cityLongitude();
				double Latitude = c.cityLatitude();
				restReader = new BufferedReader(new FileReader(new File("data/burgerking.csv")));
				while((line = restReader.readLine()) != null) {
					tokens = line.split(",");
					if (Math.abs(Double.parseDouble(tokens[0]) - Longitude) <= 0.5 && Math.abs(Double.parseDouble(tokens[1]) - Latitude) <= 0.5) {
						c.addRest(BurgerKing);
						break;
					}
				}
			}
			
			
			/*
			 * This is used to create an Weighted graph for the Dijkstra's Shortest Path Algorithm
			 */
			bufferedReader = new BufferedReader(new FileReader(new File("data/connectedCities.txt")));
			while((line = bufferedReader.readLine()) != null) {
				tokens = line.split(", ");
				double edgeWeight = g.getWeight(tokens[0], tokens[1], g, cities);
				g.addEdgeWeighted(tokens[0], new Edge(tokens[0],tokens[1],edgeWeight));
			}

			
			/*
			 * This section is used to output the final result for DFS, BFS, Shortest Path Table onto
			 * the console and into the a2_out.txt file.
			 */
			String dfsPath = "";
			String bfsPath = "";
			DFS dfs = new DFS(g,"Boston");
			List<String> path = new ArrayList<String>();
			for (String x : dfs.pathTo("Minneapolis")) {
				path.add(0, x);
			}
			System.out.print("DFS: ");
			dfsPath += "DFS: ";
			for (int i = 0; i < path.size()-1; i++) {
				System.out.print(path.get(i)+", ");
				dfsPath += path.get(i)+", ";
			}
			System.out.print(path.get(path.size()-1));
			dfsPath += path.get(path.size()-1);
			
			System.out.println();
			
			BFS bfs = new BFS(g,"Boston");
			List<String> path2 = new ArrayList<String>();
			for (String x : bfs.pathTo("Minneapolis")) {
				path2.add(0,x);
			}
			
			System.out.print("BFS: ");
			bfsPath += "BFS: ";
			for (int i = 0; i < path2.size()-1; i++) {
				System.out.print(path2.get(i)+", ");
				bfsPath += path2.get(i)+", ";
			}
			System.out.print(path2.get(path2.size()-1));
			bfsPath += path2.get(path2.size()-1);
			
			out.format(bfsPath+"\n");
			out.format(dfsPath+"\n");
			
			System.out.println();
			DijkstraSP shortest = new DijkstraSP(g,"Boston");
			List<String> path3 = new ArrayList<String>();
			for (Edge e : shortest.pathTo("Minneapolis")) {
				path3.add(0,e.other(e.either()));
				if (e.either().equals("Boston"))
					path3.add(0,e.either());
			}
			
			System.out.print("Shortest Path using Dijkstra's Algorithm: ");
			for (int i = 0; i<path3.size()-1; i++) {
				System.out.print(path3.get(i)+", ");
			}
			System.out.println(path3.get(path3.size()-1));
			
			System.out.println();
			System.out.println("3.4 Shortest Path Table");
			out.format("\n3.4 Shortest Path Table\n");
			System.out.format("%-20s%-25s%-20s","City","Meal Choice","Cost of Meal");
			out.format("%-20s%-25s%-20s\n","City","Meal Choice","Cost of Meal");
			System.out.println();
			System.out.format("%-20s",path3.get(0));
			out.format("%-20s\n",path3.get(0));
			System.out.println();
			for (int i = 1; i < path3.size(); i++) {
				System.out.format("%-20s%-20s",path3.get(i),g.getChoice(path3.get(i)));
				out.format("%-20s%-20s\n",path3.get(i),g.getChoice(path3.get(i)));
				System.out.println();
			}
			
			out.close();
			bufferedReader.close();
			cityReader.close();
			mealsReader.close();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
