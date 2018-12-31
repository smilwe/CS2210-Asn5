/**
 * Class that implements the class of exception thrown by the Labyrinth and
 * getGraph methods
 * 
 * @author stephenkim
 *
 */
public class LabyrinthException extends Exception {
	/**
	 * Sets up this exception with the appropriate message
	 * 
	 * @param message
	 *            - String representing the error encountered
	 */
	public LabyrinthException(String message) {
		super(message);
	}
}
