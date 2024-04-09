import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FlexiblePortfolioModel extends Model{

  protected Map<String, Portfolio> portfolioList;

  public FlexiblePortfolioModel() {
    this.portfolioList = new HashMap<>();
    new File("stockcsvs").mkdirs();
  }


  protected Portfolio getPortfolio(String portfolioName) throws FileNotFoundException {
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

    Portfolio portfolio = new Portfolio(portfolioName);
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
   * Purchase a specified number of shares of a stock with the given ticker symbol
   * for the specified portfolio and date.
   * Throws an IllegalArgumentException if the portfolio is not found.
   *
   * @param portfolioName The name of the portfolio where the shares will be purchased.
   * @param tickerSymbol  The ticker symbol of the stock to be purchased.
   * @param date          The date of the purchase.
   * @param numShares     The number of shares to be purchased.
   * @throws IllegalArgumentException If the specified portfolio is not found.
   */
  public void purchaseShares(String portfolioName, String tickerSymbol, String date, int numShares)
          throws IllegalArgumentException {
    Portfolio portfolio = portfolioList.get(portfolioName);
    if (portfolio == null) {
      throw new IllegalArgumentException("Portfolio not found.");
    }

    // Add purchase information to the portfolio
    portfolio.addStock(tickerSymbol, numShares);
    float costBasis = calculateCostBasis(tickerSymbol, numShares, date);

    // Update the cost basis map in the portfolio
    portfolio.costBasisMap.put(tickerSymbol, costBasis);

    portfolio.purchaseDates.put(tickerSymbol, date);
  }

  public String purchaseShares(String portfolioName, String tickerSymbol, String date, float numShares)
          throws IllegalArgumentException {
    Portfolio portfolio = portfolioList.get(portfolioName);
    if (portfolio == null) {
      throw new IllegalArgumentException("Portfolio not found.");
    }

    // Add purchase information to the portfolio
    portfolio.addStock(tickerSymbol, numShares);
    float costBasis = calculateCostBasis(tickerSymbol, numShares, date);

    // Update the cost basis map in the portfolio
    portfolio.costBasisMap.put(tickerSymbol, costBasis);

    portfolio.purchaseDates.put(tickerSymbol, date);
    return "Shares purchased successfully";
  }

  /**
   * Sell a specified number of shares of a stock with the given ticker symbol
   * from the specified portfolio and date.
   * Throws an IllegalArgumentException if the portfolio is not found.
   *
   * @param portfolioName The name of the portfolio from which the shares will be sold.
   * @param tickerSymbol  The ticker symbol of the stock to be sold.
   * @param date          The date of the sale.
   * @param numShares     The number of shares to be sold.
   * @throws IllegalArgumentException If the specified portfolio is not found.
   */
  public void sellShares(String portfolioName, String tickerSymbol, String date, int numShares)
          throws IllegalArgumentException {
    Portfolio portfolio = portfolioList.get(portfolioName);
    if (portfolio == null) {
      throw new IllegalArgumentException("Portfolio not found.");
    }
    // Remove sold shares from the portfolio
    portfolio.removeStock(tickerSymbol, numShares);
  }


  public float movingAverage(int numDays, String tickerSymbol, String date) {
    if (isValidTicker(tickerSymbol)) {
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
            float sum = 0;
            int numAdded = 0;
            for (int i = 0; i < numDays; i++) {
              float closingPrice = Float.parseFloat(line.split(",")[4]);
              sum += closingPrice;
              numAdded++;
              if (s.hasNextLine()) {
                line = s.nextLine();
              } else {
                break;
              }
            }
            return sum / numAdded;
          }
        }
        s.close();
      } catch (FileNotFoundException e) {
        System.out.println("Unable to read " + tickerSymbol + ".csv." + e.getMessage());
      }
      throw new RuntimeException("Reached end of file. Stock price not found in file.");
    } else {
      throw new IllegalArgumentException(tickerSymbol + " is not a valid ticker symbol.");
    }
  }

  public String findCrossovers(String tickerSymbol, String startDateString, String endDateString)
          throws IllegalArgumentException {
    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("M/d/yyyy");
    LocalDate curDate = LocalDate.parse(startDateString, formatter1);
    LocalDate endDate = LocalDate.parse(endDateString, formatter1);
    if (endDate.isBefore(curDate)) {
      throw new IllegalArgumentException("End date cannot be before the start date.");
    }
    if (isValidTicker(tickerSymbol)) {
      ArrayList<String> positiveCrossovers = new ArrayList<>();
      ArrayList<String> negativeCrossovers = new ArrayList<>();
      LocalDate prevDate = curDate.minusDays(1);
      String prevDateString = prevDate.format(formatter1);
      String curDateString = curDate.format(formatter1);
      while (!curDate.isAfter(endDate)) {
        float movingAverage30Day = movingAverage(30, tickerSymbol, curDateString);
        float stockPrice1 = getStockPrice(tickerSymbol, prevDateString, TypeOfPrice.CLOSE);
        float stockPrice2 = getStockPrice(tickerSymbol, curDateString, TypeOfPrice.CLOSE);
        if ((stockPrice1 < movingAverage30Day && stockPrice2 > movingAverage30Day)) {
          positiveCrossovers.add(curDateString);
        } else if ((stockPrice1 > movingAverage30Day && stockPrice2 < movingAverage30Day)) {
          negativeCrossovers.add(curDateString);
        }
        prevDateString = curDateString;
        curDate = curDate.plusDays(1);
        curDateString = curDate.format(formatter1);
      }
      return assemblePosNegCrossovers(positiveCrossovers, negativeCrossovers);
    } else {
      throw new IllegalArgumentException(tickerSymbol + " is not a valid ticker symbol.");
    }
  }

  /**
   * Finds moving average crossovers for a given ticker symbol within a specified date range.
   *
   * @param tickerSymbol    The ticker symbol of the stock.
   * @param startDateString The start date of the date range in the format "M/d/yyyy".
   * @param endDateString   The end date of the date range in the format "M/d/yyyy".
   * @param x               The number of days for the first moving average.
   * @param y               The number of days for the second moving average.
   * @return A string containing positive and negative crossover dates separated by commas.
   * @throws IllegalArgumentException If the end date is before the start date or if the ticker
   *                                  symbol is invalid.
   */
  public String findMovingCrossovers(String tickerSymbol, String startDateString,
                                     String endDateString, int x, int y)
          throws IllegalArgumentException {
    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("M/d/yyyy");
    LocalDate curDate = LocalDate.parse(startDateString, formatter1);
    LocalDate endDate = LocalDate.parse(endDateString, formatter1);
    if (curDate.isAfter(endDate)) {
      throw new IllegalArgumentException("End date cannot be before the start date.");
    }
    if (isValidTicker(tickerSymbol)) {
      ArrayList<String> positiveCrossovers = new ArrayList<>();
      ArrayList<String> negativeCrossovers = new ArrayList<>();
      LocalDate prevDate = curDate.minusDays(1);
      String prevDateString = prevDate.format(formatter1);
      String curDateString = curDate.format(formatter1);
      while (!curDate.isEqual(endDate)) {
        float yDayMovingAverage = movingAverage(y, tickerSymbol, curDateString);
        float xDayMovingAverage1 = movingAverage(x, tickerSymbol, prevDateString);
        float xDayMovingAverage2 = movingAverage(x, tickerSymbol, curDateString);
        if ((xDayMovingAverage1 < yDayMovingAverage && xDayMovingAverage2 > yDayMovingAverage)) {
          positiveCrossovers.add(curDateString);
        } else if ((xDayMovingAverage1 > yDayMovingAverage && xDayMovingAverage2 <
                yDayMovingAverage)) {
          negativeCrossovers.add(curDateString);
        }
        prevDateString = curDateString;
        curDate = curDate.plusDays(1);
        curDateString = curDate.format(formatter1);
      }
      return assemblePosNegCrossovers(positiveCrossovers, negativeCrossovers);
    } else {
      throw new IllegalArgumentException(tickerSymbol + " is not a valid ticker symbol.");
    }
  }

  private String assemblePosNegCrossovers(ArrayList<String> positiveCrossovers, ArrayList<String>
          negativeCrossovers) {
    StringBuilder ret = new StringBuilder();
    if (positiveCrossovers.isEmpty()) {
      ret.append("None");
    } else {
      for (String d : positiveCrossovers) {
        ret.append(d).append(",");
      }
    }
    ret.append(" ");
    if (negativeCrossovers.isEmpty()) {
      ret.append("None");
    } else {
      for (String d : negativeCrossovers) {
        ret.append(d).append(",");
      }
    }
    return ret.toString();
  }


  protected float calculateCostBasis(String tickerSymbol, int numShares, String purchaseDate) {
    float purchasePrice = getStockPrice(tickerSymbol, purchaseDate, FlexiblePortfolioModel.TypeOfPrice.CLOSE);
    // Calculate the cost basis of the purchased shares
    float costBasis = purchasePrice * numShares;
    return costBasis;
  }

  protected float calculateCostBasis(String tickerSymbol, float numShares, String purchaseDate) {
    float purchasePrice = getStockPrice(tickerSymbol, purchaseDate, FlexiblePortfolioModel.TypeOfPrice.CLOSE);
    // Calculate the cost basis of the purchased shares
    float costBasis = purchasePrice * numShares;
    return costBasis;
  }

  /**
   * Calculates the total cost basis of stocks in a specified portfolio up to a given date.
   *
   * @param portfolioName The name of the portfolio.
   * @param date          The date up to which the cost basis is calculated.
   * @return The total cost basis of the stocks in the portfolio up to the specified date.
   */
  public float totalCostBasis(String portfolioName, String date) {
    Portfolio portfolio = portfolioList.get(portfolioName);
    if (portfolio == null) {
      System.out.println("Portfolio not found.");
      return 0;
    }
    float totalCostBasis = 0;
    for (Map.Entry<String, String> entry : portfolio.purchaseDates.entrySet()) {
      String tickerSymbol = entry.getKey();
      String purchaseDate = entry.getValue();
      if (purchaseDate.compareTo(date) <= 0) {
        float stockCostBasis = portfolio.costBasisMap.getOrDefault(tickerSymbol, 0f);
        totalCostBasis += stockCostBasis;
      }
    }
    return totalCostBasis;
  }

  /**
   * Calculates the total value of a portfolio on a specified date.
   *
   * @param portfolioName The name of the portfolio.
   * @param date          The date for which the portfolio value is calculated.
   * @return The total value of the portfolio on the specified date.
   */
  public float portfolioValueOnDate(String portfolioName, String date) {
    Portfolio portfolio = portfolioList.get(portfolioName);
    if (portfolio == null) {
      System.out.println("Portfolio not found.");
      return 0;
    }

    float totalValue = 0;
    for (Map.Entry<String, String> entry : portfolio.purchaseDates.entrySet()) {
      String tickerSymbol = entry.getKey();
      String purchaseDate = entry.getValue();
      if (purchaseDate.compareTo(date) <= 0) {
        // Purchase date is on or before the given date
        float numShares = portfolio.getStocks().getOrDefault(tickerSymbol, 0.0f);
        float currentPrice = getStockPrice(tickerSymbol, date, FlexiblePortfolioModel.TypeOfPrice.CLOSE);
        totalValue += currentPrice * numShares;
      }
    }
    return totalValue;
  }


  public String chartPerformance(String portfolioName, String startDate, String endDate) {
    return PerformanceChart.generatePerformanceChart(portfolioName, startDate, endDate, this);
  }


  public String chartPerformanceStock(String tickerSymbol, String startDate, String endDate)
          throws IllegalArgumentException {
    return PerformanceChart.generatePerformanceChartStock(tickerSymbol, startDate, endDate, this);
  }

  public String investFixedAmount(String portfolioName, float amount, String date, Map<String, Float> weightDistribution) {
    Portfolio portfolio = portfolioList.get(portfolioName);
    if (portfolio == null) {
      return "Portfolio not found.";
    }

    // Calculate the amount to be invested in each stock
    Map<String, Float> investments = new HashMap<>();
    for (Map.Entry<String, Float> entry : weightDistribution.entrySet()) {
      String tickerSymbol = entry.getKey();
      float weight = entry.getValue();
      float amountToInvest = (weight / 100) * amount;
      investments.put(tickerSymbol, amountToInvest);
    }

    // Purchase shares for each stock in the portfolio
    StringBuilder result = new StringBuilder();
    for (Map.Entry<String, Float> entry : investments.entrySet()) {
      String tickerSymbol = entry.getKey();
      float amountToInvest = entry.getValue();

      //Calculate the number of shares to buy for each stock.
      float pricePerShare = getStockPrice(tickerSymbol, date, TypeOfPrice.CLOSE);
      if (pricePerShare > 0) {
        float numShares = amountToInvest / pricePerShare;
        purchaseShares(portfolioName, tickerSymbol, date, numShares);
      } else {
        result.append("Failed to retrieve stock price for ").append(tickerSymbol).append("\n");
      }
    }
    return amount + "Amount invested!";
  }

  /**
   * Helper method to calculate the number of shares to be purchased for each stock based on
   * the weight distribution and the investment amount.
   *
   * @param weightDistribution Map containing the weight distribution for each stock.
   * @param amount Amount to be invested.
   * @param date Date for which the prices are considered.
   * @return Array containing ticker symbols as keys and the corresponding number of shares as values.
   */
  public float[] calculateNumShares(float amount, Map<String, Float> weightDistribution, String date) {
    float[] numSharesArray = new float[weightDistribution.size()];
    int index = 0;
    for (Map.Entry<String, Float> entry : weightDistribution.entrySet()) {
      String tickerSymbol = entry.getKey();
      float weight = entry.getValue();
      float amountToInvest = (weight / 100) * amount; // Convert weight to percentage
      float numShares = amountToInvest / getStockPrice(tickerSymbol, date, TypeOfPrice.CLOSE);
      numSharesArray[index++] = numShares;
    }

    return numSharesArray;
  }

  public String dollarCostAveraging(String portfolioName, float amount, String startDate, String endDate, int frequency, Map<String, Float> weightDistribution) {

    // Calculate the number of periods
    LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    LocalDate end;
    if (endDate == null) {
      // If endDate is null, set end to the current date
      end = LocalDate.now();
    } else {
      // Otherwise, parse the endDate string
      end = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }
    int totalPeriods = (int) ChronoUnit.DAYS.between(start, end) / frequency;

    // Get ticker symbols from weight distribution
    String[] tickerSymbols = weightDistribution.keySet().toArray(new String[0]);

    // Calculate numShares array using calculateNumShares method
    float[] numShares = calculateNumShares(amount, weightDistribution, startDate);

    // Create portfolio with ticker symbols and numShares
    createPortfolio(portfolioName, tickerSymbols, numShares);
    float totalInvestment = amount;
    // Reinvest the amount periodically using investFixedAmount
    for (int i = 1; i < totalPeriods; i++) {
      String currentDate = start.plusDays(i * frequency).format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
      investFixedAmount(portfolioName, amount, currentDate, weightDistribution);
      totalInvestment += amount;
    }

    return "Dollar-cost averaging completed successfully. Total money invested: " + totalInvestment;
  }
}
