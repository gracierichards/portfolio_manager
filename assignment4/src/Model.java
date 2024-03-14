import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

public class Model implements ModelInterface {
  protected Map<String, Portfolio> portfolioList;
  // A list of all stocks that Alpha Vantage data has been downloaded for
  private HashSet<String> tickersDownloaded;

  public Model() {
    this.portfolioList = new HashMap<>();
    this.tickersDownloaded = new HashSet<>();
    new File("stockcsvs").mkdirs();
  }

  Portfolio getPortfolio(String portfolioName) throws IllegalArgumentException {
    if (!portfolioList.containsKey(portfolioName)) {
      throw new IllegalArgumentException("Cannot find portfolio with this name.");
    }
    return portfolioList.get(portfolioName);
  }

  @Override
  public float createPortfolio(String portfolioName, String[] tickerSymbols, float[] stockAmounts) {
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
      String csvData = null;
      try {
        csvData = getStockData(tickerSymbols[i]);
      } catch (Exception e) {
        System.out.println("Data for ticker symbol " + tickerSymbols[i] + " not found. Not adding "
                + "this stock to portfolio.");
      }
      if (csvData != null) {
        tickersDownloaded.add(tickerSymbols[i]);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
          writer.write(csvData);
        } catch (IOException e) {
          throw new RuntimeException("Error writing stock data to file. " + e.getMessage());
        }

        portfolio.addStock(tickerSymbols[i], (int)stockAmounts[i]);
      }
    }
    portfolioList.put(portfolioName, portfolio);
    //System.out.println("Portfolio created successfully.");
    // For simplicity, returning a default initial value
    return 0.0f;
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
  public float createPortfolioFromFile(String portfolioName, String filename) {
    File file = new File(filename);
    if (!file.exists()) {
      System.out.println("File not found.");
      return 0.0f;
    }

    try (Scanner scanner = new Scanner(file)) {
      List<String> tickerSymbols = new ArrayList<>();
      List<Float> stockAmounts = new ArrayList<>();

      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] parts = line.split(":");
        if (parts.length != 2) {
          System.out.println("Invalid format in file.");
          return 0.0f;
        }
        tickerSymbols.add(parts[0]);
        stockAmounts.add(Float.parseFloat(parts[1]));
      }

      float[] amountsArray = new float[stockAmounts.size()];
      for (int i = 0; i < stockAmounts.size(); i++) {
        amountsArray[i] = stockAmounts.get(i);
      }

      return createPortfolio(portfolioName, tickerSymbols.toArray(new String[0]), amountsArray);
    } catch (FileNotFoundException e) {
      System.out.println("Error reading file: " + e.getMessage());
    } catch (NumberFormatException e) {
      System.out.println("Invalid data format in file.");
    }
    return 0.0f;
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
    try {
      in = url.openStream();
      int b;

      while ((b=in.read())!=-1) {
        output.append((char)b);
      }
    } catch (IOException e) {
      if (urlPart.contains("TIME_SERIES_DAILY")) {
        String tickerSymbol = urlPart.substring(urlPart.indexOf("symbol")
                + "symbol".length() + 1);
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
