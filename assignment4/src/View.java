import java.util.Map;

public class View implements ViewInterface{

  @Override
  public void examineComposition(Portfolio p) {
    System.out.println("Portfolio: " + p.getName());
    Map<String, Integer> stocks = p.getStocks();
    for (Map.Entry<String, Integer> entry : stocks.entrySet()) {
      System.out.println(entry.getKey() + ": " + entry.getValue());
    }
  }

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
}
