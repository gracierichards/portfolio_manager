import javax.swing.*;

public class CommandDisplayComposition implements Command {
  @Override
  public JPanel makePanels() {
    JPanel displayCompositionPane = new JPanel();
    JLabel text2 = new JLabel("Select the portfolio to show the composition of in the above "
            + "menu.");
    displayCompositionPane.add(text2);
    return displayCompositionPane;
  }

  @Override
  public String getMenuItem() {
    return "Display composition of a portfolio";
  }

  @Override
  public String executeCommand() {
    return "";
  }
}
