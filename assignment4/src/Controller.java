import java.io.FileNotFoundException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The implementation of Controller. Its processCommand method handles the cases for each of the
 * possible commands this program accepts - see SETUP-README.txt for documentation of commands.
 */
public class Controller implements ControllerInterface {
  /**
   * Unlike in Assignment 4, the controller now takes in the scanner created by Main. This is
   * because for one of the commands, moving-crossovers, the controller has to prompt the user
   * for two ints, x and y, after entering the initial command. Therefore, the controller needs
   * access to the scanner for user input.
   * The scanner being instantiated in Main was kept, so Main can check whether the command says
   * quit and terminate the program from there. The controller does not take in a more general
   * input like an InputStream, because this would not be compatible with the scanner taking input
   * in Main.
   */
  private Model model;
  private View view;
  private Scanner scanner;

  /**
   * Instantiates the Controller.
   *
   * @param m the Model
   * @param v the View
   */
  public Controller(Model m, View v, Scanner scanner) {
    this.model = m;
    this.view = v;
    this.scanner = scanner;
  }

  /**
   * Processes the given command input.
   *
   * @param input The command input to process.
   */
  public void processCommand(String input) {

    try {
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
          try {
            float value = model.determineValue(words[1], words[2]);
            view.displayPortfolioValue(words[1], words[2], value);
          } catch (DateTimeParseException e) {
            System.out.println("Invalid date. Date must be given in MM/DD/YYYY format. Please try "
                    + "again.");
          }
          break;
        case "search":
          String matches = model.getTickerMatches(words[1]);
          view.showTickerMatches(matches);
          break;
        case "stock-direction-day":
          if (words.length < 3) {
            System.out.println("Please provide a ticker symbol and date.");
            break;
          }
          boolean isGained;
          try {
            isGained = model.stockDirection(words[1], words[2]);
          } catch (Exception e) {
            System.out.println(e.getMessage());
            break;
          }
          if (isGained) {
            System.out.println(words[1] + " gained value.");
          } else {
            System.out.println(words[1] + " lost value.");
          }
          break;
        case "stock-direction-over-time":
          if (words.length < 4) {
            System.out.println("Please provide a ticker symbol, start date, and end date.");
            break;
          }
          try {
            isGained = model.stockDirection(words[1], words[2], words[3]);
          } catch (Exception e) {
            System.out.println(e.getMessage());
            break;
          }
          if (isGained) {
            System.out.println(words[1] + " gained value.");
          } else {
            System.out.println(words[1] + " lost value.");
          }
          break;
        case "moving-average":
          if (words.length < 4) {
            System.out.println("Please provide a number of days to calculate the average over, a "
                    + "ticker symbol, and the date of the last day in the desired period.");
            break;
          }
          int x;
          try {
            x = Integer.parseInt(words[1]);
          } catch (NumberFormatException e) {
            System.out.println("Please provide an integer number of days.");
            break;
          }
          float average = model.movingAverage(x, words[2], words[3]);
          System.out.println("The " + x + "-day moving average is " + average);
          break;
        case "crossovers":
          if (words.length < 4) {
            System.out.println("Please provide a ticker symbol, start date, and end date.");
            break;
          }
          String result = model.findCrossovers(words[1], words[2], words[3]);
          view.showCrossovers(result);
          break;
        case "moving-crossovers":
          if (words.length < 4) {
            System.out.println("Please provide a ticker symbol, start date, and end date.");
            break;
          }
          System.out.println("A moving crossover is when a moving average over a shorter period of"
                  + " time (ex. 30 days) crosses the amount of the moving average for a longer "
                  + "period of time (ex. 100 days). Please specify the number of days for the first"
                  + " moving average.");
          if (scanner.hasNextInt()) {
            x = scanner.nextInt();
          } else {
            System.out.println("Not a valid integer.");
            break;
          }
          System.out.println("Please specify the number of days for the second moving average.");
          int y;
          if (scanner.hasNextInt()) {
            y = scanner.nextInt();
          } else {
            System.out.println("Not a valid integer.");
            break;
          }
          if (x >= y) {
            System.out.println("Invalid values: second amount of days must be greater than the " +
                    "first.");
            break;
          }
          result = model.findMovingCrossovers(words[1], words[2], words[3], x, y);
          view.showCrossovers(result);
          break;
        case "CostBasis":
          if (words.length < 3) {
            System.out.println("Please provide a portfolio name and date.");
            break;
          }
          float costBasis = model.totalCostBasis(words[1], words[2]);
          view.displayTotalCostBasis(words[1], costBasis);
          break;
        case "portfolioValueOnDate":
          if (words.length < 3) {
            System.out.println("Please provide a portfolio name and a date.");
            break;
          }
          float portfolioValue = model.portfolioValueOnDate(words[1], words[2]);
          view.displayPortfolioValueOnDate(words[1], words[2], portfolioValue);
          break;
        case "chart-portfolio":
          if (words.length < 4) {
            System.out.println("Please provide a portfolio name, start date, and end date.");
            break;
          }
          System.out.println(model.chartPerformance(words[1], words[2], words[3]));
          break;
        case "chart-stock":
          if (words.length < 4) {
            System.out.println("Please provide a ticker symbol, start date, and end date.");
            break;
          }
          try {
            System.out.println(model.chartPerformanceStock(words[1], words[2], words[3]));
          } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
          }
          break;
        case "purchase":
          if (words.length < 5) {
            System.out.println("Invalid purchase command. Usage: purchase <portfolio_name>"
                    + " <ticker_symbol> <date> <numShares>");
            break;
          }
          String portfolioName1 = words[1];
          String tickerSymbol = words[2];
          String date = words[3];
          int numShares = Integer.parseInt(words[4]);
          model.purchaseShares(portfolioName1, tickerSymbol, date, numShares);
          System.out.println("Shares bought successfully");
          break;
        case "sell":
          if (words.length < 5) {
            System.out.println("Invalid sell command. Usage: sell <portfolio_name> "
                    + "<ticker_symbol> <date> <numShares>");
            break;
          }
          portfolioName = words[1];
          tickerSymbol = words[2];
          date = words[3];
          numShares = Integer.parseInt(words[4]);
          model.sellShares(portfolioName, tickerSymbol, date, numShares);
          System.out.println("Shares sold successfully");
          break;
        default:
          System.out.println("Did not understand the command, please try again");
          break;
      }
    } catch (DateTimeParseException e) {
      System.out.println("Date must be in MM/DD/YYYY format. Please try again.");
    }
  }

  boolean isInteger(float num) {
    return (float) (int) num == num;
  }
}
