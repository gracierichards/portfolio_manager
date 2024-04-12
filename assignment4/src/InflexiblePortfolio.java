import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * inflexiblePortfolio class represents a collection of stocks for a particular investor.
 * It contains methods to manage and retrieve information about the stocks within the portfolio.
 */

public class InflexiblePortfolio {
  private final String name; // Name of the portfolio
  protected Map<String, Float> stocks; // Map to store stocks with their corresponding amounts
  /**
   * Constructor to initialize a new Portfolio with a given name.
   *
   * @param name The name of the portfolio.
   */

  public InflexiblePortfolio(String name) {
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
    if (stocks.containsKey(tickerSymbol)) {
      // If the stock already exists in the portfolio, update the amount
      float currentAmount = stocks.get(tickerSymbol);
      stocks.put(tickerSymbol, currentAmount + amount);
    } else {
      // If the stock is not already in the portfolio, add it
      stocks.put(tickerSymbol, (float) amount);
    }
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
  public Map<String, Float> getStocks() {
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
