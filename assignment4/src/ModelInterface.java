
public interface ModelInterface {

  /**
   *
   * @param tickerSymbols
   * @param stockAmounts in the order of the stocks specified in tickerSymbols, says how many
   *                     of each stock to add to the portfolio
   * @return
   */
  ___ createPortfolio(String[] tickerSymbols, float[] stockAmounts);

  ___ createPortfolioFromFile();

  void savePortfolioToFile(Portfolio p, String filename);

  /**
   *
   * @param p
   */
  float determineValue(Portfolio p);
}
