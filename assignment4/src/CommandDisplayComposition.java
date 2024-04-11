import javax.swing.JPanel;
import javax.swing.JLabel;

/**
 * Implementation of the Command interface for displaying the composition of a portfolio.
 */
public class CommandDisplayComposition implements Command {
  private GUIView view;

  /**
   * Constructs a CommandDisplayComposition object with the specified GUIView.
   *
   * @param view the GUIView associated with this command
   */
  public CommandDisplayComposition(GUIView view) {
    this.view = view;
  }
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

}
