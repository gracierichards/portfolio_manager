import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class CommandMovingAverage implements Command {
  @Override
  public JPanel makePanels() {
    JPanel movingAveragePane = new JPanel();
    JLabel text9 = new JLabel("Calculates the average price for the given stock in the last "
            + "given number of days, starting from the given date.");
    movingAveragePane.add(text9);

    JTextArea textBox14 = new JTextArea(1, 50);
    textBox14.setBorder(BorderFactory.createTitledBorder("How many days for the moving average?"));
    movingAveragePane.add(textBox14);
    JLabel xDisplay = new JLabel("");
    movingAveragePane.add(xDisplay);
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

    JTextArea textBox15 = new JTextArea(1, 50);
    textBox15.setBorder(BorderFactory.createTitledBorder("Enter the end date in MM/DD/YYYY "
            + "format:"));
    movingAveragePane.add(textBox15);

    JTextArea textBox16 = new JTextArea(1, 50);
    textBox16.setBorder(BorderFactory.createTitledBorder("Enter the ticker symbol for the stock "
            + "to check:"));
    movingAveragePane.add(textBox16);
    return movingAveragePane;
  }

  @Override
  public String getMenuItem() {
    return "Find the moving average of a stock";
  }

  @Override
  public String executeCommand() {

  }
}
