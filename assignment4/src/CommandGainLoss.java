import javax.swing.*;

public class CommandGainLoss implements Command {
  private GUIView view;

  public CommandGainLoss(GUIView view) {
    this.view = view;
  }
  @Override
  public JPanel makePanels() {
    JPanel gainLossPane = new JPanel();
    view.textBox9 = new JTextArea(1, 50);
    view.textBox9.setBorder(BorderFactory.createTitledBorder("Enter the ticker symbol for the stock "
            + "to check:"));
    gainLossPane.add(view.textBox9);

    view.textBox10 = new JTextArea(1, 50);
    view.textBox10.setBorder(BorderFactory.createTitledBorder("Enter the date in MM/DD/YYYY format:"));
    gainLossPane.add(view.textBox10);
    return gainLossPane;
  }

  @Override
  public String getMenuItem() {
    return "Find whether a stock gained or lost over one day";
  }

}
