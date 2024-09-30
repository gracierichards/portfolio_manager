import java.awt.FlowLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.ListSelectionModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * An implementation of GUIViewInterface. An object of this class should be used for all GUI
 * construction and interaction.
 */
public class GUIView extends JFrame implements GUIViewInterface, ItemListener {
  private JButton goButton;
  private String selectedItem;
  private JPanel mainPanel;

  //The bottom panel that changes depending on the menu option
  private JPanel cards;
  protected DefaultListModel<String> dataForPortfoliosInMenu;
  private JList<String> portfoliosInMenu;
  private String selectedPortfolio;
  //Buy if true, sell if false
  protected boolean isBuy;
  //User input text boxes
  protected JTextArea createCommandPortfolioNameTextbox;
  protected JTextArea tickersAndAmountsTextbox;
  protected JTextArea loadCommandTextBox;
  protected String loadPath;
  protected boolean loadPathSet;
  protected String savePath;
  protected boolean savePathSet;
  protected JTextArea costBasisTextBox;
  protected JTextArea valueCommandTextBox;
  protected JTextArea buySellTickerTextBox;
  protected JTextArea buySellDateTextBox;
  protected JTextArea buySellIntTextBox;
  protected JTextArea gainLossTickerTextBox;
  protected JTextArea gainLossDateTextBox;
  protected JTextArea overTimeTickerTextBox;
  protected JTextArea overTimeStartDateTextBox;
  protected JTextArea overTimeEndDateTextBox;
  protected JTextArea movingAverageXTextBox;
  protected JTextArea movingAverageDateTextBox;
  protected JTextArea movingAverageTickerTextBox;
  protected JTextArea crossoversTickerTextBox;
  protected JTextArea crossoversStartDateTextBox;
  protected JTextArea crossoversEndDateTextBox;
  protected JTextArea movingCrossoversTickerTextBox;
  protected JTextArea movingCrossoversStartDateTextBox;
  protected JTextArea movingCrossoversEndDateTextBox;
  protected JTextArea movingCrossoversXTextBox;
  protected JTextArea movingCrossoversYTextBox;
  protected JTextArea fixedAmountIntTextBox;
  protected JTextArea fixedAmountDateTextBox;
  protected JTextArea fixedAmountTickersTextBox;
  protected JTextArea dollarCostPortfolioNameTextbox;
  protected JTextArea dollarCostIntTextBox;
  protected JTextArea dollarCostStartDateTextBox;
  protected JTextArea dollarCostEndDateTextBox;
  protected JTextArea frequencyStringTextBox;
  protected JTextArea dollarCostTickersTextBox;

