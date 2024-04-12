import java.awt.*;

import javax.swing.*;

public class CommandDollarCost implements Command {
  private GUIView view;

  /**
   * Constructs a CommandBuySell object with the specified GUIView.
   *
   * @param view the GUIView associated with this command
   */
  public CommandDollarCost(GUIView view) {
    this.view = view;
  }

  @Override
  public JPanel makePanels() {
    JPanel dollarCostPane = new JPanel();
    view.dollarCostPortfolioNameTextbox = new JTextArea(1, 50);
    view.dollarCostPortfolioNameTextbox.setBorder(BorderFactory
            .createTitledBorder("Enter name for portfolio"));
    dollarCostPane.add(view.dollarCostPortfolioNameTextbox);

    view.dollarCostIntTextBox = new JTextArea(1, 50);
    view.dollarCostIntTextBox.setBorder(BorderFactory.createTitledBorder("Amount to invest in the "
            + "portfolio at regular intervals in dollars:"));
    dollarCostPane.add(view.dollarCostIntTextBox);

    view.dollarCostStartDateTextBox = new JTextArea(1, 50);
    view.dollarCostStartDateTextBox.setBorder(BorderFactory.createTitledBorder("Enter the start date"
            + " for the investing period in MM/DD/YYYY format:"));
    dollarCostPane.add(view.dollarCostStartDateTextBox);

    view.dollarCostEndDateTextBox = new JTextArea(1, 50);
    view.dollarCostEndDateTextBox.setBorder(BorderFactory.createTitledBorder("Enter the end date "
            + "for the investing period in MM/DD/YYYY format:"));
    dollarCostPane.add(view.dollarCostEndDateTextBox);

    view.frequencyStringTextBox = new JTextArea(1, 50);
    view.frequencyStringTextBox.setBorder(BorderFactory.createTitledBorder("Frequency (in number of"
            + " days)"));
    dollarCostPane.add(view.frequencyStringTextBox);

    JLabel text2 = new JLabel("Enter the desired stocks and their weights in the following "
            + "format:");
    dollarCostPane.add(text2);
    JLabel text3 = new JLabel("<ticker_symbol1>:<weight1> <ticker_symbol2>:<weight2> ...   ");
    dollarCostPane.add(text3);
    JLabel text4 = new JLabel("where the weights are numbers between 0 and 100 (representing "
            + "percentages)");
    dollarCostPane.add(text4);
    view.dollarCostTickersTextBox = new JTextArea(1, 60);
    view.dollarCostTickersTextBox.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    dollarCostPane.add(view.dollarCostTickersTextBox);

    return dollarCostPane;
  }

  @Override
  public String getMenuItem() {
    return "Dollar-cost averaging";
  }
}
