import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DollarCostAveragingStrategy {
  private String portfolioName;
  private Map<String, Float> stocksWithWeights;
  private float investmentAmount;
  private int frequencyDays;
  private LocalDate startDate;
  private LocalDate endDate;

  public DollarCostAveragingStrategy(String portfolioName, Map<String, Float> stocksWithWeights,
                                     float investmentAmount, int frequencyDays,
                                     LocalDate startDate, LocalDate endDate) {
    this.portfolioName = portfolioName;
    this.stocksWithWeights = stocksWithWeights;
    this.investmentAmount = investmentAmount;
    this.frequencyDays = frequencyDays;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public String getPortfolioName() {
    return portfolioName;
  }

  public Map<String, Float> getStocksWithWeights() {
    return stocksWithWeights;
  }

  public float getInvestmentAmount() {
    return investmentAmount;
  }

  public int getFrequencyDays() {
    return frequencyDays;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

}
