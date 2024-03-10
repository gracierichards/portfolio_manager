import java.util.ArrayList;
import java.util.List;

/**
 * Maybe the create portfolio command for the user can be in the format:
 * create portfolio <portfolio_name> MSFT:20, AAPL:10, NVDA:30
 * for a user who wants to make a new portfolio with 20 shares of MSFT, 10 shares of AAPL, and 30
 * shares of NVDA
 * The load portfolio from file command can be:
 * load portfolio <portfolio_name> <filename>
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
  private Model model;
  private View view;
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
                      + "stock" + words[i].substring(0, words[i].indexOf(":")) + "in the "
                      + "portfolio.");
            }
          }
          model.createPortfolio(portfolioName, tickerSymbols.toArray(new String[0]),
                  stockAmounts.toArray(new float[0]));
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
        Portfolio p = model.getPortfolio(words[1]);
        view.examineComposition(p);
        break;
      case "value":
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
