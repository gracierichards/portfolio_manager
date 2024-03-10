public interface ControllerInterface {


  /**
   * Reads the latest user input and calls the relevant method.
   * @param input a String containing the latest text entered into the command line.
   */
  void processCommand(String input);

}
