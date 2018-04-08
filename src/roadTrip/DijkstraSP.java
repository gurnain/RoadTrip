package roadTrip;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * This class implements the Dijkstra's Shortest Path Algorithm (Referenced from the Algorithms Course Website)
 * algs4.cs.princeton.edu
 * @author Gurnaindeep Saini
 *
 */
public class DijkstraSP {
	private Map<String, Edge> edgeTo;
	private Map<String, Double> distTo;
	private PriorityQueue<String> pq;
	
	/**
	 * This constructs a DijkstraSP Object with the given Graph G and String s parameters
	 * @param G the the directed weighted graph
	 * @param s the starting node or city
	 */
	public DijkstraSP(Graph G, String s) {
		distTo = new HashMap<String, Double>();
		edgeTo = new HashMap<String, Edge>();
		distTo.putIfAbsent(s, 0.0);

		Iterator<String> iterator = G.getWeightedIter();
		while (iterator.hasNext()) {
			distTo.putIfAbsent(iterator.next().toString(), Double.POSITIVE_INFINITY);
		}
		pq = new PriorityQueue<String>();
		pq.add(s);
		while (!pq.isEmpty()) {
			String v = pq.poll();
			for (Edge e : G.getNeighborsWeighted(v)) {
				relax(e, v);
			}
		}
	}
	
	/**
	 * This is a helper method used by the algorithm to find the shortest paths by find edges with smallest
	 * weight
	 * @param e the edge to be relaxed
	 * @param v the starting node
	 */
	private void relax(Edge e, String v) {
		String w = e.other(v);
		if (distTo.get(w) > distTo.get(v) + e.weight()) {
			distTo.put(w, distTo.get(v) + e.weight());
			edgeTo.put(w, e);
			if (pq.contains(w)) pq.remove(w);
			else
				pq.add(w);
		}
	}
	
	/**
	 * This methods tells if there is a path from the starting city to the city v
	 * @param v the node or city to which a path may or may not exist
	 * @return true if such path exists, and false otherwise
	 */
	public boolean hasPathTo(String v) {
		return distTo.get(v) < Double.POSITIVE_INFINITY;
	}
	
	/**
	 * This method returns an Iterable of Edge objects representing a path from the starting city to city v.
	 * Returns null if no such path exists
	 * @param v the city to which a path may exist
	 * @return an Iterable of Edge objects representing a path from the starting city to city v.
	 */
	public Iterable<Edge> pathTo(String v){
		if (!hasPathTo(v)) return null;
		Stack<Edge> path = new Stack<Edge>();
		String x = v;
		for (Edge e = edgeTo.get(v); e != null; e = edgeTo.get(x)) {
			path.push(e);
			x = e.other(x);
		}
		return path;
	}
	
}
