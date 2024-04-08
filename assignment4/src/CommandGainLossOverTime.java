import javax.swing.*;

public class CommandGainLossOverTime implements Command {
  @Override
  public JPanel makePanels() {
    JPanel gainLossOverTimePane = new JPanel();
    JLabel text8 = new JLabel("Tells you whether the given stock gained or lost over the "
            + "given period of time, from the start date to the end date.");
    gainLossOverTimePane.add(text8);

    JTextArea textBox11 = new JTextArea(1, 50);
    textBox11.setBorder(BorderFactory.createTitledBorder("Enter the ticker symbol for the stock "
            + "to check:"));
    gainLossOverTimePane.add(textBox11);

    JTextArea textBox12 = new JTextArea(1, 50);
    textBox12.setBorder(BorderFactory.createTitledBorder("Enter the start date in MM/DD/YYYY "
            + "format:"));
    gainLossOverTimePane.add(textBox12);

    JTextArea textBox13 = new JTextArea(1, 50);
    textBox13.setBorder(BorderFactory.createTitledBorder("Enter the end date in MM/DD/YYYY "
            + "format:"));
    gainLossOverTimePane.add(textBox13);
    return gainLossOverTimePane;
  }

  @Override
  public String getMenuItem() {
    return "Find whether a stock gained or lost between two dates";
  }

  @Override
  public void executeCommand() {

  }
}
