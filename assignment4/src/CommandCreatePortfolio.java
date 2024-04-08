import javax.swing.*;

public class CommandCreatePortfolio implements Command {
  @Override
  public JPanel makePanels() {
    JPanel createPortfolioPane = new JPanel();
    JTextArea textBox1 = new JTextArea(1, 50);
    textBox1.setBorder(BorderFactory.createTitledBorder("Enter name for portfolio"));
    createPortfolioPane.add(textBox1);

    JTextArea textBox2 = new JTextArea(1, 60);
    textBox2.setBorder(BorderFactory.createTitledBorder("Enter the desired stocks and their "
            + "amounts in the following format: MSFT:20 AAPL:10 NVDA:30"));
    createPortfolioPane.add(textBox2);
    return createPortfolioPane;
  }

  @Override
  public String getMenuItem() {
    return "Create a new portfolio";
  }

  @Override
  public void executeCommand() {

  }
}
