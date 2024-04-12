import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;

/**
 * Implementation of the Command interface for finding whether a stock gained or lost over one day.
 */
public class CommandGainLoss implements Command {
  private GUIView view;

  /**
   * Constructs a CommandGainLoss object with the specified GUIView.
   *
   * @param view the GUIView associated with this command
   */
  public CommandGainLoss(GUIView view) {
    this.view = view;
  }

  @Override
  public JPanel makePanels() {
    JPanel gainLossPane = new JPanel();
    view.gainLossTickerTextBox = new JTextArea(1, 50);
    view.gainLossTickerTextBox.setBorder(BorderFactory.createTitledBorder("Enter the ticker symbol"
            + " for the stock to check:"));
    gainLossPane.add(view.gainLossTickerTextBox);

    view.gainLossDateTextBox = new JTextArea(1, 50);
    view.gainLossDateTextBox.setBorder(BorderFactory.createTitledBorder("Enter the date in "
            + "MM/DD/YYYY format:"));
    gainLossPane.add(view.gainLossDateTextBox);
    return gainLossPane;
  }

  @Override
  public String getMenuItem() {
    return "Find whether a stock gained or lost over one day";
  }

}
