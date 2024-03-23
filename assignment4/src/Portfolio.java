import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Portfolio class represents a collection of stocks for a particular investor.
 * It contains methods to manage and retrieve information about the stocks within the portfolio.
 */
public class Portfolio {

  private Model model;
  private final String name; // Name of the portfolio
  private Map<String, Integer> stocks; // Map to store stocks with their corresponding amounts

  protected Map<String, Float> costBasis; // Map to store cost basis of stocks

  private String creationDate; // Field to store the creation date
  /**
   * Constructor to initialize a new Portfolio with a given name.
   *
   * @param name The name of the portfolio.
   */
  public Portfolio(String name) {
    this.name = name;
    this.stocks = new HashMap<>();
    this.costBasis = new HashMap<>();
    this.creationDate = getCurrentDate(); // Automatically set creation date
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
      int currentAmount = stocks.get(tickerSymbol);
      stocks.put(tickerSymbol, currentAmount + amount);
    } else {
      // If the stock is not already in the portfolio, add it
      stocks.put(tickerSymbol, amount);
    }
    float costBasis = calculateCostBasis(tickerSymbol, amount);
    this.costBasis.put(tickerSymbol, costBasis);
  }

  /**
   * Method to get the current date in the format "MM/DD/YYYY".
   *
   * @return The current date.
   */
  private String getCurrentDate() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    return LocalDate.now().format(formatter);
  }
  private float calculateCostBasis(String tickerSymbol, int numShares) {

    float purchasePrice = model.getStockPrice(tickerSymbol, creationDate, Model.TypeOfPrice.CLOSE);

    // Calculate the cost basis of the purchased shares
    float costBasis = purchasePrice * numShares;


    return costBasis;
  }

  /**
   * Method to remove a specified number of shares of a specific stock from the portfolio.
   *
   * @param tickerSymbol The ticker symbol of the stock to be removed.
   * @param numShares    The number of shares to be removed.
   * @throws IllegalArgumentException If the stock is not found in the portfolio or if the number of shares to remove exceeds the available shares.
   */
  public void removeStock(String tickerSymbol, int numShares) throws IllegalArgumentException {
    if (!stocks.containsKey(tickerSymbol)) {
      throw new IllegalArgumentException("Stock not found in portfolio.");
    }

    int currentShares = stocks.get(tickerSymbol);
    if (currentShares < numShares) {
      throw new IllegalArgumentException("Not enough shares to sell.");
    }

    int remainingShares = currentShares - numShares;
    if (remainingShares == 0) {
      // If no shares left, remove the stock from the portfolio
      stocks.remove(tickerSymbol);
    } else {
      // Update the number of shares in the portfolio
      stocks.put(tickerSymbol, remainingShares);
    }
  }

  /**
   * Method to calculate the total cost basis of the entire portfolio.
   *
   * @return The total cost basis of the portfolio.
   */
  public float totalCostBasis() {
    float totalCostBasis = 0;
    for (Map.Entry<String, Integer> entry : stocks.entrySet()) {
      String tickerSymbol = entry.getKey();
      int amount = entry.getValue();
      float stockCostBasis = costBasis.getOrDefault(tickerSymbol, 0.0f) * amount;
      totalCostBasis += stockCostBasis;
    }
    return totalCostBasis;
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
