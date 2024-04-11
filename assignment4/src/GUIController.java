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
          view.showMessage("Portfolio created successfully.");
          break;
        case "Load portfolio from file":
          if (!view.loadPathSet) {
            view.errorMessage("File not specified.");
            break;
          }
          portfolioName = view.loadCommandTextBox.getText();
          String loadPath = view.loadPath;
          controller.loadCommand(portfolioName, loadPath);
          view.dataForPortfoliosInMenu.addElement(portfolioName);
          view.showMessage("Portfolio loaded successfully.");
          view.loadPathSet = false;
          break;
        case "Save portfolio to file":
          if (!view.savePathSet) {
            view.errorMessage("File not specified.");
            break;
          }
          portfolioName = view.selectedPortfolio;
          String savePath = view.savePath;
          controller.saveCommand(portfolioName, savePath);
          view.showMessage("Portfolio saved successfully.");
          view.savePathSet = false;
          break;
        case "Display composition of a portfolio":
          portfolioName = view.selectedPortfolio;
          controller.listCommand(portfolioName);
          break;
        case "Find cost basis of a portfolio":
          portfolioName = view.selectedPortfolio;
          String date = view.costBasisTextBox.getText();
          controller.costBasisCommand(portfolioName, date);
          break;
        case "Find value of a portfolio":
          portfolioName = view.selectedPortfolio;
          date = view.valueCommandTextBox.getText();
          controller.portfolioValueOnDate(portfolioName, date);
          break;
        case "Buy/sell stocks":
          portfolioName = view.selectedPortfolio;
          String ticker = view.buySellTickerTextBox.getText();
          date = view.buySellDateTextBox.getText();
          String numShares = view.buySellIntTextBox.getText();
          if (view.isBuy) {
            controller.purchaseCommand(portfolioName, ticker, date, numShares);
            view.showMessage("Shares bought successfully.");
          } else {
            controller.sellCommand(portfolioName, ticker, date, numShares);
            view.showMessage("Shares sold successfully.");
          }
          break;
        case "Find whether a stock gained or lost over one day":
          ticker = view.gainLossTickerTextBox.getText();
          date = view.gainLossDateTextBox.getText();
          controller.stockDirectionDay(ticker, date);
          break;
        case "Find whether a stock gained or lost between two dates":
          ticker = view.overTimeTickerTextBox.getText();
          String startDate = view.overTimeStartDateTextBox.getText();
          String endDate = view.overTimeEndDateTextBox.getText();
          controller.stockDirectionOverTime(ticker, startDate, endDate);
          break;
        case "Find the moving average of a stock":
          String x = view.movingAverageXTextBox.getText();
          String tickerSymbol = view.movingAverageTickerTextBox.getText();
          date = view.movingAverageDateTextBox.getText();
          controller.movingAverageCommand(x, tickerSymbol, date);
          break;
        case "Find crossovers for a stock":
          tickerSymbol = view.crossoversTickerTextBox.getText();
          startDate = view.crossoversStartDateTextBox.getText();
          endDate = view.crossoversEndDateTextBox.getText();
          controller.crossoversCommand(tickerSymbol, startDate, endDate);
          break;
        case "Find moving crossovers for a stock":
          tickerSymbol = view.movingCrossoversTickerTextBox.getText();
          startDate = view.movingCrossoversStartDateTextBox.getText();
          endDate = view.movingCrossoversEndDateTextBox.getText();
          x = view.movingCrossoversXTextBox.getText();
          String y = view.movingCrossoversYTextBox.getText();
          int xInt;
          int yInt;
          try {
            xInt = Integer.parseInt(x);
            yInt = Integer.parseInt(y);
          }
          catch (NumberFormatException e2) {
            view.errorMessage("Number of days must be an integer.");
            break;
          }
          if (xInt >= yInt) {
            view.errorMessage("Invalid values: second amount of days must be greater than the " +
                    "first.");
            break;
          }
          String result = fleximodel.findMovingCrossovers(tickerSymbol, startDate, endDate, xInt,
                  yInt);
          view.showCrossovers(result);
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