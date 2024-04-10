import java.util.Map;

/**
 * Implementation of the View. The view handles user interface elements and presentation logic. It
 * provides visual representations of portfolio data and interacts with users through a
 * command-line interface (CLI)
 */
public class View implements ViewInterface {

  @Override
  public void examineComposition(InflexiblePortfolio p) {
    System.out.println("Portfolio: " + p.getName());
    Map<String, Float> stocks = p.getStocks();
    for (Map.Entry<String, Float> entry : stocks.entrySet()) {
      System.out.println(entry.getKey() + ": " + entry.getValue());
    }
  }

  @Override
  public void showTickerMatches(String csvContents) {
    System.out.println("Matching stocks:");
    System.out.println("symbol\tname\t");
    String[] lines = csvContents.split("\n");
    for (int i = 1; i < lines.length; i++) {
      String[] elements = lines[i].split(",");
      String type = elements[2];
      String region = elements[3];
      if (type.equals("Equity") && region.equals("United States")) {
        System.out.println(elements[0] + "\t" + elements[1] + "\n");
      }
    }
  }

  @Override
  public void displayPortfolioValue(String portfolioName, String date, float value) {
    System.out.println("Portfolio: " + portfolioName);
    System.out.println("Date: " + date);
    System.out.println("Total Value: $" + value);
  }

  @Override
  public void showCrossovers(String results) {
    System.out.println("Positive crossovers:");
    String positive = results.split(" ")[0];
    String negative = results.split(" ")[1];
    for (String date : positive.split(",")) {
      System.out.println(date);
    }
    System.out.println("Negative crossovers:");
    for (String date : negative.split(",")) {
      System.out.println(date);
    }
  }

  @Override
  public void displayTotalCostBasis(String portfolioName, float totalCostBasis) {
    System.out.println("Total cost basis for portfolio " + portfolioName + ": " + totalCostBasis);
  }

  @Override
  public void displayPortfolioValueOnDate(String portfolioName, String date, float portfolioValue) {
    System.out.println("Portfolio value for " + portfolioName + " on " + date + ": "
            + portfolioValue);
  }

  @Override
  public void printMenu() {
    System.out.println(System.lineSeparator() + "\tPlease enter a command. See SETUP-README.txt "
            + "for full explanations of commands and their usage."
            + System.lineSeparator() + "\tcreate inflexibleportfolio <portfolio_name> "
            + "<ticker_symbol>:<integer> [<ticker_symbol>:<integer>...]"
            + System.lineSeparator() + "\tcreate portfolio <portfolio_name> "
            + "<ticker_symbol>:<integer> [<ticker_symbol>:<integer>...]"
            + System.lineSeparator() + "\tload portfolio <portfolio_name> <filename>"
            + System.lineSeparator() + "\tsave <portfolio_name> <filename>"
            + System.lineSeparator() + "\tlist <portfolio_name>"
            + System.lineSeparator() + "\tvalue <portfolio_name> MM/DD/YYYY"
            + System.lineSeparator() + "\tsearch <company_name>"
            + System.lineSeparator() + "\tsearch <ticker_symbol>"
            + System.lineSeparator() + "\tstock-direction-day <ticker_symbol> MM/DD/YYYY"
            + System.lineSeparator() + "\tstock-direction-over-time <ticker_symbol> start_date "
            + "end_date"
            + System.lineSeparator() + "\tmoving-average x <ticker_symbol> MM/DD/YYYY"
            + System.lineSeparator() + "\tcrossovers <ticker_symbol> start_date end_date"
            + System.lineSeparator() + "\tmoving-crossovers <ticker_symbol> start_date end_date"
            + System.lineSeparator() + "\tCostBasis <portfolio-name> MM/DD/YYYY"
            + System.lineSeparator() + "\tportfolioValueOnDate <portfolio-name> MM/DD/YYYY"
            + System.lineSeparator() + "\tchart-portfolio <portfolio-name> start_date end_date"
            + System.lineSeparator() + "\tchart-stock <ticker_symbol> start_date end_date"
            + System.lineSeparator() + "\tpurchase <portfolio_name> <ticker_symbol> <date> "
            + "<numShares>"
            + System.lineSeparator() + "\tsell {portfolio_name} {ticker_symbol} {date} {numShares}"
            + System.lineSeparator() + "\tquit");
  }
}
