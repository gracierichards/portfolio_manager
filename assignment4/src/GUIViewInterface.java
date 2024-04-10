/**
 * A ViewInterface for all GUI-related functionality. Objects of this type should be used whenever
 * the GUI is involved.
 */
public interface GUIViewInterface extends ViewInterface {
  /**
   * Displays a popup window saying that the action was successful.
   */
  void successPopup();

  /**
   * Displays a popup window saying that there is an error with the user input(s).
   */
  void errorPopup();
}
