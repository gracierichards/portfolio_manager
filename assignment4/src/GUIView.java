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
  private JPanel mainPanel;
  public GUIView(String caption) {
    super(caption);
    setSize(1000, 750);
    setLocation(500, 500);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //See views module for more info
    this.setLayout(new FlowLayout());

    mainPanel = new JPanel();

    JPanel comboboxPanel = new JPanel();
    mainPanel.add(comboboxPanel);
    comboboxDisplay = new JLabel("Please select one of the following actions from the menu.");
    comboboxPanel.add(comboboxDisplay);
    String[] options = {"Create a new portfolio", "Load portfolio from file", "Save portfolio to "
            + "file"};
    combobox = new JComboBox<String>();
    combobox.setActionCommand("Menu");
    for (int i = 0; i < options.length; i++) {
      combobox.addItem(options[i]);
    }
    comboboxPanel.add(combobox);
  }

  @Override
  public void addFeatures(Features features) {
    combobox.addActionListener(new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        JComboBox<String> cb = (JComboBox<String>)e.getSource();
        String selectedItem = (String)cb.getSelectedItem();
      }
    });
  }
}
