public interface ViewInterface {
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
}
