import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;

/**
 * Implementation of the Command interface for calculating the cost basis of a portfolio.
 */
public class CommandCostBasis implements Command {

  private GUIView view;

  /**
   * Constructs a CommandCostBasis object with the specified GUIView.
   *
   * @param view the GUIView associated with this command
   */
  public CommandCostBasis(GUIView view) {
    this.view = view;
  }

  @Override
  public JPanel makePanels() {
    JPanel costBasisPane = new JPanel();
    JLabel text3 = new JLabel("Select the portfolio in the above menu, then enter the date");
    JLabel text4 = new JLabel("to calculate the total amount of money invested in the "
            + "portfolio by the given date.");
    costBasisPane.add(text3);
    costBasisPane.add(text4);
    view.costBasisTextBox = new JTextArea(1, 50);
    view.costBasisTextBox.setBorder(BorderFactory.createTitledBorder("Date in MM/DD/YYYY format:"));
    costBasisPane.add(view.costBasisTextBox);
    return costBasisPane;
  }

  @Override
  public String getMenuItem() {
    return "Find cost basis of a portfolio";
  }

}
