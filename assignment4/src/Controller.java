import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * These are the possible commands the user can provide when using this program.
 *
 * create portfolio <portfolio_name> MSFT:20 AAPL:10 NVDA:30
 * for a user who wants to make a new portfolio with 20 shares of MSFT, 10 shares of AAPL, and 30
 * shares of NVDA
 *
 * The load portfolio from file command is:
 * load portfolio <portfolio_name> <filename>
 *
 * The save portfolio to file command is:
 * save <portfolio_name> <filename>
 *
 * The command to examine the composition of a portfolio is:
 * list <portfolio_name>
 *
 * The command to determine the total value of a portfolio on a given date is:
 * value <portfolio_name> MM/DD/YYYY
 *
 * There is an optional command for the user to look up ticker symbols that match the name of a
 * company, or all ticker symbols that start with the inputted string. It is in the format:
 * search <company_name>
 * OR
 * search <ticker_symbol>
 *
 * quit   - to terminate the program.
 */

/**
 * The implementation of Controller. Its processCommand method handles the cases for each of the
 * possible commands this program accepts - see SETUP-README.txt for documentation of commands.
 */
public class Controller implements ControllerInterface {
  private Model model;
  private View view;

  /**
   * Instantiates the Controller.
   * @param m the Model
   * @param v the View
   */
  public Controller(Model m, View v) {
    this.model = m;
    this.view = v;
  }
  public void processCommand(String input) {
    String[] words = input.split(" ");
    switch (words[0]) {
      case "create":
        List<String> tickerSymbols = new ArrayList<>();
        List<Float> stockAmounts = new ArrayList<>();
        String portfolioName;
        int startIndex;
        if (words[1].equals("portfolio")) {
          startIndex = 2;
        } else {
          startIndex = 1;
        }
        if (words[startIndex].contains(":")) {
          System.out.println("Please provide a name for your portfolio. The name cannot contain "
                  + "a colon.");
        } else {
          portfolioName = words[startIndex];
          for (int i = startIndex + 1; i < words.length; i++) {
            if (!words[i].contains(":")) {
              System.out.println("There is something wrong with the syntax of the create "
                      + "portfolio command.");
              break;
            }
            String value = words[i].substring(words[i].indexOf(":") + 1);
            if (isInteger(Float.parseFloat(value))) {
              tickerSymbols.add(words[i].substring(0, words[i].indexOf(":")));
              stockAmounts.add(Float.parseFloat(value));
            } else {
              System.out.println("Cannot purchase a fractional number of shares. Not including "
                      + "stock " + words[i].substring(0, words[i].indexOf(":")) + " in the "
                      + "portfolio.");
            }
          }
          float[] amountsArray = new float[stockAmounts.size()];
          for (int i = 0; i < stockAmounts.size(); i++) {
            amountsArray[i] = stockAmounts.get(i);
          }
          model.createPortfolio(portfolioName, tickerSymbols.toArray(new String[0]),
                  amountsArray);
          System.out.println("Portfolio created successfully.");
        }
        break;
      case "load":
        if (words[1].equals("portfolio")) {
          startIndex = 2;
        } else {
          startIndex = 1;
        }
        model.createPortfolioFromFile(words[startIndex], words[startIndex + 1]);
        break;
      case "save":
        if (words[1].equals("portfolio")) {
          startIndex = 2;
        } else {
          startIndex = 1;
        }
        model.savePortfolioToFile(words[startIndex], words[startIndex + 1]);
        break;
      case "list":
        Portfolio p = null;
        try {
          p = model.getPortfolio(words[1]);
        } catch (FileNotFoundException e) {
          System.out.println("Cannot find portfolio with this name.");
          break;
        }
        view.examineComposition(p);
        break;
      case "value":
        if (words.length < 3) {
          System.out.println("Please provide a date.");
          break;
        }
        float value = model.determineValue(words[1], words[2]);
        view.displayPortfolioValue(words[1], words[2], value);
        break;
      case "search":
        String matches = model.getTickerMatches(words[1]);
        view.showTickerMatches(matches);
        break;
      default:
        System.out.println("Did not understand the command, please try again");
        break;
    }
  }

  boolean isInteger(float num) {
    return (float) (int) num == num;
  }
}
