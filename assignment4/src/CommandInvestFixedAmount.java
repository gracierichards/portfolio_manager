import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;

/**
 * Implementation of the Command interface for displaying the fixed amount investment made in a
 * portfolio.
 */
public class CommandInvestFixedAmount implements Command {
  private GUIView view;

  /**
   * Constructs a CommandBuySell object with the specified GUIView.
   *
   * @param view the GUIView associated with this command
   */
  public CommandInvestFixedAmount(GUIView view) {
    this.view = view;
  }

  @Override
  public JPanel makePanels() {
    JPanel investFixedAmountPane = new JPanel();
    JLabel text = new JLabel("Select the portfolio to invest in from the above menu.");
    investFixedAmountPane.add(text);

    view.fixedAmountIntTextBox = new JTextArea(1, 50);
    view.fixedAmountIntTextBox.setBorder(BorderFactory.createTitledBorder("Total amount in dollars"
            + " to invest in the portfolio:"));
    investFixedAmountPane.add(view.fixedAmountIntTextBox);

    view.fixedAmountDateTextBox = new JTextArea(1, 50);
    view.fixedAmountDateTextBox.setBorder(BorderFactory.createTitledBorder("Enter the date to make "
            + "the purchases on in MM/DD/YYYY format:"));
    investFixedAmountPane.add(view.fixedAmountDateTextBox);

    JLabel text2 = new JLabel("Enter the desired stocks and their weights in the following "
            + "format:");
    investFixedAmountPane.add(text2);
    JLabel text3 = new JLabel("<ticker_symbol1>:<weight1> <ticker_symbol2>:<weight2> ...   ");
    investFixedAmountPane.add(text3);
    JLabel text4 = new JLabel("where the weights are numbers between 0 and 100 (representing "
            + "percentages)");
    investFixedAmountPane.add(text4);
    view.fixedAmountTickersTextBox = new JTextArea(1, 60);
    view.fixedAmountTickersTextBox.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    investFixedAmountPane.add(view.fixedAmountTickersTextBox);

    return investFixedAmountPane;
  }

  @Override
  public String getMenuItem() {
    return "Invest a fixed amount into an existing portfolio";
  }
}
