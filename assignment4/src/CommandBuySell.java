import java.awt.event.ActionEvent;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.AbstractAction;

/**
 * Implementation of the Command interface for handling buy/sell stock commands.
 */
public class CommandBuySell implements Command {
  private GUIView view;

  /**
   * Constructs a CommandBuySell object with the specified GUIView.
   *
   * @param view the GUIView associated with this command
   */
  public CommandBuySell(GUIView view) {
    this.view = view;
  }
  @Override
  public JPanel makePanels() {
    JPanel buySellPane = new JPanel();
    JPanel radioPanel = new JPanel();
    JRadioButton[] radioButtons = new JRadioButton[2];
    ButtonGroup rGroup = new ButtonGroup();
    radioButtons[0] = new JRadioButton("Buy");
    radioButtons[1] = new JRadioButton("Sell");
    radioButtons[0].setActionCommand("Buy");
    radioButtons[1].setActionCommand("Sell");
    radioButtons[0].addActionListener(new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        view.isBuy = true;
      }
    });
    radioButtons[1].addActionListener(new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        view.isBuy = false;
      }
    });
    rGroup.add(radioButtons[0]);
    rGroup.add(radioButtons[1]);
    radioPanel.add(radioButtons[0]);
    radioPanel.add(radioButtons[1]);
    radioButtons[0].doClick();
    buySellPane.add(radioPanel);

    JLabel text7 = new JLabel("Select the portfolio to buy or sell stocks from in the above "
            + "menu");
    buySellPane.add(text7);

    view.buySellTickerTextBox = new JTextArea(1, 50);
    view.buySellTickerTextBox.setBorder(BorderFactory.createTitledBorder("Enter the ticker symbol "
            + "for the desired stock:"));
    buySellPane.add(view.buySellTickerTextBox);

    view.buySellDateTextBox = new JTextArea(1, 50);
    view.buySellDateTextBox.setBorder(BorderFactory.createTitledBorder("Enter the date to make the "
            + "purchase or sale on in MM/DD/YYYY format:"));
    buySellPane.add(view.buySellDateTextBox);

    view.buySellIntTextBox = new JTextArea(1, 50);
    view.buySellIntTextBox.setBorder(BorderFactory.createTitledBorder("Enter the number of shares "
            + "to buy or sell. Must be an integer:"));
    buySellPane.add(view.buySellIntTextBox);
    return buySellPane;
  }

  @Override
  public String getMenuItem() {
    return "Buy/sell stocks";
  }

}
