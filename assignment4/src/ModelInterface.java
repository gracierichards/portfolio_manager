/**
 * The interface for the model. Is able to create a portfolio given ticker names and stock amounts,
 * create a portfolio from a file, save a portfolio to file, and determine the value of a portfolio
 * on a given date.
 */
public interface ModelInterface {

  /**
   * Creates a portfolio with the given name from the given stocks and number of each stock.
   *
   * @param portfolioName the name of the portfolio to be created.
   * @param tickerSymbols a String array of ticker symbols.
   * @param stockAmounts  in the order of the stocks specified in tickerSymbols, says how many
   *                      of each stock to add to the portfolio.
   */
  void createPortfolio(String portfolioName, String[] tickerSymbols, float[] stockAmounts);

  /**
   * Creates a portfolio using the data from a file. The format of the file should be the same as
   * a file created by the savePortfolioToFile method.
   *
   * @param portfolioName how the portfolio will be named
   * @param filename      the file to read the portfolio data from
   */
  void createPortfolioFromFile(String portfolioName, String filename);

  /**
   * Saves one of the users portfolios to a file containing the portfolio name and stock
   * composition.
   *
   * @param portfolioName the portfolio to write to file
   * @param filename      the name of the output file, exactly as given
   */
  void savePortfolioToFile(String portfolioName, String filename);

  /**
   * Calculates the total value of a portfolio on the given date.
   *
   * @param portfolioName the portfolio to find the value of
   * @param date          in MM/DD/YYYY format
   * @return the value of the portfolio
   */
  float determineValue(String portfolioName, String date);

  /**
   * Determines whether the given stock gained or lost on the given day.
   *
   * @param tickerSymbol the stock to check
   * @param date         the date to check the stock data for
   * @return a boolean, true if the stock gained, false if it lost value
   * @throws IllegalArgumentException if given an invalid ticker symbol
   */
  boolean stockDirection(String tickerSymbol, String date) throws IllegalArgumentException;

  /**
   * Determines whether the given stock gained or lost over the given period of time, from
   * start_date to end_date (both in MM/DD/YYYY). Uses closing values.
   *
   * @param tickerSymbol the stock to check
   * @param startDate    the start of the period to check
   * @param endDate      the end of the period to check
   * @return a boolean, true if the stock gained, false if it lost value
   * @throws IllegalArgumentException if given an invalid ticker symbol
   */
  boolean stockDirection(String tickerSymbol, String startDate, String endDate)
          throws IllegalArgumentException;
}
