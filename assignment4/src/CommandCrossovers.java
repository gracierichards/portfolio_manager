import javax.swing.*;

public class CommandCrossovers implements Command {
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

    JTextArea textBox17 = new JTextArea(1, 50);
    textBox17.setBorder(BorderFactory.createTitledBorder("Enter the ticker symbol for the stock "
            + "to check:"));
    crossoversPane.add(textBox17);

    JTextArea textBox18 = new JTextArea(1, 50);
    textBox18.setBorder(BorderFactory.createTitledBorder("Enter the start date in MM/DD/YYYY "
            + "format:"));
    crossoversPane.add(textBox18);

    JTextArea textBox19 = new JTextArea(1, 50);
    textBox19.setBorder(BorderFactory.createTitledBorder("Enter the end date in MM/DD/YYYY "
            + "format:"));
    crossoversPane.add(textBox19);
    return crossoversPane;
  }

  @Override
  public String getMenuItem() {
    return "Find crossovers for a stock";
  }

  @Override
  public void executeCommand() {

  }
}
