import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;

/**
 * Implementation of the Command interface for finding crossovers for a stock.
 */
public class CommandCrossovers implements Command {
  private GUIView view;

  /**
   * Constructs a CommandCrossovers object with the specified GUIView.
   *
   * @param view the GUIView associated with this command
   */
  public CommandCrossovers(GUIView view) {
    this.view = view;
  }

  @Override
  public JPanel makePanels() {
    JPanel crossoversPane = new JPanel();
    JLabel text10 = new JLabel("Finds the positive crossovers and negative crossovers within"
            + " the given time period. ");
    JLabel text14 = new JLabel("A crossover day means that the closing price for the day and"
            + " the closing price for the previous day");
    JLabel text12 = new JLabel(" are on opposite sides of the 30-day moving average.");
    crossoversPane.add(text10);
    crossoversPane.add(text14);
    crossoversPane.add(text12);

    view.crossoversTickerTextBox = new JTextArea(1, 50);
    view.crossoversTickerTextBox.setBorder(BorderFactory.createTitledBorder("Enter the ticker "
            + "symbol for the stock to check:"));
    crossoversPane.add(view.crossoversTickerTextBox);

    view.crossoversStartDateTextBox = new JTextArea(1, 50);
    view.crossoversStartDateTextBox.setBorder(BorderFactory.createTitledBorder("Enter the start "
            + "date in MM/DD/YYYY format:"));
    crossoversPane.add(view.crossoversStartDateTextBox);

    view.crossoversEndDateTextBox = new JTextArea(1, 50);
    view.crossoversEndDateTextBox.setBorder(BorderFactory.createTitledBorder("Enter the end date "
            + "in MM/DD/YYYY format:"));
    crossoversPane.add(view.crossoversEndDateTextBox);
    return crossoversPane;
  }

  @Override
  public String getMenuItem() {
    return "Find crossovers for a stock";
  }

}
