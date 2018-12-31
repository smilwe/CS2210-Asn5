import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Stack;

/**
 * Class that represents the labyrinth stored in a graph and solved using a
 * graph
 * 
 * @author stephenkim
 *
 */
public class Labyrinth {
	// Initializing the instance variables
	private int width, length, bBombs, aBombs, entrance, exit;
	private Graph labyrinth;
	private Stack<Node> solver;

	/**
	 * Constructor method that builds a labyrinth using the contents of the
	 * input file
	 * 
	 * @param inputFile
	 *            - file to be used to build the labyrinth
	 * @throws LabyrinthException
	 *             if no input file
	 * @throws GraphException
	 *             if nodes don't exist, or edge already exists between nodes
	 */
	public Labyrinth(String inputFile) throws LabyrinthException, GraphException {
		solver = new Stack<Node>();
		int nodeName = 0;
		try {
			/* Reads the input file */
			BufferedReader lab = new BufferedReader(new FileReader(inputFile));
			/* Skips the first line since not used by program */
			lab.readLine();
			width = Integer.parseInt(lab.readLine());
			length = Integer.parseInt(lab.readLine());
			bBombs = Integer.parseInt(lab.readLine());
			aBombs = Integer.parseInt(lab.readLine());
			/* Creates the labyrinth using the width and length */
			labyrinth = new Graph(width * length);
			while (true) {
				/*
				 * Goes through the file line by line and breaks when there are
				 * no more lines to read
				 */
				String inputLine = lab.readLine();
				if (inputLine == null) {
					lab.close();
					break;
				}
				/*
				 * Checks each character in the line and builds the dungeon
				 * using the character values
				 */
				for (int i = 0; i < inputLine.length(); i++) {
					char character = inputLine.charAt(i);
					// Room nodes
					if (character == 'b') {
						entrance = nodeName;
						nodeName++;
					} else if (character == 'x') {
						exit = nodeName;
						nodeName++;
					} else if (character == '+') {
						nodeName++;
						// Horizontal Edges
					} else if (character == 'h') {
						labyrinth.insertEdge(labyrinth.getNode(nodeName - 1), labyrinth.getNode(nodeName), "wall");
					} else if (character == 'H') {
						labyrinth.insertEdge(labyrinth.getNode(nodeName - 1), labyrinth.getNode(nodeName), "thickWall");
					} else if (character == 'm') {
						labyrinth.insertEdge(labyrinth.getNode(nodeName - 1), labyrinth.getNode(nodeName), "metalWall");
					} else if (character == '-') {
						labyrinth.insertEdge(labyrinth.getNode(nodeName - 1), labyrinth.getNode(nodeName), "corridor");
						// Vertical Edges
					} else if (character == 'v') {
						labyrinth.insertEdge(labyrinth.getNode(nodeName - width + ((i + 1) / 2)),
								labyrinth.getNode(nodeName + ((i + 1) / 2)), "wall");
					} else if (character == 'V') {
						labyrinth.insertEdge(labyrinth.getNode(nodeName - width + ((i + 1) / 2)),
								labyrinth.getNode(nodeName + ((i + 1) / 2)), "thickWall");
					} else if (character == 'M') {
						labyrinth.insertEdge(labyrinth.getNode(nodeName - width + ((i + 1) / 2)),
								labyrinth.getNode(nodeName + ((i + 1) / 2)), "metalWall");
					} else if (character == '|') {
						labyrinth.insertEdge(labyrinth.getNode(nodeName - width + ((i + 1) / 2)),
								labyrinth.getNode(nodeName + ((i + 1) / 2)), "corridor");
					}
				}
			}
		} catch (IOException e) {
			throw new LabyrinthException("Cannot build labyrinth, file does not exist!");
		}
	}

