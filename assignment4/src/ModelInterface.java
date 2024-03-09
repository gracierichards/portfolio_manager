//Notes for model implementation - model should save the stockAmounts * value of each stock at the
// time the user bought it

public interface ModelInterface {
  
  /**
   *
   * @param portfolioName
   * @param tickerSymbols
   * @param stockAmounts in the order of the stocks specified in tickerSymbols, says how many
   *                     of each stock to add to the portfolio
   * @return value of the portfolio at the time of creation
   */
  float createPortfolio(String portfolioName, String[] tickerSymbols, float[] stockAmounts);

  /**
   *
   * @param portfolioName
   * @return value of the portfolio at the time of creation
   */
  float createPortfolioFromFile(String portfolioName);

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
