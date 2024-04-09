import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

/**
 * The implementation of the Model. Is able to create a portfolio given ticker names and stock
 * amounts, create a portfolio from a file, save a portfolio to file, and determine the value of a
 * portfolio on a given date.
 */
public class Model implements ModelInterface {
  protected Map<String, InflexiblePortfolio> portfolioList;
  private API api;

  public Model() {
    this.portfolioList = new HashMap<>();
    new File("stockcsvs").mkdirs();
  }

  protected InflexiblePortfolio getPortfolio(String portfolioName) throws FileNotFoundException {
    if (!portfolioList.containsKey(portfolioName)) {
      throw new FileNotFoundException();
    }
    return portfolioList.get(portfolioName);
  }

  /**
   * createPortfolio has changed slightly compared to the version from Assignment 4. The difference
   * is that the logic for checking if a ticker symbol is a valid stock has been abstracted away
   * into a helper function called isValidTicker. This is different from isValidTicker in Assignment
   * 4, which could only check validity in certain cases, and the old method is used as a helper for
   * the new isValidTicker. The new method is a general function that any method in Model can use to
   * determine if a ticker symbol is valid.
   * This is an improvement over the original design because functionality that is required for
   * many parts of the program, determining if a ticker symbol is valid, is now in a separate
   * method, and createPortfolio does not duplicate code in this new method.
   */
  @Override
  public void createPortfolio(String portfolioName, String[] tickerSymbols, float[] stockAmounts)
          throws IllegalArgumentException {
    if (portfolioList.containsKey(portfolioName)) {
      System.out.println("Portfolio with the same name already exists.");
      return;
    }

    InflexiblePortfolio portfolio = new InflexiblePortfolio(portfolioName);
    for (int i = 0; i < tickerSymbols.length; i++) {
      if (isValidTicker(tickerSymbols[i])) {
        portfolio.addStock(tickerSymbols[i], (int) stockAmounts[i]);
      } else {
        System.out.println(tickerSymbols[i] + " is not a valid ticker symbol. Not adding to "
                + "portfolio.");
      }
    }
    portfolioList.put(portfolioName, portfolio);
  }

