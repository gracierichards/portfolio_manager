import java.awt.event.ActionEvent;

import javax.swing.*;

public class CommandBuySell implements Command {
  private GUIView view;

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
    buySellPane.add(radioPanel);

    JLabel text7 = new JLabel("Select the portfolio to buy or sell stocks from in the above "
            + "menu");
    buySellPane.add(text7);

    view.textBox4 = new JTextArea(1, 50);
    view.textBox4.setBorder(BorderFactory.createTitledBorder("Enter the ticker symbol for the desired "
            + "stock:"));
    buySellPane.add(view.textBox4);

    view.textBox8 = new JTextArea(1, 50);
    view.textBox8.setBorder(BorderFactory.createTitledBorder("Enter the date to make the purchase or "
            + "sale on in MM/DD/YYYY format:"));
    buySellPane.add(view.textBox8);

    view.textBox5 = new JTextArea(1, 50);
    view.textBox5.setBorder(BorderFactory.createTitledBorder("Enter the number of shares to buy or "
            + "sell. Must be an integer:"));
    buySellPane.add(view.textBox5);
    return buySellPane;
  }

  @Override
  public String getMenuItem() {
    return "Buy/sell stocks";
  }

}
