import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CommandLoadPortfolio implements Command {
  private GUIView view;

  public CommandLoadPortfolio(GUIView view) {
    this.view = view;
  }

  @Override
  public JPanel makePanels() {
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
        int retvalue = fchooser.showOpenDialog(view);
        if (retvalue == JFileChooser.APPROVE_OPTION) {
          File f = fchooser.getSelectedFile();
          //Use this to get the user selected file path: f.getAbsolutePath()
        }
      }
    });
    fileopenPanel.add(fileOpenButton);
    loadPortfolioPane.add(fileopenPanel);
    return loadPortfolioPane;
  }

  @Override
  public String getMenuItem() {
    return "Load portfolio from file";
  }

  @Override
  public void executeCommand() {

  }
}
