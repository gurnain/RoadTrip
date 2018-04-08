package roadTrip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * This class implements the Depth First Search algorithm (Referenced from the Lab Walk-through 10 
 * and algs4.cs.princeton.edu)
 * @author Gurnaindeep Saini
 *
 */
public class DFS {
	private Map<String, Boolean> marked;
	private Map<String, String> edgeTo;
	private final String s;
	private Map<String, Boolean> onPath;
	private Stack<String> path;
	private ArrayList<Stack<String>> possiblePaths;
	private int numberOfPaths;
	private int row;
	
	/**
	 * This constructs a DFS object with the given Graph G and String s
	 * @param G the directed unweighted graph
	 * @param s the starting node or city
	 */
	public DFS(Graph G, String s) {
		row = 0;
		marked = new HashMap<String, Boolean>();
		edgeTo = new HashMap<String, String>();
		onPath = new HashMap<String, Boolean>();
		path = new Stack<String>();
		possiblePaths = new ArrayList<Stack<String>>();
		this.s = s;
		dfs(G,s);
	}
	
	/**
	 * This is a helper method that conducts the depth first search on the given graph
	 * @param G the graph on which DFS is conducted
	 * @param v the starting node or city
	 */
	private void dfs(Graph G, String v) {
		marked.putIfAbsent(v, true);
		for(String w : G.getNeighbors(v)) {
			//String neighborCity = w.other(v);
			if (!marked.containsKey(w)) {
				edgeTo.putIfAbsent(w, v);
				dfs(G,w);
			}
		}
	}
	
	/**
	 * This method is used to find all the possible routes between two cities in the graph
	 * @param G the graph
	 * @param v the starting city or node
	 * @param t the ending city or node
	 */
	public void dfsAllPaths(Graph G, String v, String t) {
		path.push(v);
		onPath.put(v, true);
		
		if (v.equals(t)) {
			processCurrentPath();
			numberOfPaths++;
		} else {
			for(String w : G.getNeighbors(v)) {
				//String neighborCity = w.other(v);
				if (!onPath.containsKey(w)) {
					dfsAllPaths(G,w,t);
				}
			}
		}
		path.pop();
		onPath.remove(v);
	}
	
	/**
	 * This is a helper method used by dfsAllPaths to output the found paths between two nodes
	 */
	private void processCurrentPath() {
		possiblePaths.add(new Stack<String>());
		Stack<String> copy = new Stack<String>();
		Stack<String> reverse = new Stack<String>();
		for (String v : path)
			copy.push(v);
		while(!copy.isEmpty()) {
			String x = copy.pop();
			reverse.push(x);
			possiblePaths.get(row).add(x);
		}
		row++;
		
		if (reverse.size() >= 1)
			System.out.print(reverse.pop());
		while(!reverse.isEmpty())
			System.out.print("-"+reverse.pop());
		System.out.println();
	}
	
	/**
	 * Returns an Iterable for the path at index i
	 * @param i the index at which the requested path is present in the ArrayList
	 * @return an Iterable for the path at index i
	 */
	public Iterable<String> pathAt(int i) {
		return possiblePaths.get(i);
	}
	
	/**
	 * This returns the total number of possible paths between the two cities
	 * @return the total number of possible paths between the two cities
	 */
	public int numberOfPaths() {
		return this.numberOfPaths;
	}
	
	/**
	 * This methods tells if there is a path from the starting city to the city v
	 * @param v the node or city to which a path may or may not exist
	 * @return true if such path exists, and false otherwise
	 */
	public boolean hasPathTo(String v) {
		return marked.get(v);
	}
	
	/**
	 * This method returns an Iterable of Strings representing a path from the starting city to city v.
	 * Returns null if no such path exists
	 * @param v the city to which a path may exist
	 * @return an Iterable of Strings representing a path from the starting city to city v.
	 */
	public Iterable<String> pathTo(String v) {
		if (!hasPathTo(v))
			return null;
		
		Stack<String> path = new Stack<String>();
		for (String x = v; x != s; x = edgeTo.get(x)) {
			path.push(x);
		}
		
		path.push(s);
		return path;
	}
}