  /**
   * Constructs a new GUIView with the specified caption.
   *
   * @param caption The caption to be displayed on the frame.
   */
  public GUIView(String caption) {
    super(caption);
    setSize(750, 600);
    setLocation(350, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setLayout(new FlowLayout());

    mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

    cards = new JPanel(new CardLayout());

    makeMainMenu();
    dataForPortfoliosInMenu = new DefaultListModel<>();
    makePortfolioMenu();
    bottomPanel();

    setContentPane(mainPanel);
    //pack();
    setVisible(true);

    selectedItem = "---";
    loadPathSet = false;
    savePathSet = false;
    isBuy = true;
  }

  private void makeMainMenu() {
    JPanel comboboxPanel = new JPanel();
    //Drop down menu of options for commands
    JLabel comboboxDisplay = new JLabel("Please select one of the following actions" +
            " from the menu.");
    comboboxPanel.add(comboboxDisplay);
    String[] options = {"---", "Create a new portfolio", "Load portfolio from file",
      "Save portfolio to file", "Display composition of a portfolio",
      "Find cost basis of a portfolio", "Find value of a portfolio", "Buy/sell stocks",
      "Find whether a stock gained or lost over one day",
      "Find whether a stock gained or lost between two dates",
      "Find the moving average of a stock", "Find crossovers for a stock",
      "Find moving crossovers for a stock",
      "Invest a fixed amount into an existing portfolio", "Dollar-cost averaging"};
    JComboBox<String> combobox = new JComboBox<String>();
    combobox.setActionCommand("Menu");
    combobox.addActionListener(new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        @SuppressWarnings("unchecked")
        JComboBox<String> cb = (JComboBox<String>) e.getSource();
        
        selectedItem = (String) cb.getSelectedItem();
        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, selectedItem);
        mainPanel.revalidate();
      }
    });
    for (int i = 0; i < options.length; i++) {
      combobox.addItem(options[i]);
    }
    comboboxPanel.add(combobox);
    //setContentPane(comboboxPanel);
    mainPanel.add(comboboxPanel);
    comboboxPanel.add(Box.createRigidArea(new Dimension(0, 50)));
  }

  private void makePortfolioMenu() {
    JPanel selectionListPanel = new JPanel();
    //portfolioMenuDisplay = new JLabel("User Portfolios");
    //selectionListPanel.add(portfolioMenuDisplay);
    selectionListPanel.setBorder(BorderFactory.createTitledBorder("User Portfolios"));
    //selectionListPanel.setAlignmentX(LEFT_ALIGNMENT);
    //selectionListPanel.setLayout(new BoxLayout(selectionListPanel, BoxLayout.X_AXIS));
    //selectionListPanel.setPreferredSize(new Dimension(250, 500));
    portfoliosInMenu = new JList<>(dataForPortfoliosInMenu);
    portfoliosInMenu.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    portfoliosInMenu.addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        selectedPortfolio = portfoliosInMenu.getSelectedValue();
      }
    });
    portfoliosInMenu.setFont(new Font("Arial", Font.PLAIN, 16));
    selectionListPanel.add(portfoliosInMenu);
    //setContentPane(selectionListPanel);
    mainPanel.add(selectionListPanel);
  }

  private void bottomPanel() {
    JPanel emptyPane = new JPanel();
    cards.add(emptyPane, "---");
    ArrayList<Command> commandObjects = new ArrayList<>();
    commandObjects.add(new CommandCreatePortfolio(this));
    commandObjects.add(new CommandLoadPortfolio(this));
    commandObjects.add(new CommandSavePortfolio(this));
    commandObjects.add(new CommandDisplayComposition(this));
    commandObjects.add(new CommandCostBasis(this));
    commandObjects.add(new CommandValuePortfolio(this));
    commandObjects.add(new CommandBuySell(this));
    commandObjects.add(new CommandGainLoss(this));
    commandObjects.add(new CommandGainLossOverTime(this));
    commandObjects.add(new CommandMovingAverage(this));
    commandObjects.add(new CommandCrossovers(this));
    commandObjects.add(new CommandMovingCrossovers(this));
    commandObjects.add(new CommandInvestFixedAmount(this));
    commandObjects.add(new CommandDollarCost(this));

    for (Command c : commandObjects) {
      JPanel createdPanel = c.makePanels();
      cards.add(createdPanel, c.getMenuItem());
    }

    mainPanel.add(cards);

    goButton = new JButton("Go");
    goButton.setActionCommand("Go");
    /*goButton.addActionListener(new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        for (Command c : commandObjects) {
          if (c.getMenuItem().equals(selectedItem)) {
            String result = c.executeCommand();
            JLabel resultJLabel = new JLabel(result);
            mainPanel.add(resultJLabel);
          }
        }
      }
    });*/
    mainPanel.add(goButton);
  }

  @Override
  public void itemStateChanged(ItemEvent evt) {
    CardLayout cl = (CardLayout) (cards.getLayout());
    cl.show(cards, (String) evt.getItem());
  }

  @Override
  public void examineComposition(InflexiblePortfolio p) {
    StringBuilder messageText = new StringBuilder();
    messageText.append("Portfolio: " + p.getName() + System.lineSeparator());
    Map<String, Float> stocks = p.getStocks();
    for (Map.Entry<String, Float> entry : stocks.entrySet()) {
      messageText.append(entry.getKey() + ": " + entry.getValue() + System.lineSeparator());
    }
    showMessage(messageText.toString());
  }

  @Override
  public void displayTotalCostBasis(String portfolioName, float totalCostBasis) {
    showMessage("Total cost basis for portfolio " + portfolioName + ": " + totalCostBasis);
  }

  @Override
  public void displayPortfolioValueOnDate(String portfolioName, String date, float portfolioValue) {
    showMessage("Portfolio value for " + portfolioName + " on " + date + ": " + portfolioValue);
  }

  @Override
  public void showCrossovers(String results) {
    StringBuilder result = new StringBuilder();
    result.append("Positive crossovers:" + System.lineSeparator());
    String positive = results.split(" ")[0];
    String negative = results.split(" ")[1];
    for (String date : positive.split(",")) {
      result.append(date + System.lineSeparator());
    }
    result.append("Negative crossovers:" + System.lineSeparator());
    for (String date : negative.split(",")) {
      result.append(date + System.lineSeparator());
    }
    showMessage(result.toString());
  }

  @Override
  public void showTickerMatches(String csvContents) {
    //This functionality is not required for the GUI.**/
  }

  @Override
  public void displayPortfolioValue(String portfolioName, String date, float value) {
    //This functionality is not required for the GUI.**/
  }

  @Override
  public void printMenu() {
    //This functionality is not required for the GUI.**/
  }

  // ActionListener for the menu selection
  /*public void addMenuListener(ActionListener listener) {
    combobox.addActionListener(listener);
  }*/

  // ActionListener for the "Go" button
  public void addGoButtonListener(ActionListener listener) {
    goButton.addActionListener(listener);
  }

  // Method to get the selected menu item
  public String getSelectedMenuItem() {
    return selectedItem;
  }

  @Override
  public void showMessage(String message) {
    JOptionPane.showMessageDialog(GUIView.this, message, "Success",
            JOptionPane.PLAIN_MESSAGE);
  }

  @Override
  public void errorMessage(String message) {
    JOptionPane.showMessageDialog(GUIView.this, message, "Error",
            JOptionPane.ERROR_MESSAGE);
  }

  public String getSelectedPortfolio() {
    if (selectedPortfolio == null) {
      errorMessage("Portfolio not selected, please try again.");
    }
    return selectedPortfolio;
  }
}
