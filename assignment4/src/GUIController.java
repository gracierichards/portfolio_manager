import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIController {
  private GUIView view;
  private Model model;
  private FlexiblePortfolioModel fleximodel;
  private Controller controller;

  public GUIController() {
    view = new GUIView("Portfolio Manager");
    model = new Model();
    fleximodel = new FlexiblePortfolioModel();
    controller = new Controller(model, fleximodel, view, null);

    // Add action listeners to GUI components
    //view.addMenuListener(new MenuListener());
    view.addGoButtonListener(new GoButtonListener());
  }

  // ActionListener for menu selection
  /*class MenuListener implements ActionListener {
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
  }*/

  // ActionListener for the "Go" button
  class GoButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      String selectedItem = view.getSelectedMenuItem();
      // Invoke corresponding method in the model based on the selected menu item
      switch (selectedItem) {
        case "Create a new portfolio":
          String portfolioName = view.createCommandPortfolioNameTextbox.getText();
          String tickers = view.tickersAndAmountsTextbox.getText();
          controller.createCommand("flexible", portfolioName, tickers);
          view.dataForPortfoliosInMenu.addElement(portfolioName);
          view.successPopup();
          break;
        case "Load portfolio from file":
          if (!view.loadPathSet) {
            view.errorPopup();
            break;
          }
          portfolioName = view.loadCommandTextBox.getText();
          String loadPath = view.loadPath;
          controller.loadCommand(portfolioName, loadPath);
          view.dataForPortfoliosInMenu.addElement(portfolioName);
          view.successPopup();
          view.loadPathSet = false;
          break;
        case "Save portfolio to file":
          if (!view.savePathSet) {
            view.errorPopup();
            break;
          }
          portfolioName = view.selectedPortfolio;
          String savePath = view.savePath;
          controller.saveCommand(portfolioName, savePath);
          view.successPopup();
          view.savePathSet = false;
          break;
        case "Display composition of a portfolio":
          break;
        case "Find cost basis of a portfolio":
          break;
        case "Find value of a portfolio":
          break;
        case "Buy/sell stocks":
          break;
        case "Find whether a stock gained or lost over one day":
          break;
        case "Find whether a stock gained or lost between two dates":
          break;
        case "Find the moving average of a stock":
          break;
        case "Find crossovers for a stock":
          break;
        case "Find moving crossovers for a stock":
          break;
      }
    }
  }

  /*public static void main(String[] args) {
    new GUIController();

    class GUIController implements ControllerInterface {
      @Override
      public void processCommand(String input) {

      }
    }
  }*/
}