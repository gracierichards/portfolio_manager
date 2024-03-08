public interface ControllerInterface {
  /**
   *
   * @param portfolioName
   * @param date in MM/DD/YYYY format
   * @return
   */
  float determineValue(String portfolioName, String date);

  /**
   * Reads the latest user input and calls the relevant method.
   * @param input a String containing the latest text entered into the command line.
   */
  void processCommand(String input);

}
