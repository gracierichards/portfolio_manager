import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;

/**
 * Implementation of the Command interface for finding whether a stock gained or lost over a
 * period of time.
 */
public class CommandGainLossOverTime implements Command {
  private GUIView view;

  /**
   * Constructs a CommandGainLossOverTime object with the specified GUIView.
   *
   * @param view the GUIView associated with this command
   */
  public CommandGainLossOverTime(GUIView view) {
    this.view = view;
  }

  @Override
  public JPanel makePanels() {
    JPanel gainLossOverTimePane = new JPanel();
    JLabel text8 = new JLabel("Tells you whether the given stock gained or lost over the "
            + "given period of time, from the start date to the end date.");
    gainLossOverTimePane.add(text8);

    view.overTimeTickerTextBox = new JTextArea(1, 50);
    view.overTimeTickerTextBox.setBorder(BorderFactory.createTitledBorder("Enter the ticker symbol"
            + " for the stock to check:"));
    gainLossOverTimePane.add(view.overTimeTickerTextBox);

    view.overTimeStartDateTextBox = new JTextArea(1, 50);
    view.overTimeStartDateTextBox.setBorder(BorderFactory.createTitledBorder("Enter the start date"
            + " in MM/DD/YYYY format:"));
    gainLossOverTimePane.add(view.overTimeStartDateTextBox);

    view.overTimeEndDateTextBox = new JTextArea(1, 50);
    view.overTimeEndDateTextBox.setBorder(BorderFactory.createTitledBorder("Enter the end date in "
            + "MM/DD/YYYY format:"));
    gainLossOverTimePane.add(view.overTimeEndDateTextBox);
    return gainLossOverTimePane;
  }

  @Override
  public String getMenuItem() {
    return "Find whether a stock gained or lost between two dates";
  }

}
