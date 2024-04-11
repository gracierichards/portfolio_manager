import java.io.FileNotFoundException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
  private FlexiblePortfolioModel fleximodel;
  private API api;
  private ViewInterface view;
  private Scanner scanner;

  /**
   * Instantiates the Controller.
   *
   * @param m the Model
   * @param v the View
   */
  public Controller(Model m, FlexiblePortfolioModel fm, ViewInterface v, Scanner scanner) {
    this.model = m;
    this.fleximodel = fm;
    this.view = v;
    this.scanner = scanner;
  }
  private boolean isInteger1(String[] inputs) {
    for (String input : inputs) {
      try {
        Integer.parseInt(input);
        // Check if the input contains a decimal point
        if (input.contains(".")) {
          return false; // Return false if the input contains a decimal point
        }
      } catch (NumberFormatException e) {
        return false;
      }
    }
    return true;
  }
  /**
   * Processes the given command input.
   *
   * @param input The command input to process.
   */
  public void processCommand(String input) {

    try {
      String[] words = input.split(" ");
      isInteger1(words);
      switch (words[0]) {
        case "create":
          if (words.length < 3) {
            System.out.println("Please provide a portfolio name.");
            break;
          }
          createCommand(words[1], words[2], String.join(" ",
                  Arrays.copyOfRange(words, 3, words.length)));
          break;
        case "load":
          if (words.length < 4) {
            System.out.println("Please provide a portfolio name and file name.");
            break;
          }
          loadCommand(words[2], words[3]);
          break;
        case "save":
          if (words.length < 3) {
            System.out.println("Please provide a portfolio name and file name.");
            break;
          }
          saveCommand(words[2], words[3]);
          break;
        case "list":
          listCommand(words[1]);
          break;
        case "value":
          valueCommand(words);
          break;
        case "search":
          String matches = api.getTickerMatches(words[1]);
          view.showTickerMatches(matches);
          break;
        case "stock-direction-day":
          stockDirectionDay(words);
          break;
        case "stock-direction-over-time":
          stockDirectionOverTime(words);
          break;
        case "moving-average":
          movingAverageCommand(words);
          break;
        case "crossovers":
          crossoversCommand(words);
          break;
        case "moving-crossovers":
          movingCrossoversCommand(words);
          break;
        case "CostBasis":
          if (words.length < 3) {
            System.out.println("Please provide a portfolio name and date.");
            break;
          }
          //costBasisCommand(words[1], words[2]);
          break;
        case "portfolioValueOnDate":
          portfolioValueOnDate(words);
          break;
        case "chart-portfolio":
          chartPortfolioCommand(words);
          break;
        case "chart-stock":
          chartStockCommand(words);
          break;
        case "purchase":
          purchaseCommand(words);
          break;
        case "sell":
          sellCommand(words);
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

  protected void createCommand(String portfolioType, String portfolioName,
                               String tickersAndAmounts) {
    List<String> tickerSymbols = new ArrayList<>();
    List<Float> stockAmounts = new ArrayList<>();
    if (portfolioName.contains(":")) {
      view.invalidPortfolioNameMessage();
    } else {
      String[] words = tickersAndAmounts.split(" ");
      for (String word : words) {
        if (!word.contains(":")) {
          System.out.println("There is something wrong with the syntax of the create "
                  + "portfolio command.");
          return;
        }
        String value = word.substring(word.indexOf(":") + 1);
        if (isInteger(Float.parseFloat(value))) {
          tickerSymbols.add(word.substring(0, word.indexOf(":")));
          stockAmounts.add(Float.parseFloat(value));
        } else {
          System.out.println("Cannot purchase a fractional number of shares. Not including "
                  + "stock " + word.substring(0, word.indexOf(":")) + " in the "
                  + "portfolio.");
        }
      }
      float[] amountsArray = new float[stockAmounts.size()];
      for (int i = 0; i < stockAmounts.size(); i++) {
        amountsArray[i] = stockAmounts.get(i);
      }
      if (portfolioType.equals("inflexibleportfolio")) {
        model.createPortfolio(portfolioName, tickerSymbols.toArray(new String[0]),
                amountsArray);
      } else {
        fleximodel.createPortfolio(portfolioName, tickerSymbols.toArray(new String[0]),
                amountsArray);
      }

      System.out.println("Portfolio created successfully.");
    }
  }

  protected void loadCommand(String portfolioName, String path) {
    /*
    if (words[1].equals("portfolio")) {
      fleximodel.createPortfolioFromFile(words[startIndex], words[startIndex + 1]);
    } else if (words[1].equals("inflexibleportfolio")) {
      model.createPortfolioFromFile(words[startIndex], words[startIndex + 1]);
    }*/
    fleximodel.createPortfolioFromFile(portfolioName, path);
  }

  protected void saveCommand(String portfolioName, String path) {
    /*
    if (words[1].equals("portfolio")) {
      fleximodel.savePortfolioToFile(words[startIndex], words[startIndex + 1]);
    } else if (words[1].equals("inflexibleportfolio")) {
      model.savePortfolioToFile(words[startIndex], words[startIndex + 1]);
    }*/
    fleximodel.savePortfolioToFile(portfolioName, path);
  }

  protected void listCommand(String portfolioName) {
    InflexiblePortfolio p = null;
    try {
      p = fleximodel.getPortfolio(portfolioName);
    } catch (FileNotFoundException e) {
      view.invalidPortfolioNameMessage();
      return;
    }
    view.examineComposition(p);
  }

  protected void valueCommand(String[] words) {
    isInteger1(words);
    if (words.length < 3) {
      System.out.println("Please provide a date.");
      return;
    }
    try {
      float value = model.determineValue(words[1], words[2]);
      view.displayPortfolioValue(words[1], words[2], value);
    } catch (DateTimeParseException e) {
      System.out.println("Invalid date. Date must be given in MM/DD/YYYY format. Please try "
              + "again.");
    }
  }

  protected void stockDirectionDay(String[] words) {
    isInteger1(words);
    if (words.length < 3) {
      System.out.println("Please provide a ticker symbol and date.");
      return;
    }
    boolean isGained;
    try {
      isGained = model.stockDirection(words[1], words[2]);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return;
    }
    if (isGained) {
      System.out.println(words[1] + " gained value.");
    } else {
      System.out.println(words[1] + " lost value.");
    }
  }

  protected void stockDirectionOverTime(String[] words) {
    isInteger1(words);
    if (words.length < 4) {
      System.out.println("Please provide a ticker symbol, start date, and end date.");
      return;
    }
    boolean isGained;
    try {
      isGained = model.stockDirection(words[1], words[2], words[3]);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return;
    }
    if (isGained) {
      System.out.println(words[1] + " gained value.");
    } else {
      System.out.println(words[1] + " lost value.");
    }
  }

  protected void movingAverageCommand(String[] words) {
    isInteger1(words);
    if (words.length < 4) {
      System.out.println("Please provide a number of days to calculate the average over, a "
              + "ticker symbol, and the date of the last day in the desired period.");
      return;
    }
    int x;
    try {
      x = Integer.parseInt(words[1]);
    } catch (NumberFormatException e) {
      System.out.println("Please provide an integer number of days.");
      return;
    }
    float average = fleximodel.movingAverage(x, words[2], words[3]);
    System.out.println("The " + x + "-day moving average is " + average);
  }

  protected void crossoversCommand(String[] words) {
    isInteger1(words);
    if (words.length < 4) {
      System.out.println("Please provide a ticker symbol, start date, and end date.");
      return;
    }
    String result = fleximodel.findCrossovers(words[1], words[2], words[3]);
    view.showCrossovers(result);
  }

  protected void movingCrossoversCommand(String[] words) {
    isInteger1(words);
    if (words.length < 4) {
      System.out.println("Please provide a ticker symbol, start date, and end date.");
      return;
    }
    System.out.println("A moving crossover is when a moving average over a shorter period of"
            + " time (ex. 30 days) crosses the amount of the moving average for a longer "
            + "period of time (ex. 100 days). Please specify the number of days for the first"
            + " moving average.");
    int x;
    if (scanner.hasNextInt()) {
      x = scanner.nextInt();
    } else {
      System.out.println("Not a valid integer.");
      return;
    }
    System.out.println("Please specify the number of days for the second moving average.");
    int y;
    if (scanner.hasNextInt()) {
      y = scanner.nextInt();
    } else {
      System.out.println("Not a valid integer.");
      return;
    }
    if (x >= y) {
      System.out.println("Invalid values: second amount of days must be greater than the " +
              "first.");
      return;
    }
    String result = fleximodel.findMovingCrossovers(words[1], words[2], words[3], x, y);
    view.showCrossovers(result);
  }

  /*
  protected void costBasisCommand(String portfolioName, String date) {
    if (words.length < 3) {
      System.out.println("Please provide a portfolio name and date.");
      return;
    }
    float costBasis = fleximodel.totalCostBasis(words[1], words[2]);
    view.displayTotalCostBasis(words[1], costBasis);
  }

   */

  protected void portfolioValueOnDate(String[] words) {
    isInteger1(words);
    if (words.length < 3) {
      System.out.println("Please provide a portfolio name and a date.");
      return;
    }
    float portfolioValue = fleximodel.portfolioValueOnDate(words[1], words[2]);
    view.displayPortfolioValueOnDate(words[1], words[2], portfolioValue);
  }

  protected void chartPortfolioCommand(String[] words) {
    isInteger1(words);
    if (words.length < 4) {
      System.out.println("Please provide a portfolio name, start date, and end date.");
      return;
    }
    System.out.println(fleximodel.chartPerformance(words[1], words[2], words[3]));
  }

  protected void chartStockCommand(String[] words) {
    isInteger1(words);
    if (words.length < 4) {
      System.out.println("Please provide a ticker symbol, start date, and end date.");
      return;
    }
    try {
      System.out.println(fleximodel.chartPerformanceStock(words[1], words[2], words[3]));
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  protected void purchaseCommand(String[] words) {
    isInteger1(words);
    if (words.length < 5) {
      System.out.println("Invalid purchase command. Usage: purchase <portfolio_name>"
              + " <ticker_symbol> <date> <numShares>");
      return;
    }
    String portfolioName1 = words[1];
    String tickerSymbol = words[2];
    String date = words[3];
    int numShares = Integer.parseInt(words[4]);
    if (numShares < 0) {
      System.out.println("Number of shares cannot be negative!");
      return;
    }
    fleximodel.purchaseShares(portfolioName1, tickerSymbol, date, numShares);
    System.out.println("Shares bought successfully");
  }

  protected void sellCommand(String[] words) {
    isInteger1(words);
    if (words.length < 5) {
      System.out.println("Invalid sell command. Usage: sell <portfolio_name> "
              + "<ticker_symbol> <date> <numShares>");
      return;
    }
    String portfolioName = words[1];
    String tickerSymbol = words[2];
    String date = words[3];
    int numShares = Integer.parseInt(words[4]);
    if (numShares < 0) {
      System.out.println("Number of shares cannot be negative!");
      return;
    }
    fleximodel.sellShares(portfolioName, tickerSymbol, date, numShares);
    System.out.println("Shares sold successfully");
  }

  protected void investFixedAmountCommand(String[] words) {
    if (words.length < 5) {
      System.out.println("Invalid invest command. Usage: invest <portfolio_name> <amount> <date> "
              + "<ticker_symbol1>:<weight1> <ticker_symbol2>:<weight2> ...");
      return;
    }

    String portfolioName = words[1];
    float amount = Float.parseFloat(words[2]);
    String date = words[3];

    Map<String, Float> weightDistribution = new HashMap<>();
    for (int i = 4; i < words.length; i++) {
      String[] pair = words[i].split(":");
      if (pair.length != 2) {
        System.out.println("Invalid ticker symbol:weight pair: " + words[i]);
        continue;
      }
      String tickerSymbol = pair[0];
      float weight = Float.parseFloat(pair[1]);
      weightDistribution.put(tickerSymbol, weight);
    }

    String result = fleximodel.investFixedAmount(portfolioName, amount, date, weightDistribution);
    System.out.println(result);
  }

}
