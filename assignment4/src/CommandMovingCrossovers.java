import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class CommandMovingCrossovers implements Command {
  private GUIView view;

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

    view.textBox20 = new JTextArea(1, 50);
    view.textBox20.setBorder(BorderFactory.createTitledBorder("Enter the ticker symbol for the stock "
            + "to check:"));
    movingCrossoversPane.add(view.textBox20);

    view.textBox21 = new JTextArea(1, 50);
    view.textBox21.setBorder(BorderFactory.createTitledBorder("Enter the start date in MM/DD/YYYY "
            + "format:"));
    movingCrossoversPane.add(view.textBox21);

    view.textBox22 = new JTextArea(1, 50);
    view.textBox22.setBorder(BorderFactory.createTitledBorder("Enter the end date in MM/DD/YYYY "
            + "format:"));
    movingCrossoversPane.add(view.textBox22);

    view.textBox23 = new JTextArea(1, 50);
    view.textBox23.setBorder(BorderFactory.createTitledBorder("Set x, the number of days for the first "
            + "moving average"));
    movingCrossoversPane.add(view.textBox23);
    JLabel xDisplay2 = new JLabel("");
    view.textBox23.getDocument().addDocumentListener(new DocumentListener() {
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

    view.textBox24 = new JTextArea(1, 50);
    view.textBox24.setBorder(BorderFactory.createTitledBorder("Set y, the number of days for the second "
            + "moving average"));
    movingCrossoversPane.add(view.textBox24);
    JLabel yDisplay = new JLabel("");
    view.textBox24.getDocument().addDocumentListener(new DocumentListener() {
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
