import java.util.Map;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Model implements ModelInterface {
  private Map<String, Portfolio> portfolioList;

  /**
   * Helper function to determine if name is the name of a portfolio that has been created or not.
   */
  private void checkValidPortfolioName(String name) throws IllegalArgumentException {
    if (!portfolioList.containsKey(name)) {
      throw new IllegalArgumentException("Not a valid portfolio name.");
    }
  }

  /**
   * Contains the functionality of AlphaVantageDemo, and downloads the stock data for the stock
   * with the given ticker symbol on the given date. Returns all the data as a String in csv
   * format - lines separated by newline chaacters, and individual cells separated by commas.
   */
  private String getAlphaVantageData(String tickerSymbol) {
    String apiKey = "8FDS9CHM4YROZVC5";
    URL url = null;
    try {
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + tickerSymbol + "&apikey="+apiKey+"&datatype=csv");
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
      throw new IllegalArgumentException("No price data found for " + tickerSymbol);
    }
    return output.toString();
  }

  public float determineValue(String portfolioName, String date) throws IllegalArgumentException {
    checkValidPortfolioName(portfolioName);

  }
}
