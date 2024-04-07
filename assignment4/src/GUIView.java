import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * An implementation of GUIViewInterface. An object of this class should be used for all GUI
 * construction and interaction.
 */
public class GUIView extends JFrame implements GUIViewInterface, ItemListener {
  //Drop down menu of options for commands
  private JLabel comboboxDisplay;
  JComboBox<String> combobox;
  //private JLabel portfolioMenuDisplay;
  private JPanel mainPanel;

  //The bottom panel that changes depending on the menu option
  JPanel cards;
  private JList<String> portfoliosInMenu;
  //Buy if true, sell if false
  private boolean isBuy;
  //Tells the user how many days in the moving average they input
  private JLabel xDisplay;
  //For the moving crossovers
  private JLabel xDisplay2;
  private JLabel yDisplay;
  public GUIView(String caption) {
    super(caption);
    setSize(750, 500);
    setLocation(350, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setLayout(new FlowLayout());

    mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

    cards = new JPanel(new CardLayout());

    makeMainMenu();
    makePortfolioMenu();
    bottomPanel();

    setContentPane(mainPanel);
    //pack();
    setVisible(true);
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
        String selectedItem = (String)cb.getSelectedItem();
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

  private void bottomPanel() {
    //Empty pane
    JPanel emptyPane = new JPanel();

    //Create portfolio
    JPanel createPortfolioPane = new JPanel();
    JTextArea textBox1 = new JTextArea(1, 50);
    textBox1.setBorder(BorderFactory.createTitledBorder("Enter name for portfolio"));
    createPortfolioPane.add(textBox1);

    JTextArea textBox2 = new JTextArea(1, 60);
    textBox2.setBorder(BorderFactory.createTitledBorder("Enter the desired stocks and their "
            + "amounts in the following format: MSFT:20 AAPL:10 NVDA:30"));
    createPortfolioPane.add(textBox2);

    //Load portfolio
    JPanel loadPortfolioPane = new JPanel();
    JTextArea textBox3 = new JTextArea(1, 50);
    textBox3.setBorder(BorderFactory.createTitledBorder("Enter a name for the loaded "
            + "portfolio"));
    loadPortfolioPane.add(textBox3);

    JPanel fileopenPanel = new JPanel();
    fileopenPanel.setLayout(new FlowLayout());
    JButton fileOpenButton = new JButton("Select the file with the portfolio data");
    fileOpenButton.setActionCommand("Open file");
    fileOpenButton.addActionListener(new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        JFileChooser fchooser = new JFileChooser(".");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("txt files",
                "txt");
        fchooser.setFileFilter(filter);
        int retvalue = fchooser.showOpenDialog(GUIView.this);
        if (retvalue == JFileChooser.APPROVE_OPTION) {
          File f = fchooser.getSelectedFile();
          //Use this to get the user selected file path: f.getAbsolutePath()
        }
      }
    });
    fileopenPanel.add(fileOpenButton);
    loadPortfolioPane.add(fileopenPanel);

    //Save portfolio
    JPanel savePortfolioPane = new JPanel();
    JLabel text = new JLabel("First select the portfolio to save from the above menu. Then "
            + "specify the file to save to:");
    savePortfolioPane.add(text);

    JPanel filesavePanel = new JPanel();
    filesavePanel.setLayout(new FlowLayout());
    JButton fileSaveButton = new JButton("Open file browser");
    fileSaveButton.setActionCommand("Save file");
    fileSaveButton.addActionListener(new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        JFileChooser fchooser = new JFileChooser(".");
        int retvalue = fchooser.showSaveDialog(GUIView.this);
        if (retvalue == JFileChooser.APPROVE_OPTION) {
          File f = fchooser.getSelectedFile();
          //Use this to get the user selected file path: f.getAbsolutePath()
        }
      }
    });
    filesavePanel.add(fileSaveButton);
    savePortfolioPane.add(filesavePanel);

    //Display composition
    JPanel displayCompositionPane = new JPanel();
    JLabel text2 = new JLabel("Select the portfolio to show the composition of in the above "
            + "menu.");
    displayCompositionPane.add(text2);

    //Cost basis
    JPanel costBasisPane = new JPanel();
    JLabel text3 = new JLabel("Select the portfolio in the above menu, then enter the date");
    JLabel text4 = new JLabel("to calculate the total amount of money invested in the "
            + "portfolio by the given date.");
    costBasisPane.add(text3);
    costBasisPane.add(text4);
    JTextArea textBox6 = new JTextArea(1, 50);
    textBox6.setBorder(BorderFactory.createTitledBorder("Date in MM/DD/YYYY format:"));
    costBasisPane.add(textBox6);

    //Value of a portfolio
    JPanel valuePortfolioPane = new JPanel();
    JLabel text5 = new JLabel("Select the portfolio in the above menu, then enter the date");
    JLabel text6 = new JLabel("to calculate the total value of the portfolio at the end of "
            + "the specified day.");
    valuePortfolioPane.add(text5);
    valuePortfolioPane.add(text6);
    JTextArea textBox7 = new JTextArea(1, 50);
    textBox7.setBorder(BorderFactory.createTitledBorder("Date in MM/DD/YYYY format:"));
    valuePortfolioPane.add(textBox7);

    //Buy/sell stocks
    JPanel buySellPane = new JPanel();
    JPanel radioPanel = new JPanel();
    JRadioButton[] radioButtons = new JRadioButton[2];
    ButtonGroup rGroup = new ButtonGroup();
    radioButtons[0] = new JRadioButton("Buy");
    radioButtons[1] = new JRadioButton("Sell");
    radioButtons[0].setActionCommand("Buy");
    radioButtons[1].setActionCommand("Sell");
    radioButtons[0].addActionListener(new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        isBuy = true;
      }
    });
    radioButtons[1].addActionListener(new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        isBuy = false;
      }
    });
    rGroup.add(radioButtons[0]);
    rGroup.add(radioButtons[1]);
    radioPanel.add(radioButtons[0]);
    radioPanel.add(radioButtons[1]);
    buySellPane.add(radioPanel);

    JLabel text7 = new JLabel("Select the portfolio to buy or sell stocks from in the above "
            + "menu");
    buySellPane.add(text7);

    JTextArea textBox4 = new JTextArea(1, 50);
    textBox4.setBorder(BorderFactory.createTitledBorder("Enter the ticker symbol for the desired "
            + "stock:"));
    buySellPane.add(textBox4);

    JTextArea textBox8 = new JTextArea(1, 50);
    textBox8.setBorder(BorderFactory.createTitledBorder("Enter the date to make the purchase or "
            + "sale on in MM/DD/YYYY format:"));
    buySellPane.add(textBox8);

    JTextArea textBox5 = new JTextArea(1, 50);
    textBox5.setBorder(BorderFactory.createTitledBorder("Enter the number of shares to buy or "
            + "sell. Must be an integer:"));
    buySellPane.add(textBox5);

    //Stock direction day
    JPanel gainLossPane = new JPanel();
    JTextArea textBox9 = new JTextArea(1, 50);
    textBox9.setBorder(BorderFactory.createTitledBorder("Enter the ticker symbol for the stock "
            + "to check:"));
    gainLossPane.add(textBox9);

    JTextArea textBox10 = new JTextArea(1, 50);
    textBox10.setBorder(BorderFactory.createTitledBorder("Enter the date in MM/DD/YYYY format:"));
    gainLossPane.add(textBox10);

    //Stock direction over time
    JPanel gainLossOverTimePane = new JPanel();
    JLabel text8 = new JLabel("Tells you whether the given stock gained or lost over the "
            + "given period of time, from the start date to the end date.");
    gainLossOverTimePane.add(text8);

    JTextArea textBox11 = new JTextArea(1, 50);
    textBox11.setBorder(BorderFactory.createTitledBorder("Enter the ticker symbol for the stock "
            + "to check:"));
    gainLossOverTimePane.add(textBox11);

    JTextArea textBox12 = new JTextArea(1, 50);
    textBox12.setBorder(BorderFactory.createTitledBorder("Enter the start date in MM/DD/YYYY "
            + "format:"));
    gainLossOverTimePane.add(textBox12);

    JTextArea textBox13 = new JTextArea(1, 50);
    textBox13.setBorder(BorderFactory.createTitledBorder("Enter the end date in MM/DD/YYYY "
            + "format:"));
    gainLossOverTimePane.add(textBox13);

    //Moving average
    JPanel movingAveragePane = new JPanel();
    JLabel text9 = new JLabel("Calculates the average price for the given stock in the last "
            + "given number of days, starting from the given date.");
    movingAveragePane.add(text9);

    JTextArea textBox14 = new JTextArea(1, 50);
    textBox14.setBorder(BorderFactory.createTitledBorder("How many days for the moving average?"));
    movingAveragePane.add(textBox14);
    textBox14.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        Document d = e.getDocument();
        try {
          xDisplay.setText("Calculating " + d.getText(0, d.getLength()) + "-day moving average");
        } catch (BadLocationException ex) {
          throw new RuntimeException(ex);
        }
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        Document d = e.getDocument();
        try {
          xDisplay.setText("Calculating " + d.getText(0, d.getLength()) + "-day moving average");
        } catch (BadLocationException ex) {
          throw new RuntimeException(ex);
        }
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        Document d = e.getDocument();
        try {
          xDisplay.setText("Calculating " + d.getText(0, d.getLength()) + "-day moving average");
        } catch (BadLocationException ex) {
          throw new RuntimeException(ex);
        }
      }
    });
    xDisplay = new JLabel("");
    movingAveragePane.add(xDisplay);

    JTextArea textBox15 = new JTextArea(1, 50);
    textBox15.setBorder(BorderFactory.createTitledBorder("Enter the end date in MM/DD/YYYY "
            + "format:"));
    movingAveragePane.add(textBox15);

    JTextArea textBox16 = new JTextArea(1, 50);
    textBox16.setBorder(BorderFactory.createTitledBorder("Enter the ticker symbol for the stock "
            + "to check:"));
    movingAveragePane.add(textBox16);

    //Crossovers
    JPanel crossoversPane = new JPanel();
    JLabel text10 = new JLabel("Finds the positive crossovers and negative crossovers within"
            + " the given time period. ");
    JLabel text14 = new JLabel("A crossover day means that the closing price for the day and"
            + " the closing price for the previous day");
    JLabel text12 = new JLabel(" are on opposite sides of the 30-day moving average.");
    crossoversPane.add(text10);
    crossoversPane.add(text14);
    crossoversPane.add(text12);

    JTextArea textBox17 = new JTextArea(1, 50);
    textBox17.setBorder(BorderFactory.createTitledBorder("Enter the ticker symbol for the stock "
            + "to check:"));
    crossoversPane.add(textBox17);

    JTextArea textBox18 = new JTextArea(1, 50);
    textBox18.setBorder(BorderFactory.createTitledBorder("Enter the start date in MM/DD/YYYY "
            + "format:"));
    crossoversPane.add(textBox18);

    JTextArea textBox19 = new JTextArea(1, 50);
    textBox19.setBorder(BorderFactory.createTitledBorder("Enter the end date in MM/DD/YYYY "
            + "format:"));
    crossoversPane.add(textBox19);

    //Moving crossovers
    JPanel movingCrossoversPane = new JPanel();
    JLabel text11 = new JLabel("Finds the positive crossovers and negative crossovers within"
            + " the given time period. ");
    JLabel text15 = new JLabel("A moving crossover happens when an x-day moving average "
            + "crosses from one side to another side");
    JLabel text13 = new JLabel(" of a y-day moving average, with x lesser than y.");
    movingCrossoversPane.add(text11);
    movingCrossoversPane.add(text15);
    movingCrossoversPane.add(text13);

    JTextArea textBox20 = new JTextArea(1, 50);
    textBox20.setBorder(BorderFactory.createTitledBorder("Enter the ticker symbol for the stock "
            + "to check:"));
    movingCrossoversPane.add(textBox20);

    JTextArea textBox21 = new JTextArea(1, 50);
    textBox21.setBorder(BorderFactory.createTitledBorder("Enter the start date in MM/DD/YYYY "
            + "format:"));
    movingCrossoversPane.add(textBox21);

    JTextArea textBox22 = new JTextArea(1, 50);
    textBox22.setBorder(BorderFactory.createTitledBorder("Enter the end date in MM/DD/YYYY "
            + "format:"));
    movingCrossoversPane.add(textBox22);

    JTextArea textBox23 = new JTextArea(1, 50);
    textBox23.setBorder(BorderFactory.createTitledBorder("Set x, the number of days for the first "
            + "moving average"));
    movingCrossoversPane.add(textBox23);
    textBox23.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        Document d = e.getDocument();
        try {
          xDisplay2.setText("Calculating when the " + d.getText(0, d.getLength()) +
                  "-day moving average crosses the");
        } catch (BadLocationException ex) {
          throw new RuntimeException(ex);
        }
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        Document d = e.getDocument();
        try {
          xDisplay2.setText("Calculating when the " + d.getText(0, d.getLength()) +
                  "-day moving average crosses the");
        } catch (BadLocationException ex) {
          throw new RuntimeException(ex);
        }
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        Document d = e.getDocument();
        try {
          xDisplay2.setText("Calculating when the " + d.getText(0, d.getLength()) +
                  "-day moving average crosses the");
        } catch (BadLocationException ex) {
          throw new RuntimeException(ex);
        }
      }
    });

    JTextArea textBox24 = new JTextArea(1, 50);
    textBox24.setBorder(BorderFactory.createTitledBorder("Set y, the number of days for the second "
            + "moving average"));
    movingCrossoversPane.add(textBox24);
    textBox24.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        Document d = e.getDocument();
        try {
          yDisplay.setText(d.getText(0, d.getLength()) + "-day moving average.");
        } catch (BadLocationException ex) {
          throw new RuntimeException(ex);
        }
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        Document d = e.getDocument();
        try {
          yDisplay.setText(d.getText(0, d.getLength()) + "-day moving average.");
        } catch (BadLocationException ex) {
          throw new RuntimeException(ex);
        }
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        Document d = e.getDocument();
        try {
          yDisplay.setText(d.getText(0, d.getLength()) + "-day moving average.");
        } catch (BadLocationException ex) {
          throw new RuntimeException(ex);
        }
      }
    });
    xDisplay2 = new JLabel("");
    movingCrossoversPane.add(xDisplay2);
    yDisplay = new JLabel("");
    movingCrossoversPane.add(yDisplay);

    cards.add(emptyPane, "---");
    cards.add(createPortfolioPane, "Create a new portfolio");
    cards.add(loadPortfolioPane, "Load portfolio from file");
    cards.add(savePortfolioPane, "Save portfolio to file");
    cards.add(displayCompositionPane, "Display composition of a portfolio");
    cards.add(costBasisPane, "Find cost basis of a portfolio");
    cards.add(valuePortfolioPane, "Find value of a portfolio");
    cards.add(buySellPane, "Buy/sell stocks");
    cards.add(gainLossPane, "Find whether a stock gained or lost over one day");
    cards.add(gainLossOverTimePane, "Find whether a stock gained or lost between two "
            + "dates");
    cards.add(movingAveragePane, "Find the moving average of a stock");
    cards.add(crossoversPane, "Find crossovers for a stock");
    cards.add(movingCrossoversPane, "Find moving crossovers for a stock");

    mainPanel.add(cards);
  }

  @Override
  public void itemStateChanged(ItemEvent evt) {
    CardLayout cl = (CardLayout)(cards.getLayout());
    cl.show(cards, (String)evt.getItem());
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
