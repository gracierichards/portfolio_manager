import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller for the Portfolio Manager GUI.
 */
public class GUIController {
  private GUIView view;
  private FlexiblePortfolioModel fleximodel;
  private Controller controller;

  /**
   * Constructor for GUIController.
   */
  public GUIController() {
    view = new GUIView("Portfolio Manager");
    Model model = new Model();
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
          if (portfolioName.isEmpty()) {
            view.errorMessage("Portfolio name cannot be empty");
            break;
          }
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
          if (portfolioName.isEmpty()) {
            view.errorMessage("Portfolio name cannot be empty");
            break;
          }
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
          portfolioName = view.getSelectedPortfolio();
          String savePath = view.savePath;
          controller.saveCommand(portfolioName, savePath);
          view.showMessage("Portfolio saved successfully.");
          view.savePathSet = false;
          break;
        case "Display composition of a portfolio":
          portfolioName = view.getSelectedPortfolio();
          controller.listCommand(portfolioName);
          break;
        case "Find cost basis of a portfolio":
          portfolioName = view.getSelectedPortfolio();
          String date = view.costBasisTextBox.getText();
          controller.costBasisCommand(portfolioName, date);
          break;
        case "Find value of a portfolio":
          portfolioName = view.getSelectedPortfolio();
          date = view.valueCommandTextBox.getText();
          controller.portfolioValueOnDate(portfolioName, date);
          break;
        case "Buy/sell stocks":
          portfolioName = view.getSelectedPortfolio();
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
          } catch (NumberFormatException e2) {
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
        case "Invest a fixed amount into an existing portfolio":
          portfolioName = view.getSelectedPortfolio();
          String amount = view.fixedAmountIntTextBox.getText();
          date = view.fixedAmountDateTextBox.getText();
          tickers = view.fixedAmountTickersTextBox.getText();
          controller.investFixedAmountCommand(portfolioName, amount, date, tickers);
          //view.showMessage("Transaction successful.");
          break;
        case "Dollar-cost averaging":
          portfolioName = view.getSelectedPortfolio();
          amount = view.dollarCostIntTextBox.getText();
          startDate = view.dollarCostStartDateTextBox.getText();
          endDate = view.dollarCostEndDateTextBox.getText();
          String frequencyString = view.frequencyStringTextBox.getText();
          tickers = view.dollarCostTickersTextBox.getText();
          if (endDate.equals("")) {
            endDate = null;
          }
          controller.dollarCostAveragingCommand(portfolioName, amount, startDate, endDate,
                  frequencyString, tickers);
          view.dataForPortfoliosInMenu.addElement(portfolioName);
          break;
        default:
          System.out.println("Default case in GUIController");
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