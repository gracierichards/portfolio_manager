/**
 * Maybe the create portfolio command for the user can be in the format:
 * create portfolio MSFT:20, AAPL:10, NVDA:30
 * for a user who wants to make a new portfolio with 20 shares of MSFT, 10 shares of AAPL, and 30
 * shares of NVDA
 * The load portfolio from file command can be:
 * load portfolio <filename>
 * The save portfolio to file command can be:
 * save <portfolio_name> <filename>
 * The command to examine the composition of a portfolio can be:
 * list <portfolio_name>
 * The command to determine the total value of a portfolio on a given date can be:
 * value <portfolio_name> MM/DD/YYYY
 * There can be an optional command for the user to look up ticker symbols that match the name of a
 * company, or all ticker symbols that start with the inputted string. It is in the format:
 * search <company_name>
 * OR
 * search <ticker_symbol>
 */
public class Controller implements ControllerInterface {
  public void processCommand(String input) {

  }
}
