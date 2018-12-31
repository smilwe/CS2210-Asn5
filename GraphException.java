/**
 * Class that implements the class of exception thrown by the methods of the
 * Graph class
 * 
 * @author stephenkim
 *
 */
public class GraphException extends Exception {
	/**
	 * Sets up this exception with an appropriate message
	 * 
	 * @param message
	 *            - String representing the error encountered
	 */
	public GraphException(String message) {
		super(message);
	}
}
