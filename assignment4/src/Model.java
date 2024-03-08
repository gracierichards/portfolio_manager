import java.util.Map;

public class Model implements ModelInterface {
  private Map<String, Portfolio> portfolioList;

  /**
   * Helper function to determine if name is the name of a portfolio that has been created or not.
   */
  private void checkValidPortfolioName(String name) throws IllegalArgumentException {
    if (!portfolioList.containsKey(name)) {
      throw new IllegalArgumentException("Not a valid portfolio name.");
    }
  }

  public float determineValue(String portfolioName, String date) throws IllegalArgumentException {
    checkValidPortfolioName(portfolioName);

  }
}
