import javax.swing.*;

public class CommandGainLoss implements Command {
  @Override
  public JPanel makePanels() {
    JPanel gainLossPane = new JPanel();
    JTextArea textBox9 = new JTextArea(1, 50);
    textBox9.setBorder(BorderFactory.createTitledBorder("Enter the ticker symbol for the stock "
            + "to check:"));
    gainLossPane.add(textBox9);

    JTextArea textBox10 = new JTextArea(1, 50);
    textBox10.setBorder(BorderFactory.createTitledBorder("Enter the date in MM/DD/YYYY format:"));
    gainLossPane.add(textBox10);
    return gainLossPane;
  }

  @Override
  public String getMenuItem() {
    return "Find whether a stock gained or lost over one day";
  }

  @Override
  public void executeCommand() {

  }
}
