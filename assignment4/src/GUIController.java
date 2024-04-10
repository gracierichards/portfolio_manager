import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIController {
  private GUIView view;
  private FlexiblePortfolioModel model;

  public GUIController() {
    view = new GUIView("Portfolio Manager");
    model = new FlexiblePortfolioModel();

    // Add action listeners to GUI components
    view.addMenuListener(new MenuListener());
    view.addGoButtonListener(new GoButtonListener());
  }

  // ActionListener for menu selection
  class MenuListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      String selectedItem = view.getSelectedMenuItem();
      // Invoke corresponding method in the model based on the selected menu item
      switch (selectedItem) {
        case "Create a new portfolio":
          // Code to create a new portfolio
          break;
        case "Load portfolio from file":
          // Code to load portfolio from file
          break;
        // Handle other menu items similarly
      }
    }
  }

  // ActionListener for the "Go" button
  class GoButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      String selectedItem = view.getSelectedMenuItem();
      // Invoke corresponding method in the model based on the selected menu item
      switch (selectedItem) {
        case "Buy/sell stocks":
          // Code to buy or sell stocks
          break;
        // Handle other menu items similarly
      }
    }
  }

  public static void main(String[] args) {
    new GUIController();
  }
}
