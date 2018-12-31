/**
 * Class that represents a node of a graph
 * 
 * @author stephenkim
 *
 */
public class Node {

	// Initializing the instance variables
	private int name;
	private boolean mark;

	/**
	 * Constructor method that creates a node with a given name
	 * 
	 * @param name
	 *            - name of the node representing the number of vertices to
	 *            which the node belongs
	 */
	public Node(int name) {
		this.name = name;
	}

	/**
	 * Method that marks the node with the specified value
	 * 
	 * @param mark
	 *            - mark with which we set the node
	 */
	public void setMark(boolean mark) {
		this.mark = mark;
	}

	/**
	 * Method that returns the mark of the node
	 * 
	 * @return mark of the node
	 */
	public boolean getMark() {
		return mark;
	}

	/**
	 * Method that returns the name of the node
	 * 
	 * @return name of the node
	 */
	public int getName() {
		return name;
	}

}
