/**
 * Interface for the View. The view handles user interface elements and presentation logic. It
 * provides visual representations of portfolio data and interacts with users through a
 * command-line interface (CLI)
 */
public interface ViewInterface {
  /**
   * Prints the portfolio name, and lists each stock and the amount of that stock in the portfolio.
   * @param p the name of the portfolio
   */
  void examineComposition(Portfolio p);

  /**
   * The user can look up ticker symbols that match the name of a company, or all ticker symbols
   * that start with the inputted string. Elsewhere the program will query Alpha Vantage for a
   * list of matching results. This method will display the top ticker symbols output by
   * Alpha Vantage.
   * @param csvContents data in csv format of the ticker symbol matches for a user query. The
   *                    columns output by Alpha Vantage are symbol, name, type, region, marketOpen,
   *                    marketClose, timezone, currency, and matchScore.
   */
  void showTickerMatches(String csvContents);

  /**
   * Prints the name of a portfolio and its value on a certain date, and prints the date.
   * @param portfolioName the name of the portfolio that the list command was run on.
   * @param date the date that the value of the portfolio was determined for. MM/DD/YYYY format.
   * @param value the total value of the portfolio.
   */
  void displayPortfolioValue(String portfolioName, String date, float value);

  /**
   * Takes a string containing the crossover results from the controller and prints it in a
   * user-friendly format.
   * @param results the crossover results from the model
   */
  void showCrossovers(String results);
}
