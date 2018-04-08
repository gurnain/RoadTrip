package roadTrip;

/**
 * This class implements an ADT for Graph Edges (Referenced from the textbook Pg. 610)
 * @author Gurnaindeep Saini
 *
 */
public class Edge implements Comparable<Edge> {
	private final String v;
	private final String w;
	private final double weight;
	
	/**
	 * This constructs an Edge with the given Strings and weight parameters
	 * @param v the starting node
	 * @param w the ending node
	 * @param weight the edge weight
	 */
	public Edge(String v, String w, double weight) {
		this.v = v;
		this.w = w;
		this.weight = weight;
	}
	
	/**
	 * Returns the weight of the edge
	 * @return the weight of the edge
	 */
	public double weight() {
		return this.weight;
	}
	
	/**
	 * Returns the starting node of the edge
	 * @return the starting node of the edge
	 */
	public String either() {
		return this.v;
	}
	
	/**
	 * Returns the ending node of the edge
	 * @param vertex node whose adjacent node on the edge is to be returned
	 * @return the ending node of the edge
	 */
	public String other(String vertex) {
		if (vertex.equals(v))
			return this.w;
		else if (vertex.equals(w))
			return this.v;
		else
			return null;
	}
	
	/**
	 * Compares this Edge object with another Edge object.
	 * @return -1 if this Edge's weight is less than that Edge's weight, 1
	 * if it's greater, and 0 if equal.
	 */
	public int compareTo(Edge that) {
		if (this.weight() < that.weight()) return -1;
		else if (this.weight() > that.weight()) return 1;
		else
			return 0;
	}
}
