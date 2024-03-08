import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Map;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.HashMap;

public class Model implements ModelInterface {
  private Map<String, Portfolio> portfolioList;

  public Model() {
    this.portfolioList = new HashMap<>();
  }

  /**
   * Helper function to determine if name is the name of a portfolio that has been created or not.
   */
  private void checkValidPortfolioName(String name) throws IllegalArgumentException {
    if (!portfolioList.containsKey(name)) {
      throw new IllegalArgumentException("Not a valid portfolio name.");
    }
  }



  @Override
  public float createPortfolio(String portfolioName, String[] tickerSymbols, float[] stockAmounts) {
    if (portfolioList.containsKey(portfolioName)) {
      throw new IllegalArgumentException("Portfolio with the same name already exists.");
    }

    Portfolio portfolio = new Portfolio(portfolioName);
    for (int i = 0; i < tickerSymbols.length; i++) {
      portfolio.addStock(tickerSymbols[i], (int)stockAmounts[i]);
    }
    portfolioList.put(portfolioName, portfolio);

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
            System.out.println("Portfolio saved to file: " + filename);
        } catch (IOException e) {
            System.out.println("Error saving portfolio to file: " + e.getMessage());
        }
    }


  /**
   * Contains the functionality of AlphaVantageDemo, and downloads the stock data for the stock
   * with the given ticker symbol on the given date. Returns all the data as a String in csv
   * format - lines separated by newline chaacters, and individual cells separated by commas.
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
        throw new IllegalArgumentException("No price data found for " + tickerSymbol);
      } else {
        String query = urlPart.substring(urlPart.indexOf("keywords")
                + "keywords".length() + 1);
        throw new IllegalArgumentException("No ticker symbols or companies found for " + query);
      }
    }
    return output.toString();
  }

  public float determineValue(String portfolioName, String date) throws IllegalArgumentException {
    checkValidPortfolioName(portfolioName);
    Portfolio p = portfolioList.get(portfolioName);
    float sum = 0;
    for (Stock s : p.getStocks()) {

    }
  }
}
