
/**
 * This interface defines methods for interacting with the AlphaVantage API.
 */
public interface API_Interface {

  /**
   * Contains the functionality of AlphaVantageDemo, and downloads the stock data for the stock
   * with the given ticker symbol on the given date. Returns all the data as a String in csv
   * format - lines separated by newline characters, and individual cells separated by commas.
   *
   * @param tickerSymbol the ticker symbol of the stock
   * @return stock data as a String in csv format
   */
  String getStockData(String tickerSymbol);

  /**
   * Retrieves data from the AlphaVantage API based on the provided URL part.
   *
   * @param urlPart the specific part of the URL for the AlphaVantage API query
   * @return data from AlphaVantage as a String in CSV format
   */
  String getAlphaVantageData(String urlPart);

  /**
   * The user can look up ticker symbols that match the name of a company, or all ticker symbols
   * that start with the inputted string. This method will query Alpha Vantage for a list of
   * matching results. It will only include results whose type is "Equity" and whose region is
   * "United States".
   *
   * @param query the partial or full name of a company or ticker symbol being looked up by the user
   * @return data in csv format of the ticker symbol matches.
   * The columns output by AlphaVantage are symbol, name, type, region, marketOpen, marketClose,
   * timezone, currency, and matchScore.
   */
  String getTickerMatches(String query);
}