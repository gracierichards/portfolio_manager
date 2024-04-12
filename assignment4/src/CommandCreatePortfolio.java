import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;

/**
 * Implementation of the Command interface for creating a new portfolio.
 */
public class CommandCreatePortfolio implements Command {
  private GUIView view;

  /**
   * Creates and returns the JPanel containing components for creating a new portfolio.
   *
   * @return a JPanel containing components for creating a new portfolio
   */
  public CommandCreatePortfolio(GUIView view) {
    this.view = view;
  }

  @Override
  public JPanel makePanels() {
    JPanel createPortfolioPane = new JPanel();
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
