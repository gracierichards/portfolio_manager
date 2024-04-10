import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * An implementation of GUIViewInterface. An object of this class should be used for all GUI
 * construction and interaction.
 */
public class GUIView extends JFrame implements GUIViewInterface, ItemListener {
  //Drop down menu of options for commands
  private JLabel comboboxDisplay;
  private JComboBox<String> combobox;
  private JButton goButton;
  private String selectedItem;
  private JPanel mainPanel;

  //The bottom panel that changes depending on the menu option
  private JPanel cards;
  protected DefaultListModel<String> dataForPortfoliosInMenu;
  private JList<String> portfoliosInMenu;
  protected String selectedPortfolio;
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
  protected JTextArea textBox4;
  protected JTextArea textBox5;
  protected JTextArea textBox7;
  protected JTextArea textBox8;
  protected JTextArea textBox9;
  protected JTextArea textBox10;
  protected JTextArea textBox11;
  protected JTextArea textBox12;
  protected JTextArea textBox13;
  protected JTextArea textBox14;
  protected JTextArea textBox15;
  protected JTextArea textBox16;
  protected JTextArea textBox17;
  protected JTextArea textBox18;
  protected JTextArea textBox19;
  protected JTextArea textBox20;
  protected JTextArea textBox21;
  protected JTextArea textBox22;
  protected JTextArea textBox23;
  protected JTextArea textBox24;
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
  }

  private void makeMainMenu() {
    JPanel comboboxPanel = new JPanel();
    comboboxDisplay = new JLabel("Please select one of the following actions from the menu.");
    comboboxPanel.add(comboboxDisplay);
    String[] options = {"---", "Create a new portfolio", "Load portfolio from file",
            "Save portfolio to file", "Display composition of a portfolio",
            "Find cost basis of a portfolio", "Find value of a portfolio", "Buy/sell stocks",
            "Find whether a stock gained or lost over one day",
            "Find whether a stock gained or lost between two dates",
            "Find the moving average of a stock", "Find crossovers for a stock",
            "Find moving crossovers for a stock"};
    combobox = new JComboBox<String>();
    combobox.setActionCommand("Menu");
    combobox.addActionListener(new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        JComboBox<String> cb = (JComboBox<String>)e.getSource();
        selectedItem = (String)cb.getSelectedItem();
        CardLayout cl = (CardLayout)(cards.getLayout());
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
    portfoliosInMenu.setFont(new Font("Arial",Font.PLAIN,16));
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
    CardLayout cl = (CardLayout)(cards.getLayout());
    cl.show(cards, (String)evt.getItem());
  }

  @Override
  public void examineComposition(InflexiblePortfolio p) {
    StringBuilder messageText = new StringBuilder();
    messageText.append("Portfolio: " + p.getName() + System.lineSeparator());
    Map<String, Float> stocks = p.getStocks();
    for (Map.Entry<String, Float> entry : stocks.entrySet()) {
      messageText.append(entry.getKey() + ": " + entry.getValue() + System.lineSeparator());
    }
    JOptionPane.showMessageDialog(GUIView.this, messageText.toString(),
            "Portfolio Composition", JOptionPane.PLAIN_MESSAGE);
  }

  @Override
  public void showTickerMatches(String csvContents) {
    /**This functionality is not required for the GUI.**/
  }

  @Override
  public void displayPortfolioValue(String portfolioName, String date, float value) {

  }

  @Override
  public void showCrossovers(String results) {

  }

  @Override
  public void displayTotalCostBasis(String portfolioName, float totalCostBasis) {

  }

  @Override
  public void displayPortfolioValueOnDate(String portfolioName, String date, float portfolioValue) {

  }

  @Override
  public void printMenu() {
    /**This functionality is not required for the GUI.**/
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
  public void successPopup() {
    JOptionPane.showMessageDialog(GUIView.this,
            "Action performed successfully.", "Success", JOptionPane.PLAIN_MESSAGE);
  }

  @Override
  public void errorPopup() {
    JOptionPane.showMessageDialog(GUIView.this, "There is an error in the "
            + "user input(s).", "Cannot Continue", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void invalidPortfolioNameMessage() {
    JOptionPane.showMessageDialog(GUIView.this, "Invalid portfolio name "
            + "given.", "Please try again", JOptionPane.ERROR_MESSAGE);
  }
}
