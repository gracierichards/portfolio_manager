import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * Implementation of the Command interface for finding moving crossovers for a stock.
 */
public class CommandMovingCrossovers implements Command {
  private GUIView view;

  /**
   * Constructs a CommandMovingCrossovers object with the specified GUIView.
   *
   * @param view the GUIView associated with this command
   */
  public CommandMovingCrossovers(GUIView view) {
    this.view = view;
  }

  @Override
  public JPanel makePanels() {
    JPanel movingCrossoversPane = new JPanel();
    JLabel text11 = new JLabel("Finds the positive crossovers and negative crossovers within"
            + " the given time period. ");
    JLabel text15 = new JLabel("A moving crossover happens when an x-day moving average "
            + "crosses from one side to another side");
    JLabel text13 = new JLabel(" of a y-day moving average, with x lesser than y.");
    movingCrossoversPane.add(text11);
    movingCrossoversPane.add(text15);
    movingCrossoversPane.add(text13);

    view.movingCrossoversTickerTextBox = new JTextArea(1, 50);
    view.movingCrossoversTickerTextBox.setBorder(BorderFactory.createTitledBorder("Enter the ticker"
            + " symbol for the stock to check:"));
    movingCrossoversPane.add(view.movingCrossoversTickerTextBox);

    view.movingCrossoversStartDateTextBox = new JTextArea(1, 50);
    view.movingCrossoversStartDateTextBox.setBorder(BorderFactory.createTitledBorder("Enter the "
            + "start date in MM/DD/YYYY format:"));
    movingCrossoversPane.add(view.movingCrossoversStartDateTextBox);

    view.movingCrossoversEndDateTextBox = new JTextArea(1, 50);
    view.movingCrossoversEndDateTextBox.setBorder(BorderFactory.createTitledBorder("Enter the end "
            + "date in MM/DD/YYYY format:"));
    movingCrossoversPane.add(view.movingCrossoversEndDateTextBox);

    view.movingCrossoversXTextBox = new JTextArea(1, 50);
    view.movingCrossoversXTextBox.setBorder(BorderFactory.createTitledBorder("Set x, the number of days for " +
            "the first moving average"));
    movingCrossoversPane.add(view.movingCrossoversXTextBox);
    JLabel xDisplay2 = new JLabel("");
    view.movingCrossoversXTextBox.getDocument().addDocumentListener(new DocumentListener() {
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

    view.movingCrossoversYTextBox = new JTextArea(1, 50);
    view.movingCrossoversYTextBox.setBorder(BorderFactory.createTitledBorder("Set y, the number of days for the "
            + "second moving average"));
    movingCrossoversPane.add(view.movingCrossoversYTextBox);
    JLabel yDisplay = new JLabel("");
    view.movingCrossoversYTextBox.getDocument().addDocumentListener(new DocumentListener() {
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

    movingCrossoversPane.add(xDisplay2);
    movingCrossoversPane.add(yDisplay);
    return movingCrossoversPane;
  }

  @Override
  public String getMenuItem() {
    return "Find moving crossovers for a stock";
  }

}
