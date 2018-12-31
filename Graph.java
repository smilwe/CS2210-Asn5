import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class representing an undirected graph using an adjacency matrix
 * 
 * @author stephenkim
 *
 */

public class Graph implements GraphADT {
	// Initializing the instance variables
	private int n;
	private Node graph[];
	private Edge edgeHolder[][];

	/**
	 * Constructor method that creates an empty graph with n nodes and 0 edges
	 * 
	 * @param n
	 *            - number of nodes
	 */
	public Graph(int n) {
		this.n = n;
		graph = new Node[n];
		for (int i = 0; i < n; i++) {
			graph[i] = new Node(i);
		}
		edgeHolder = new Edge[n][n];
	}

	/**
	 * Method that creates and edge connecting the two specified nodes
	 * 
	 * @param u
	 *            - first node
	 * @param v
	 *            - second node
	 * @param edgeType
	 *            - type of the newly created edge
	 * @throws GraphException
	 *             if either of the nodes do not exist or there already exists
	 *             an edge between the two nodes
	 */
	public void insertEdge(Node u, Node v, String edgeType) throws GraphException {
		/* Checks if either node doesn't exist */
		if (u.getName() < 0 || u.getName() > n - 1 || v.getName() < 0 || v.getName() > n - 1) {
			throw new GraphException("Node does not exist! Cannot insert edge");
			/* Checks whether an edge already exists between the two nodes */
		} else if (edgeHolder[u.getName()][v.getName()] != null) {
			throw new GraphException("Edge already exists! Cannot insert edge");
			/* Inserts edge */
		} else {
			edgeHolder[u.getName()][v.getName()] = new Edge(u, v, edgeType);
			edgeHolder[v.getName()][u.getName()] = new Edge(v, u, edgeType);
		}
	}

	/**
	 * Method that returns the node with the specified name
	 * 
	 * @param name
	 *            - name of the node to return
	 * @return node with given name
	 * @throws GraphException
	 *             if there is no node with the given name
	 */
	public Node getNode(int name) throws GraphException {
		/* Checks whether the a node with the given name exists */
		if (name < 0 || name > n - 1) {
			throw new GraphException("No node with this name exists!");
		} else {
			return graph[name];
		}
	}

	/**
	 * Method that returns an iterator storing all the edges incident on the
	 * given node
	 * 
	 * @param u
	 *            - node to check for incident edges
	 * @return iterator storing all incident edges on the node
	 * @throws GraphException
	 *             if the node does not exist
	 */
	public Iterator<Edge> incidentEdges(Node u) throws GraphException {
		/* Checks whether the given node exists */
		if (u.getName() < 0 || u.getName() > n - 1) {
			throw new GraphException("Node does not exist, therefore no incident edges!");
		} else {
			/* Creates an array list to store the edges of the given node */
			ArrayList<Edge> incEdges = new ArrayList<>();
			/*
			 * Checks all other possible nodes and adds all incident edges to
			 * the list
			 */
			for (int i = 0; i < n; i++) {
				if (edgeHolder[u.getName()][i] != null) {
					incEdges.add(edgeHolder[u.getName()][i]);
				}
			}
			/* Checks whether the node has any incident edges */
			if (incEdges.isEmpty()) {
				return null;
			} else {
				return incEdges.iterator();
			}
		}
	}

	/**
	 * Method that returns the edge between two nodes
	 * 
	 * @param u
	 *            - first node
	 * @param v
	 *            - second node
	 * @return edge connecting the two nodes
	 * @throws GraphException
	 *             if there is no edge between the two nodes or if either of the
	 *             two nodes does not exist
	 */
	public Edge getEdge(Node u, Node v) throws GraphException {
		/* Checks whether there is an edge between the two nodes */
		if (edgeHolder[u.getName()][v.getName()] == null) {
			throw new GraphException("There is no edge between the two nodes!");
			/* Checks whether both nodes exist */
		} else if (u.getName() < 0 || u.getName() > n - 1 || v.getName() < 0 || v.getName() > n - 1) {
			throw new GraphException("Node does not exist!");
		} else {
			return edgeHolder[u.getName()][v.getName()];
		}
	}

	/**
	 * Method that determines whether the two given nodes are adjacent
	 * 
	 * @param u
	 *            - first node
	 * @param v
	 *            - second node
	 * @return true if nodes are adjacent, false otherwise
	 * @throws GraphException
	 *             if either of the two nodes doesn't exist
	 */
	public boolean areAdjacent(Node u, Node v) throws GraphException {
		/* Checks whether both nodes exist */
		if (u.getName() < 0 || u.getName() > n - 1 || v.getName() < 0 || v.getName() > n - 1) {
			throw new GraphException("Node does not exist!");
		} else {
			if (edgeHolder[u.getName()][v.getName()] != null) {
				return true;
			} else {
				return false;
			}
		}
	}
}
