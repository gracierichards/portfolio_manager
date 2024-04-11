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
    JLabel text9 = new JLabel("Calculates the average price for the given stock in the last "
            + "given number of days, starting from the given date.");
    movingAveragePane.add(text9);

    view.textBox14 = new JTextArea(1, 50);
    view.textBox14.setBorder(BorderFactory.createTitledBorder("How many days for the moving " +
            "average?"));
    movingAveragePane.add(view.textBox14);
    JLabel xDisplay = new JLabel("");
    movingAveragePane.add(xDisplay);
    view.textBox14.getDocument().addDocumentListener(new DocumentListener() {
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

    view.textBox15 = new JTextArea(1, 50);
    view.textBox15.setBorder(BorderFactory.createTitledBorder("Enter the end date in MM/DD/YYYY "
            + "format:"));
    movingAveragePane.add(view.textBox15);

    view.textBox16 = new JTextArea(1, 50);
    view.textBox16.setBorder(BorderFactory.createTitledBorder("Enter the ticker symbol for the" +
            "stock to check:"));
    movingAveragePane.add(view.textBox16);
    return movingAveragePane;
  }

  @Override
  public String getMenuItem() {
    return "Find the moving average of a stock";
  }

}
