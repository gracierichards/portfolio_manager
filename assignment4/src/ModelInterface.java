/**
 * The interface for the model. Is able to create a portfolio given ticker names and stock amounts,
 * create a portfolio from a file, save a portfolio to file, and determine the value of a portfolio
 * on a given date.
 */
public interface ModelInterface {
  
  /**
   * Creates a portfolio with the given name from the given stocks and number of each stock.
   * @param portfolioName the name of the portfolio to be created.
   * @param tickerSymbols a String array of ticker symbols.
   * @param stockAmounts in the order of the stocks specified in tickerSymbols, says how many
   *                     of each stock to add to the portfolio.
   * @return value of the portfolio at the time of creation.
   */
  void createPortfolio(String portfolioName, String[] tickerSymbols, float[] stockAmounts);

  /**
   *
   * @param portfolioName
   * @param filename
   * @return value of the portfolio at the time of creation
   */
  void createPortfolioFromFile(String portfolioName, String filename);

  /**
   * If portfolioName is not the name of a portfolio that has been created before, prints a message
   * to console and returns.
   * @param portfolioName
   * @param filename
   */
  void savePortfolioToFile(String portfolioName, String filename);

  /**
   * If portfolioName is not the name of a portfolio that has been created before, prints a message
   * to console and returns 0.
   * @param portfolioName
   * @param date in MM/DD/YYYY format
   * @return
   */
  float determineValue(String portfolioName, String date);
}
