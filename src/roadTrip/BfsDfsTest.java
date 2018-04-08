/**
 * 
 */
package roadTrip;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the BFS and DFS for correctness
 * @author Gurnaindeep Saini
 *
 */
public class BfsDfsTest {
	Graph g;
	DFS dfs;
	BFS bfs;

	/**
	 * This initializes a Graph object by reading the data from the connectedCities text file. Furthermore, it
	 * also initializes the dfs and bfs objects.
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		g = new Graph();
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("data/connectedCities.txt")));
			String[] tokens;
			String line;
			
			while((line = bufferedReader.readLine()) != null) {
				tokens = line.split(", ");
				g.insertCityNode(tokens[0]);
				g.insertCityNode(tokens[1]);
				g.addNeighborCity(tokens[0], tokens[1]);
			}
			bufferedReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		dfs = new DFS(g,"Boston");
		bfs = new BFS(g,"Boston");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * This method tests the BFS for correctness by making sure that all the routes contain only
	 * cities that are connected and in the correct order. As for the number of possible routes between Boston
	 * Minneapolis, the entire graph was hand drawn and after multiple examinations it was found that there
	 * are a total of 21 possible routes. The BFS output for all paths shows a total of 21 possible paths, and
	 * each of these paths were tested by comparing to the actual paths. In conclusion, each route was found
	 * out to be correct and all the possible paths were successfully found by BFS.
	 */
	@Test
	public void testBfs() {
		System.out.println("All possible paths between Boston and Minneapolis using BFS:");
		System.out.println();
		bfs.bfsAllPaths("Boston", "Minneapolis");
		System.out.println("Total number of possible paths: "+bfs.numberOfPaths());
		List<String> path;
		for (int i = 0; i < bfs.numberOfPaths(); i++) {
			path = new ArrayList<String>();
			for (String x : bfs.pathAt(i))
				path.add(0,x);
			for (int j = 0; j < path.size()-1; j++) {
				assertTrue(g.getNeighbors(path.get(j)).contains(path.get(j+1)));
			}
		}
		System.out.println();
	}
	
	/**
	 * This method tests the DFS for correctness by making sure that all the routes contain only
	 * cities that are connected and in the correct order. As for the number of possible routes between Boston
	 * Minneapolis, the entire graph was hand drawn and after multiple examinations it was found that there
	 * are a total of 21 possible routes. The DFS output for all paths shows a total of 21 possible paths, and
	 * each of these paths were tested by comparing to the actual paths. In conclusion, each route was found
	 * out to be correct and all the possible paths were successfully found by DFS.
	 */
	@Test
	public void testDfs() {
		System.out.println("All possible paths between Boston and Minneapolis using DFS:");
		System.out.println();
		dfs.dfsAllPaths(g, "Boston", "Minneapolis");
		System.out.println("Total number of possible paths: "+dfs.numberOfPaths());
		List<String> path;
		for (int i = 0; i < dfs.numberOfPaths(); i++) {
			path = new ArrayList<String>();
			for (String x : dfs.pathAt(i))
				path.add(0,x);
			for (int j = 0; j < path.size()-1; j++) {
				assertTrue(g.getNeighbors(path.get(j)).contains(path.get(j+1)));
			}
		}
	}

}
