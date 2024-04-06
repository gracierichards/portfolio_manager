import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;

/**
 * An implementation of GUIViewInterface. An object of this class should be used for all GUI
 * construction and interaction.
 */
public class GUIView extends JFrame implements GUIViewInterface {
  //Drop down menu of options for commands
  private JLabel comboboxDisplay;
  JComboBox<String> combobox;
  //private JPanel mainPanel;
  public GUIView(String caption) {
    super(caption);
    setSize(750, 500);
    setLocation(350, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setLayout(new FlowLayout());

    //mainPanel = new JPanel();

    JPanel comboboxPanel = new JPanel();
    //mainPanel.add(comboboxPanel);
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
    setContentPane(comboboxPanel);

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

  @Override
  public void examineComposition(Portfolio p) {

  }

  @Override
  public void showTickerMatches(String csvContents) {

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

  }
}
