import javax.swing.*;

public class CommandGainLossOverTime implements Command {
  private GUIView view;

  public CommandGainLossOverTime(GUIView view) {
    this.view = view;
  }
  @Override
  public JPanel makePanels() {
    JPanel gainLossOverTimePane = new JPanel();
    JLabel text8 = new JLabel("Tells you whether the given stock gained or lost over the "
            + "given period of time, from the start date to the end date.");
    gainLossOverTimePane.add(text8);

    view.textBox11 = new JTextArea(1, 50);
    view.textBox11.setBorder(BorderFactory.createTitledBorder("Enter the ticker symbol for the stock "
            + "to check:"));
    gainLossOverTimePane.add(view.textBox11);

    view.textBox12 = new JTextArea(1, 50);
    view.textBox12.setBorder(BorderFactory.createTitledBorder("Enter the start date in MM/DD/YYYY "
            + "format:"));
    gainLossOverTimePane.add(view.textBox12);

    view.textBox13 = new JTextArea(1, 50);
    view.textBox13.setBorder(BorderFactory.createTitledBorder("Enter the end date in MM/DD/YYYY "
            + "format:"));
    gainLossOverTimePane.add(view.textBox13);
    return gainLossOverTimePane;
  }

  @Override
  public String getMenuItem() {
    return "Find whether a stock gained or lost between two dates";
  }

}
