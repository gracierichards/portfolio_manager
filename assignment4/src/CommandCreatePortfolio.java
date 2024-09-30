import javax.swing.*;

/**
 * Implementation of the Command interface for creating a new portfolio.
 */
public class CommandCreatePortfolio implements Command {
  private GUIView view;

  /**
   * Creates and returns the JPanel containing components for creating a new portfolio.
   */
  public CommandCreatePortfolio(GUIView view) {
    this.view = view;
  }

  @Override
  public JPanel makePanels() {
    JPanel createPortfolioPane = new JPanel();
    JLabel text = new JLabel("Creates a portfolio and buys the specified stocks on the current day. If you would like to");
    JLabel text2 = new JLabel("make a purchase on a previous date, create an empty portfolio here and then go to the buy/sell stocks tab.");
    createPortfolioPane.add(text);
    createPortfolioPane.add(text2);

    view.createCommandPortfolioNameTextbox = new JTextArea(1, 50);
    view.createCommandPortfolioNameTextbox.setBorder(BorderFactory
            .createTitledBorder("Enter name for portfolio"));
    createPortfolioPane.add(view.createCommandPortfolioNameTextbox);

    view.tickersAndAmountsTextbox = new JTextArea(1, 60);
    view.tickersAndAmountsTextbox.setBorder(BorderFactory.createTitledBorder("Enter the desired " +
            "stocks and their amounts in the following format: MSFT:20 AAPL:10 NVDA:30"));
    createPortfolioPane.add(view.tickersAndAmountsTextbox);
    return createPortfolioPane;
  }

  @Override
  public String getMenuItem() {
    return "Create a new portfolio";
  }

}
