import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * Implementation of the Command interface for finding the moving average of a stock.
 */
public class CommandMovingAverage implements Command {
  private GUIView view;

  /**
   * Constructs a CommandMovingAverage object with the specified GUIView.
   *
   * @param view the GUIView associated with this command
   */
  public CommandMovingAverage(GUIView view) {
    this.view = view;
  }

  @Override
  public JPanel makePanels() {
    JPanel movingAveragePane = new JPanel();
    JLabel text9 = new JLabel("Please provide a number of days to calculate the average over,");
    JLabel text10 = new JLabel("the date of the last day in the desired period, and the stock of interest.");
    movingAveragePane.add(text9);
    movingAveragePane.add(text10);

    view.movingAverageXTextBox = new JTextArea(1, 50);
    view.movingAverageXTextBox.setBorder(BorderFactory.createTitledBorder("How many days for the "
            + "moving average?"));
    movingAveragePane.add(view.movingAverageXTextBox);
    JLabel xDisplay = new JLabel("");
    movingAveragePane.add(xDisplay);
    view.movingAverageXTextBox.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        Document d = e.getDocument();
        try {
          xDisplay.setText("Calculating " + d.getText(0, d.getLength()) +
                  "-day moving average");
        } catch (BadLocationException ex) {
          throw new RuntimeException(ex);
        }
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        Document d = e.getDocument();
        try {
          xDisplay.setText("Calculating " + d.getText(0, d.getLength())
                  + "-day moving average");
        } catch (BadLocationException ex) {
          throw new RuntimeException(ex);
        }
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        Document d = e.getDocument();
        try {
          xDisplay.setText("Calculating " + d.getText(0, d.getLength())
                  + "-day moving average");
        } catch (BadLocationException ex) {
          throw new RuntimeException(ex);
        }
      }
    });

    view.movingAverageDateTextBox = new JTextArea(1, 50);
    view.movingAverageDateTextBox.setBorder(BorderFactory.createTitledBorder("Enter the end date " +
            "in MM/DD/YYYY format:"));
    movingAveragePane.add(view.movingAverageDateTextBox);

    view.movingAverageTickerTextBox = new JTextArea(1, 50);
    view.movingAverageTickerTextBox.setBorder(BorderFactory.createTitledBorder("Enter the ticker " +
            "symbol for the stock to check:"));
    movingAveragePane.add(view.movingAverageTickerTextBox);
    return movingAveragePane;
  }

  @Override
  public String getMenuItem() {
    return "Find the moving average of a stock";
  }

}
