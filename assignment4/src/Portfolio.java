import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Portfolio class represents a collection of stocks for a particular investor.
 * It contains methods to manage and retrieve information about the stocks within the portfolio.
 */
public class Portfolio {
  private String name; // Name of the portfolio
  private Map<String, Integer> stocks; // Map to store stocks with their corresponding amounts

  /**
   * Constructor to initialize a new Portfolio with a given name.
   *
   * @param name The name of the portfolio.
   */
  public Portfolio(String name) {
    this.name = name;
    this.stocks = new HashMap<>();
  }

  /**
   * Method to add a stock with a specified amount to the portfolio.
   *
   * @param tickerSymbol The ticker symbol of the stock to be added.
   * @param amount       The amount of the stock to be added.
   */
  public void addStock(String tickerSymbol, int amount) {
    stocks.put(tickerSymbol, amount);
  }

  /**
   * Method to retrieve the name of the portfolio.
   *
   * @return The name of the portfolio.
   */
  public String getName() {
    return name;
  }

  /**
   * Method to retrieve the map containing the stocks and their corresponding amounts.
   *
   * @return A map containing the stocks and their amounts.
   */
  public Map<String, Integer> getStocks() {
    return stocks;
  }

  /**
   * Method to retrieve the set of ticker symbols representing the stocks in the portfolio.
   *
   * @return A set containing the ticker symbols of the stocks.
   */
  public Set<String> getKeys() {
    return stocks.keySet();
  }
}
