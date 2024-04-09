import javax.swing.*;

public class CommandCreatePortfolio implements Command {
  private JTextArea portfolioTextBox;
  private JTextArea tickersTextBox;
  @Override
  public JPanel makePanels() {
    JPanel createPortfolioPane = new JPanel();
    portfolioTextBox = new JTextArea(1, 50);
    portfolioTextBox.setBorder(BorderFactory.createTitledBorder("Enter name for portfolio"));
    createPortfolioPane.add(portfolioTextBox);

    tickersTextBox = new JTextArea(1, 60);
    tickersTextBox.setBorder(BorderFactory.createTitledBorder("Enter the desired stocks and their "
            + "amounts in the following format: MSFT:20 AAPL:10 NVDA:30"));
    createPortfolioPane.add(tickersTextBox);
    return createPortfolioPane;
  }

  @Override
  public String getMenuItem() {
    return "Create a new portfolio";
  }

  @Override
  public String executeCommand() {
    String portfolioName = portfolioTextBox.getText();
    String tickers = tickersTextBox.getText();
    //call model's createPortfolio command

  }
}
