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

  //To do for myself - write another method to get ticker symbol data from Alpha Vantage

  public float determineValue(String portfolioName, String date) throws IllegalArgumentException {
    checkValidPortfolioName(portfolioName);

  }
}
