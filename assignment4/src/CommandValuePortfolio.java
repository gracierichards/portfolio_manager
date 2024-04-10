import javax.swing.*;

public class CommandValuePortfolio implements Command {

  @Override
  public JPanel makePanels() {
    JPanel valuePortfolioPane = new JPanel();
    JLabel text5 = new JLabel("Select the portfolio in the above menu, then enter the date");
    JLabel text6 = new JLabel("to calculate the total value of the portfolio at the end of "
            + "the specified day.");
    valuePortfolioPane.add(text5);
    valuePortfolioPane.add(text6);
    JTextArea textBox7 = new JTextArea(1, 50);
    textBox7.setBorder(BorderFactory.createTitledBorder("Date in MM/DD/YYYY format:"));
    valuePortfolioPane.add(textBox7);
    return valuePortfolioPane;
  }

  @Override
  public String getMenuItem() {
    return "Find value of a portfolio";
  }

  @Override
  public String executeCommand() {
    return "";
  }
}
