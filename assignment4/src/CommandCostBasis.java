import javax.swing.*;

public class CommandCostBasis implements Command {

  @Override
  public JPanel makePanels() {
    JPanel costBasisPane = new JPanel();
    JLabel text3 = new JLabel("Select the portfolio in the above menu, then enter the date");
    JLabel text4 = new JLabel("to calculate the total amount of money invested in the "
            + "portfolio by the given date.");
    costBasisPane.add(text3);
    costBasisPane.add(text4);
    JTextArea textBox6 = new JTextArea(1, 50);
    textBox6.setBorder(BorderFactory.createTitledBorder("Date in MM/DD/YYYY format:"));
    costBasisPane.add(textBox6);
    return costBasisPane;
  }

  @Override
  public String getMenuItem() {
    return "Find cost basis of a portfolio";
  }

  @Override
  public void executeCommand() {

  }
}
