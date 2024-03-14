import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

public class Model implements ModelInterface {
  protected Map<String, Portfolio> portfolioList;

  public Model() {
    this.portfolioList = new HashMap<>();
    new File("stockcsvs").mkdirs();
  }

  Portfolio getPortfolio(String portfolioName) throws IllegalArgumentException {
    if (!portfolioList.containsKey(portfolioName)) {
      throw new IllegalArgumentException("Cannot find portfolio with this name.");
    }
    return portfolioList.get(portfolioName);
  }

  @Override
  public void createPortfolio(String portfolioName, String[] tickerSymbols, float[] stockAmounts) {
    if (portfolioList.containsKey(portfolioName)) {
      throw new IllegalArgumentException("Portfolio with the same name already exists.");
    }

    Portfolio portfolio = new Portfolio(portfolioName);
    for (int i = 0; i < tickerSymbols.length; i++) {
      File file = new File("stockcsvs", tickerSymbols[i] + ".csv");
      if (file.exists()) {
        portfolio.addStock(tickerSymbols[i], (int)stockAmounts[i]);
        continue;
      }
      String csvData = getStockData(tickerSymbols[i]);
      //Check if it's a valid ticker symbol
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
        writer.write(csvData);
      } catch (IOException e) {
        throw new RuntimeException("Error writing stock data to file. " + e.getMessage());
      }
      if (csvData.charAt(0) == '{') {
        System.out.println(tickerSymbols[i] + " is not a valid ticker symbol. Not adding to "
                + "portfolio.");
      } else {
        portfolio.addStock(tickerSymbols[i], (int) stockAmounts[i]);
      }
    }
    portfolioList.put(portfolioName, portfolio);
    //System.out.println("Portfolio created successfully.");
  }

  @Override
    public void savePortfolioToFile(String portfolioName, String filename) {
        Portfolio portfolio = portfolioList.get(portfolioName);
        if (portfolio == null) {
            System.out.println("Portfolio not found.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("Portfolio Name: " + portfolio.getName());
            writer.newLine();
            writer.write("Stock Composition:");
            writer.newLine();
            for (Map.Entry<String, Integer> entry : portfolio.getStocks().entrySet()) {
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

      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
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
  }


  /**
   * Contains the functionality of AlphaVantageDemo, and downloads the stock data for the stock
   * with the given ticker symbol on the given date. Returns all the data as a String in csv
   * format - lines separated by newline characters, and individual cells separated by commas.
   */
  private String getStockData(String tickerSymbol) {
    return getAlphaVantageData("function=TIME_SERIES_DAILY"
            + "&outputsize=full"
            + "&symbol"
            + "=" + tickerSymbol);
  }

  /**
   * The user can look up ticker symbols that match the name of a company, or all ticker symbols
   * that start with the inputted string. This method will query Alpha Vantage for a list of
   * matching results.
   * It will only include results whose type is "Equity" and whose region is "United States".
   * @param query the partial or full name of a company or ticker symbol being looked up by the
   *              user
   * @return data in csv format of the ticker symbol matches. The columns output by Alpha
   * Vantage are symbol, name, type, region, marketOpen, marketClose, timezone, currency, and
   * matchScore.
   */
  String getTickerMatches(String query) {
    return getAlphaVantageData("function=SYMBOL_SEARCH"
            + "&keywords=" + query);
  }

  /**
   * Helper function that performs the common functionality between getStockData and
   * getTickerMatches.
   */
  private String getAlphaVantageData(String urlPart) {
    String apiKey = "8FDS9CHM4YROZVC5";
    URL url = null;
    try {
      url = new URL("https://www.alphavantage" + ".co/query?" + urlPart + "&apikey="
              + apiKey + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }
    InputStream in = null;
    StringBuilder output = new StringBuilder();
    String tickerSymbol = urlPart.substring(urlPart.indexOf("symbol") + "symbol".length()
            + 1);
    try {
      in = url.openStream();
      int b;

      while ((b=in.read())!=-1) {
        output.append((char)b);
      }

      //if (output.charAt(0) == '{') {
      //  System.out.println(tickerSymbol + " is not a valid ticker symbol.");
      //}
    } catch (IOException e) {
      if (urlPart.contains("TIME_SERIES_DAILY")) {
        System.out.println("No price data found for " + tickerSymbol);
      } else {
        String query = urlPart.substring(urlPart.indexOf("keywords")
                + "keywords".length() + 1);
        System.out.println("No ticker symbols or companies found for " + query);
      }
    }
    return output.toString();
  }

  public float determineValue(String portfolioName, String date) {
    Portfolio p = portfolioList.get(portfolioName);
    if (p == null) {
      System.out.println("Portfolio not found.");
      return 0;
    }
    float sum = 0;
    for (Map.Entry<String, Integer> entry : p.getStocks().entrySet()) {
      float price = 0;
      try {
        File file1 = new File("stockcsvs", entry.getKey() + ".csv");
        Scanner s = new Scanner(file1);
        String line;
        //skip first line, which is the header
        if (s.hasNextLine()) {
          line = s.nextLine();
        }
        while (s.hasNextLine()) {
          line = s.nextLine();
          String csvDate = line.split(",")[0];
          if (compareDates(date, csvDate) >= 0) {
          //the same as saying if the value is equal to 0 or 1
            price = Float.parseFloat(line.split(",")[1]);
            break;
          }
        }
        s.close();
      } catch (FileNotFoundException e) {
        System.out.println("Unable to read " + entry.getKey() + ".csv." + e.getMessage());
      }
      sum += price * entry.getValue();
    }
    return sum;
  }

  /**
   * Helper method to determine whether a ticker symbol is valid. Can only be used for tickers that
   * have been queried at least once to Alpha Vantage by this program.
   * So far only used by determineValue.
   * @param ticker the ticker symbol to be checked.
   * @return whether the ticker symbol is valid.
   */
  private boolean isValidTicker(String ticker) throws FileNotFoundException {
    File file1 = new File("stockcsvs", ticker + ".csv");
    Scanner s = new Scanner(file1);
    String line = "";
    if (s.hasNextLine()) {
      line = s.nextLine();
    }
    if (line.equals("{" + System.lineSeparator())) {
      return false;
    } else {
      return true;
    }
  }

  /**
   * Determines whether two dates are the same date, one date is before the other, or if the date
   * comes after the other. It takes in one date in the format the user provides, which should be
   * MM/DD/YYYY, and one date from an Alpha Vantage csv.
   * @returns 0 if the dates are the same, a negative number if inputDate is before csvDate, and a
   * positive number if inputDate is after inputDate.
   */
  int compareDates(String inputDate, String csvDate) {
    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("M/d/yyyy");
    DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("M/d/yy");
    LocalDate date1 = LocalDate.parse(inputDate, formatter1);
    LocalDate date2 = LocalDate.parse(csvDate, formatter2);
    return date1.compareTo(date2);
  }
}