	/**
	 * Method that returns a reference to the graph representing the labyrinth
	 * 
	 * @return reference to the graph representing the labyrinth
	 * @throws LabyrinthException
	 *             if the graph is not defined
	 */
	public Graph getGraph() throws LabyrinthException {
		if (labyrinth == null) {
			throw new LabyrinthException("Graph not defined!");
		} else {
			return labyrinth;
		}
	}

	/**
	 * Method that returns an iterator containing the nodes along the path from
	 * the entrance to the exit of the labyrinth
	 * 
	 * @return iterator containing the path from the entrance to the exit
	 * @throws LabyrinthException
	 */
	public Iterator<Node> solve() throws GraphException {
		Node adjNode = null;
		Node prevNode = null;
		Edge edgeToCheck = null;
		int prevInd;
		// Adds the entrance to the stack and marks it
		Node currentNode = labyrinth.getNode(entrance);
		solver.push(currentNode);
		currentNode.setMark(true);
		while (true) {
			// Exits loop if the current node is the exit
			if (currentNode == labyrinth.getNode(exit)) {
				break;
			} else {
				// Creates iterator storing incident edges of the current node
				Iterator<Edge> edges = labyrinth.incidentEdges(currentNode);
				while (edges.hasNext()) {
					edgeToCheck = edges.next();
					// Checks whether the connected node is marked
					if (edgeToCheck.secondEndpoint().getMark() == false) {
						adjNode = edgeToCheck.secondEndpoint();
						// Checks whether the current edge can be passed
						if (passable(edgeToCheck)) {
							solver.push(adjNode);
							adjNode.setMark(true);
							currentNode = adjNode;
							break;
							// If can't be passed and no more edges to check
						} else if (!passable(edgeToCheck) && !edges.hasNext()) {
							prevInd = solver.size() - 2;
							prevNode = solver.get(prevInd);
							restoreBombs(currentNode, prevNode, labyrinth);
							currentNode = prevNode;
							// Removes the node from the stack
							solver.pop();
							break;
						}
						// If no more edges to check and the connected node is
						// marked
					} else if (!edges.hasNext()) {
						prevInd = solver.size() - 2;
						prevNode = solver.get(prevInd);
						restoreBombs(currentNode, prevNode, labyrinth);
						currentNode = prevNode;
						// Removes the node from the stack
						solver.pop();
						break;
					}
				}
			}
		}
		// If stack is empty or only the entrance remains, no path to exit
		if (solver.isEmpty() || solver.size() == 1) {
			return null;
			// If not empty, returns path to exit
		} else {
			return solver.iterator();
		}
	}

	/**
	 * Helper method that restores bombs as needed when retracing through the
	 * dungeon
	 * 
	 * @param curr
	 *            - current node
	 * @param prev
	 *            - previous node
	 * @param graph
	 *            - labyrinth
	 * @throws GraphException
	 *             if no edge exists between the two nodes or either node
	 *             doesn't exist
	 */
	private void restoreBombs(Node curr, Node prev, Graph graph) throws GraphException {
		if (graph.getEdge(curr, prev).getType() == "wall") {
			bBombs++;
		} else if (graph.getEdge(curr, prev).getType() == "thickWall") {
			bBombs = bBombs + 2;
		} else if (graph.getEdge(curr, prev).getType() == "metalWall") {
			aBombs++;
		}
	}

	/**
	 * Helper method that checks whether an edge can be passed with the given
	 * amount of bombs
	 * 
	 * @param edge
	 *            - edge to check if can be passed
	 * @return true if edge can be passed, false otherwise
	 */
	private boolean passable(Edge edge) {
		if (edge.getType() == "corridor") {
			return true;
		} else if (edge.getType() == "wall" && bBombs != 0) {
			bBombs--;
			return true;
		} else if (edge.getType() == "thickWall" && bBombs >= 2) {
			bBombs = bBombs - 2;
			return true;
		} else if (edge.getType() == "metalWall" && aBombs != 0) {
			aBombs--;
			return true;
		} else {
			return false;
		}
	}
}
