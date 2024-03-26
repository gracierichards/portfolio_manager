import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * PortfolioPerformanceChart class generates a text-based bar chart to visualize the performance of
 * a portfolio over a specified time range.
 */
public class PerformanceChart {

  /**
   * Generates a performance chart for the specified portfolio within the given date range.
   *
   * @param portfolioName The name of the portfolio.
   * @param startDate     The start date of the performance chart in the format "yyyy-MM-dd".
   * @param endDate       The end date of the performance chart in the format "yyyy-MM-dd".
   * @param model         The model containing the portfolio and necessary data.
   * @return A string representing the generated performance chart.
   */
  public static String generatePerformanceChart(String portfolioName, String startDate,
                                                String endDate, Model model) {
    Portfolio portfolio = model.portfolioList.get(portfolioName);
    if (portfolio == null) {
      return "Portfolio not found.";
    }

    StringBuilder chart = new StringBuilder();

    // Calculate performance for each timestamp in the range
    List<String> timestamps = getTimeStampsInRange(startDate, endDate);
    if (timestamps.isEmpty()) {
      return "No timestamps found in the specified range.";
    }

    // Determine the scale for the chart
    float maxValue = 0;
    for (String timestamp : timestamps) {
      float value = model.portfolioValueOnDate(portfolioName, timestamp);
      if (value > maxValue) {
        maxValue = value;
      }
    }
    int scale = Math.max(1, (int) (maxValue / 50));

    // Add title to the chart
    chart.append("Performance of portfolio ").append(portfolioName).append(" from ")
            .append(startDate).append(" to ").append(endDate).append("\n");

    // Add data lines to the chart
    for (String timestamp : timestamps) {
      float value = model.portfolioValueOnDate(portfolioName, timestamp);
      int asterisks = Math.max(1, (int) (value / scale));
      chart.append(timestamp).append(": ");
      for (int i = 0; i < asterisks; i++) {
        chart.append("*");
      }
      chart.append("\n");
    }

    // Add scale to the chart
    chart.append("Scale: * = ").append(scale);

    return chart.toString();
  }

  /**
   * Generates a text-based bar chart to visualize the performance of a stock over a specified
   * time range.
   *
   * @param tickerSymbol The name of the stock.
   * @param startDate     The start time of the time range.
   * @param endDate       The end time of the time range.
   * @return The text-based bar chart as a string.
   */
  public static String generatePerformanceChartStock(String tickerSymbol, String startDate,
                                    String endDate, Model model) throws IllegalArgumentException {
    if (!model.isValidTicker(tickerSymbol)) {
      throw new IllegalArgumentException(tickerSymbol + " is not a valid ticker symbol.");
    }

    StringBuilder chart = new StringBuilder();

    // Calculate performance for each timestamp in the range
    List<String> timestamps = getTimeStampsInRange(startDate, endDate);
    if (timestamps.isEmpty()) {
      return "No timestamps found in the specified range.";
    }

    // Determine the scale for the chart
    float maxValue = 0;
    for (String timestamp : timestamps) {
      float value = model.getStockPrice(tickerSymbol, timestamp, Model.TypeOfPrice.CLOSE);
      if (value > maxValue) {
        maxValue = value;
      }
    }
    int scale = Math.max(1, (int) (maxValue / 50));

    // Add title to the chart
    chart.append("Performance of stock ").append(tickerSymbol).append(" from ")
            .append(startDate).append(" to ").append(endDate).append("\n");

    // Add data lines to the chart
    for (String timestamp : timestamps) {
      float value = model.getStockPrice(tickerSymbol, timestamp, Model.TypeOfPrice.CLOSE);
      int asterisks = Math.max(1, (int) (value / scale));
      chart.append(timestamp).append(": ");
      for (int i = 0; i < asterisks; i++) {
        chart.append("*");
      }
      chart.append("\n");
    }

    // Add scale to the chart
    chart.append("Scale: * = ").append(scale);

    return chart.toString();
  }

  /**
   * Generates timestamps at regular intervals within the specified time span.
   *
   * @param startDate The start date of the time range.
   * @param endDate   The end date of the time range.
   * @return The list of timestamps.
   */
  public static List<String> getTimeStampsInRange(String startDate, String endDate) {
    List<String> timestamps = new ArrayList<>();

    // Parse start and end dates
    LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));

    // Inclusive of end date without this
    // end = end.plusDays(1);

    // Determine interval based on the difference between start and end dates
    long totalDays = java.time.temporal.ChronoUnit.DAYS.between(start, end);
    String timeSpan;
    int interval;
    if (totalDays < 30) {
      timeSpan = "days";
      interval = 1;
    } else if (totalDays < 120) {
      timeSpan = "week";
      interval = 7;
    } else if (totalDays < 900) {
      timeSpan = "months";
      //interval = Math.toIntExact(totalDays / 30); // Approximate number of months
      interval = 1; //because below we use interval to add this many months I think?
    } else if (totalDays < 1490) {
      timeSpan = "2months";
      //interval = 2 * Math.toIntExact(totalDays / 30);
      interval = 2;
    } else {
      timeSpan = "years";
      //interval = Math.toIntExact(totalDays / 365); // Approximate number of years
      interval = 1;
    }

    // Generate timestamps at regular intervals
    LocalDate current = start;
    while (!current.isAfter(end)) {
      timestamps.add(current.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
      switch (timeSpan) {
        case "days":
          current = current.plusDays(interval);
          break;
        case "week":
          current = current.plusDays(interval);
          break;
        case "months":
          current = current.plusMonths(interval);
          break;
        case "2months":
          current = current.plusMonths(interval);
          break;
        case "years":
          current = current.plusYears(interval);
          break;
        default:
          System.out.println("Unexpected time span: " + timeSpan);
          break;
      }
    }

    return timestamps;
  }
}
