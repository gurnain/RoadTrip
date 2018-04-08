package roadTrip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
/**
 * This class implements the Breadth First Search algorithm (Referenced from the Lab Walk-through 10 
 * and algs4.cs.princeton.edu)
 * @author Gurnaindeep Saini
 *
 */
public class BFS {
	private Map<String, Boolean> marked;
	private Map<String, String> edgeTo;
	private Map<String, LinkedList<String>> predecessor;
	private Map<String, Boolean> onPath;
	private Stack<String> allPath;
	private List<Stack<String>> possiblePaths;
	private int numberOfPaths;
	private int row;
	private final String s;
	
	/**
	 * This constructs a BFS object with the given Graph G and String s
	 * @param G the directed unweighted graph
	 * @param s the starting node or city
	 */
	public BFS(Graph G, String s) {
		row = 0;
		marked = new HashMap<String, Boolean>();
		edgeTo = new HashMap<String, String>();
		onPath = new HashMap<String, Boolean>();
		predecessor = new HashMap<String, LinkedList<String>>();
		possiblePaths = new ArrayList<Stack<String>>();
		allPath = new Stack<String>();
		this.s = s;
		bfs(G,s);
	}
	
	/**
	 * This is a helper method that conducts the breadth first search on the given graph
	 * @param G the graph on which BFS is conducted
	 * @param s the starting node or city
	 */
	private void bfs(Graph G, String s) {
		Queue<String> queue = new LinkedList<String>();
		marked.putIfAbsent(s, true);
		queue.add(s);
		while(!queue.isEmpty()) {
			String v = queue.remove();
			for (String w : G.getNeighbors(v)) {
				//String neighborCity = w.other(v);
				if (!marked.containsKey(w)) {
					edgeTo.putIfAbsent(w, v);
					predecessor.putIfAbsent(w, new LinkedList<String>());
					predecessor.get(w).add(v);
					marked.putIfAbsent(w, true);
					queue.add(w);
				} else if (marked.containsKey(w)) {
					predecessor.get(w).add(v);
				}
			}
		}
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
	 * This method is used to find all the possible routes between two cities in the graph
	 * @param v the starting city or node
	 * @param t the ending city or node
	 */
	public void bfsAllPaths(String v, String t) {
		allPath.push(t);
		onPath.put(t, true);
		
		if (t.equals(v)) {
			processCurrentPath();
			numberOfPaths++;
		} else {
			for(String w : predecessor.get(t)) {
				if (!onPath.containsKey(w)) {
					bfsAllPaths(v,w);
				}
			}
		}
		allPath.pop();
		onPath.remove(t);
	}
	
	/**
	 * This returns the total number of possible paths between the two cities
	 * @return the total number of possible paths between the two cities
	 */
	public int numberOfPaths() {
		return this.numberOfPaths;
	}
	
	/**
	 * This is a helper method used by bfsAllPaths to output the found paths between two nodes
	 */
	private void processCurrentPath() {
		possiblePaths.add(new Stack<String>());
		Stack<String> copy = new Stack<String>();
		for (String v : allPath) {
			copy.push(v);
			possiblePaths.get(row).add(v);
		}
		row++;
		
		if (copy.size() >= 1)
			System.out.print(copy.pop());
		while(!copy.isEmpty())
			System.out.print("-"+copy.pop());
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
