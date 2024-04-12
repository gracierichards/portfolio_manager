/**
 * The interface for the model. Is able to create a portfolio given ticker names and stock amounts,
 * create a portfolio from a file, save a portfolio to file, and determine the value of a portfolio
 * on a given date.
 */
public interface FlexiblePortfolioModelInterface extends ModelInterface {

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

  /**
   * Calculates the average price for the given stock in the last x days, starting from the given
   * date. It includes the last x days for which stock prices are available.
   *
   * @param numDays      x in the x-day moving average
   * @param tickerSymbol the stock to calculate the average for
   * @param date         the last date of the desired period, in MM/DD/YYYY
   * @return the average stock price of the given period
   * @throws IllegalArgumentException if given an invalid ticker symbol
   */
  float movingAverage(int numDays, String tickerSymbol, String date)
          throws IllegalArgumentException;

  /**
   * Returns a list of the positive crossovers and negative crossovers within the given time
   * period.
   *
   * @param tickerSymbol the stock to check for crossovers
   * @param startDate    the start date of the period to check (in MM/DD/YYYY format)
   * @param endDate      the end date of the period to check (in MM/DD/YYYY format)
   * @return a string listing the positive and negative crossovers. For the View to parse into the
   * final printed output.
   * @throws IllegalArgumentException if given an invalid ticker symbol
   */
  String findCrossovers(String tickerSymbol, String startDate, String endDate)
          throws IllegalArgumentException;

  /**
   * Returns a list of the positive crossovers and negative crossovers within the given time
   * period. A moving crossover happens when an x-day moving average crosses from one side to
   * another side of a y-day moving average, with x lesser than y.
   *
   * @param tickerSymbol the stock to check for crossovers
   * @param startDate    the start date of the period to check (in MM/DD/YYYY format)
   * @param endDate      the end date of the period to check (in MM/DD/YYYY format)
   * @param x            the number of days in the moving average that is checked if it crosses
   *                     the other moving average
   * @param y            the number of days in the moving average that is the boundary for the
   *                     other moving average to cross
   * @return a string listing the positive and negative crossovers. For the View to parse into the
   * final printed output.
   * @throws IllegalArgumentException if given an invalid ticker symbol
   */
  String findMovingCrossovers(String tickerSymbol, String startDate, String endDate, int x, int y)
          throws IllegalArgumentException;

  /**
   * Creates a horizontal bar chart of the performance of a portfolio over time. It has bars for
   * regular time intervals from startDate to endDate, and the bar is made up of asterisks, with
   * each asterisk representing the number of dollars indicated in the scale.
   *
   * @param portfolioName the portfolio to chart the performance for
   * @param startDate     the beginning of the time period to be included in the graph
   * @param endDate       the end of the time period to be included in the graph
   * @return the final graph as text
   */
  String chartPerformance(String portfolioName, String startDate, String endDate);

  /**
   * Creates a horizontal bar chart of the performance of a stock over time. It has bars for
   * regular time intervals from startDate to endDate, and the bar is made up of asterisks, with
   * each asterisk representing the number of dollars indicated in the scale.
   *
   * @param tickerSymbol the stock to chart the performance for
   * @param startDate    the beginning of the time period to be included in the graph
   * @param endDate      the end of the time period to be included in the graph
   * @return the final graph as text
   */
  String chartPerformanceStock(String tickerSymbol, String startDate, String endDate)
          throws IllegalArgumentException;
}
