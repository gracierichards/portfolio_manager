import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * An implementation of GUIViewInterface. An object of this class should be used for all GUI
 * construction and interaction.
 */
public class GUIView extends JFrame implements GUIViewInterface {
  //Drop down menu of options for commands
  private JLabel comboboxDisplay;
  JComboBox<String> combobox;
  //private JLabel portfolioMenuDisplay;
  private JPanel mainPanel;
  private JList<String> portfoliosInMenu;
  public GUIView(String caption) {
    super(caption);
    setSize(750, 500);
    setLocation(350, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setLayout(new FlowLayout());

    mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

    makeMainMenu();
    makePortfolioMenu();

    setContentPane(mainPanel);
    //pack();
    setVisible(true);
  }

  @Override
  public void addFeatures(Features features) {
    /*combobox.addActionListener(new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        JComboBox<String> cb = (JComboBox<String>)e.getSource();
        String selectedItem = (String)cb.getSelectedItem();
      }
    });*/
  }

  private void makeMainMenu() {
    JPanel comboboxPanel = new JPanel();
    comboboxDisplay = new JLabel("Please select one of the following actions from the menu.");
    comboboxPanel.add(comboboxDisplay);
    String[] options = {"---", "Create a new portfolio", "Load portfolio from file",
            "Save portfolio to file", "Display composition of a portfolio",
            "Find cost basis of a portfolio", "Find value of a portfolio", "Buy/sell stocks",
            "Find whether a stock gained or lost", "Find the moving average of a stock",
            "Find crossovers for a stock", "Find moving crossovers for a stock"};
    combobox = new JComboBox<String>();
    combobox.setActionCommand("Menu");
    combobox.addActionListener(new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        JComboBox<String> cb = (JComboBox<String>)e.getSource();
        String selectedItem = (String)cb.getSelectedItem();
      }
    });
    for (int i = 0; i < options.length; i++) {
      combobox.addItem(options[i]);
    }
    comboboxPanel.add(combobox);
    //setContentPane(comboboxPanel);
    mainPanel.add(comboboxPanel);
  }

  private void makePortfolioMenu() {
    JPanel selectionListPanel = new JPanel();
    //portfolioMenuDisplay = new JLabel("User Portfolios");
    //selectionListPanel.add(portfolioMenuDisplay);
    selectionListPanel.setBorder(BorderFactory.createTitledBorder("User Portfolios"));
    //selectionListPanel.setAlignmentX(LEFT_ALIGNMENT);
    //selectionListPanel.setLayout(new BoxLayout(selectionListPanel, BoxLayout.X_AXIS));
    //selectionListPanel.setPreferredSize(new Dimension(250, 500));

    DefaultListModel<String> dataForPortfoliosInMenu = new DefaultListModel<>();
    dataForPortfoliosInMenu.addElement("GR Portfolio");
    dataForPortfoliosInMenu.addElement("TG Portfolio");
    dataForPortfoliosInMenu.addElement("Portfolio3");
    portfoliosInMenu = new JList<>(dataForPortfoliosInMenu);
    portfoliosInMenu.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    portfoliosInMenu.addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        //Use portfoliosInMenu.getSelectedValue() which is the selected String
      }
    });
    portfoliosInMenu.setFont(new Font("Arial",Font.PLAIN,16));
    selectionListPanel.add(portfoliosInMenu);
    //setContentPane(selectionListPanel);
    mainPanel.add(selectionListPanel);
  }

  @Override
  public void examineComposition(Portfolio p) {

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
}
