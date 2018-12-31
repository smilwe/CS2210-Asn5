/**
 * Class that represents an edge of the graph
 * 
 * @author stephenkim
 *
 */
public class Edge {

	// Initializing instance variables
	private Node u;
	private Node v;
	private String type;

	/**
	 * Constructor method that creates an edge of the graph
	 * 
	 * @param u
	 *            - one endpoint of the edge
	 * @param v
	 *            - other endpoint of the edge
	 * @param type
	 *            - type of edge
	 */
	public Edge(Node u, Node v, String type) {
		this.u = u;
		this.v = v;
		this.type = type;
	}

	/**
	 * Method that returns the first endpoint of the edge
	 * 
	 * @return first endpoint of the edge
	 */
	public Node firstEndpoint() {
		return u;
	}

	/**
	 * Method that returns the second endpoint of the edge
	 * 
	 * @return second endpoint of the edge
	 */
	public Node secondEndpoint() {
		return v;
	}

	/**
	 * Method that returns the type of edge
	 * 
	 * @return type of edge
	 */
	public String getType() {
		return type;
	}

	/**
	 * Method that sets the type of edge to the specified value
	 * 
	 * @param type
	 *            - type to set the edge to
	 */
	public void setType(String type) {
		this.type = type;
	}
}
