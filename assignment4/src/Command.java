import javax.swing.JPanel;

/**
 * There is a Command subclass for each possible command the GUI can perform.
 */
public interface Command {
  /**
   * When this command is selected from the main menu, makePanels will add the corresponding
   * components to the window.
   *
   * @return the JPanel containing all the components created by this method call.
   */
  JPanel makePanels();

  /**
   * Returns the name of the main menu item corresponding to this command.
   *
   * @return the name of the command in the main menu corresponding to this command.
   */
  String getMenuItem();

}