  @Override
  public void savePortfolioToFile(String portfolioName, String filename) {
    InflexiblePortfolio portfolio = portfolioList.get(portfolioName);
    if (portfolio == null) {
      System.out.println("Portfolio not found.");
      return;
    }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
      writer.write("Portfolio Name: " + portfolio.getName());
      writer.newLine();
      writer.write("Stock Composition:");
      writer.newLine();
      for (Map.Entry<String, Float> entry : portfolio.getStocks().entrySet()) {
        writer.write(entry.getKey() + ": " + entry.getValue());
        writer.newLine();
      }
      //System.out.println("Portfolio saved to file: " + filename);
      System.out.println("Portfolio saved successfully to file.");
    } catch (IOException e) {
      System.out.println("Error saving portfolio to file: " + e.getMessage());
    }
  }


  @Override
  public void createPortfolioFromFile(String portfolioName, String filename) {
    File file = new File(filename);
    if (!file.exists()) {
      System.out.println("File not found.");
      return;
    }

    try (Scanner scanner = new Scanner(file)) {
      List<String> tickerSymbols = new ArrayList<>();
      List<Float> stockAmounts = new ArrayList<>();

      //skip the header
      String line = scanner.nextLine();
      line = scanner.nextLine();
      while (scanner.hasNextLine()) {
        line = scanner.nextLine();
        String[] parts = line.split(":");
        if (parts.length != 2) {
          System.out.println("Invalid format in file.");
          return;
        }
        tickerSymbols.add(parts[0]);
        stockAmounts.add(Float.parseFloat(parts[1]));
      }

      float[] amountsArray = new float[stockAmounts.size()];
      for (int i = 0; i < stockAmounts.size(); i++) {
        amountsArray[i] = stockAmounts.get(i);
      }

      createPortfolio(portfolioName, tickerSymbols.toArray(new String[0]), amountsArray);
    } catch (FileNotFoundException e) {
      System.out.println("Error reading file: " + e.getMessage());
    } catch (NumberFormatException e) {
      System.out.println("Invalid data format in file.");
    }
    System.out.println("Portfolio loaded successfully from file.");
  }

  /**
   * determineValue has changed slightly compared to the version from Assignment 4. The difference
   * is that much of the code has been abstracted away into a helper function called getStockPrice.
   * Now any class in this package can use this method to find the price of a given stock on a given
   * date, and specify whether they want the opening or closing price.
   * This is an improvement over the original design because functionality that is required for
   * many parts of the program, finding the price of a stock, is now in a method accessible by the
   * rest of the program, and determineValue does not duplicate code in this new method.
   */
  @Override
  public float determineValue(String portfolioName, String date) {
    InflexiblePortfolio p = portfolioList.get(portfolioName);
    if (p == null) {
      System.out.println("Portfolio not found.");
      return 0;
    }
    float sum = 0;
    for (Map.Entry<String, Float> entry : p.getStocks().entrySet()) {
      float price = getStockPrice(entry.getKey(), date, Model.TypeOfPrice.CLOSE);
      sum += price * entry.getValue();
    }
    return sum;
  }

  protected enum TypeOfPrice {
    OPEN, CLOSE
  }

  /**
   * Helper method to determine whether a ticker symbol is valid. Also downloads the stock data if
   * it has not been downloaded yet.
   *
   * @param tickerSymbol the ticker symbol to be checked.
   * @return whether the ticker symbol is valid.
   */
  protected boolean isValidTicker(String tickerSymbol) {
    File file = new File("stockcsvs", tickerSymbol + ".csv");
    if (file.exists()) {
      return isQueriedTickerValid(tickerSymbol);
    }
    String csvData = api.getStockData(tickerSymbol);
    //Check if it's a valid ticker symbol
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
      writer.write(csvData);
    } catch (IOException e) {
      throw new RuntimeException("Error writing stock data to file. " + e.getMessage());
    }
    /*
    if (csvData.charAt(0) == '{') {
      return false;
    } else {
      return true;
    }
     */
    return csvData.charAt(0) != '{';
  }

  /**
   * Helper method used by isValidTicker. Can only be used for tickers that have been queried at
   * least once to Alpha Vantage by this program.
   *
   * @param ticker the ticker symbol to be checked.
   * @return whether the ticker symbol is valid.
   */
  private boolean isQueriedTickerValid(String ticker) {
    File file1 = new File("stockcsvs", ticker + ".csv");
    Scanner s = null;
    try {
      s = new Scanner(file1);
    } catch (FileNotFoundException e) {
      System.out.println("File not found.");
    }
    String line = "";
    if (s.hasNextLine()) {
      line = s.nextLine();
    }
    /*
    if (line.equals("{")) {
      return false;
    } else {
      return true;
    }
     */
    return !line.equals("{");
  }

  /**
   * Determines whether two dates are the same date, one date is before the other, or if the date
   * comes after the other. It takes in one date in the format the user provides, which should be
   * MM/DD/YYYY, and one date from an Alpha Vantage csv.
   *
   * @return 0 if the dates are the same, a negative number if inputDate is before csvDate,
   * and a positive number if inputDate is after csvDate.
   */
  int compareDates(String inputDate, String csvDate) throws DateTimeParseException {
    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("M/d/yyyy");
    DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-M-d");
    LocalDate date1 = LocalDate.parse(inputDate, formatter1);
    LocalDate date2 = LocalDate.parse(csvDate, formatter2);
    return date1.compareTo(date2);
  }

  /**
   * Returns the value of the given stock on the given date, or if there is no data for the given
   * date, the value on the last date before the given date.
   *
   * @param tickerSymbol the ticker symbol of the stock to look up
   * @param date         the date for which you want the value, in MM/DD/YYYY
   * @return the value of the stock on that day.
   */
  protected float getStockPrice(String tickerSymbol, String date, Model.TypeOfPrice typeOfPrice) {
    isValidTicker(tickerSymbol);
    try {
      File file1 = new File("stockcsvs", tickerSymbol + ".csv");
      Scanner s = new Scanner(file1);
      String line;
      //skip first line, which is the header
      if (s.hasNextLine()) {
        line = s.nextLine();
      }
      while (s.hasNextLine()) {
        line = s.nextLine();
        String csvDate = line.split(",")[0];
        //the same as saying if the value is equal to 0 or 1
        if (compareDates(date, csvDate) >= 0) {
          if (typeOfPrice == Model.TypeOfPrice.OPEN) {
            return Float.parseFloat(line.split(",")[1]);
          } else {
            return Float.parseFloat(line.split(",")[4]);
          }
        }
      }
      s.close();
    } catch (FileNotFoundException e) {
      System.out.println("Unable to read " + tickerSymbol + ".csv." + e.getMessage());
      return -1;
    }
    throw new RuntimeException("Reached end of file. Stock price not found in file.");
  }

  @Override
  public boolean stockDirection(String tickerSymbol, String date) throws IllegalArgumentException {
    return stockDirectionHelper(tickerSymbol, date, "");
  }

  @Override
  public boolean stockDirection(String tickerSymbol, String startDate, String endDate)
          throws IllegalArgumentException {
    return stockDirectionHelper(tickerSymbol, startDate, endDate);
  }


  /**
   * This function handles determining gain or loss for a day or over time. If endDate is the empty
   * string, then it finds the gain or loss for a single day, the startDate. Otherwise, it finds
   * the gain or loss over the period of time from startDate to endDate.
   */
  private boolean stockDirectionHelper(String tickerSymbol, String startDate, String endDate)
          throws IllegalArgumentException {
    if (isValidTicker(tickerSymbol)) {
      if (endDate.isEmpty()) {
        return stockDirectionHelperDay(tickerSymbol, startDate);
      } else {
        return stockDirectionHelperPeriod(tickerSymbol, startDate, endDate);
      }
    } else {
      throw new IllegalArgumentException(tickerSymbol + " is not a valid ticker symbol.");
    }
  }

  private boolean stockDirectionHelperDay(String tickerSymbol, String date) throws
          IllegalStateException {
    float openingPrice = getStockPrice(tickerSymbol, date, Model.TypeOfPrice.OPEN);
    float closingPrice = getStockPrice(tickerSymbol, date, Model.TypeOfPrice.CLOSE);
    if (closingPrice > openingPrice) {
      return true;
    } else if (closingPrice < openingPrice) {
      return false;
    } else {
      throw new IllegalStateException("Stock neither gained nor lost in the day.");
    }
  }

  private boolean stockDirectionHelperPeriod(String tickerSymbol, String startDate, String
          endDate)
          throws IllegalStateException {
    //Checking if endDate is after startDate
    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("M/d/yyyy");
    LocalDate startDateDate = LocalDate.parse(startDate, formatter1);
    LocalDate endDateDate = LocalDate.parse(endDate, formatter1);
    if (endDateDate.isBefore(startDateDate)) {
      throw new IllegalStateException("End date cannot be before the start date.");
    }

    float startPrice = getStockPrice(tickerSymbol, startDate, Model.TypeOfPrice.CLOSE);
    float endPrice = getStockPrice(tickerSymbol, endDate, Model.TypeOfPrice.CLOSE);
    if (endPrice > startPrice) {
      return true;
    } else if (endPrice < startPrice) {
      return false;
    } else {
      throw new IllegalStateException("Stock neither gained nor lost in this time period.");
    }
  }
}
