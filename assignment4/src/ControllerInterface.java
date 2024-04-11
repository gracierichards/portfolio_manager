/**
 * The interface for the controller. Only takes in one input through one method, a method that
 * takes in the user input from the command line, and calls the corresponding part of model or view.
 */
public interface ControllerInterface {

  /**
   * Reads the latest user input and calls the relevant method.
   *
   * @param input a String containing the latest text entered into the command line.
   */
  void processCommand(String input);

}
