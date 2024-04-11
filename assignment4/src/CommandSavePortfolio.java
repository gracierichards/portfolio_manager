import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.AbstractAction;

/**
 * Implementation of the Command interface for saving a portfolio to a file.
 */
public class CommandSavePortfolio implements Command {
  private GUIView view;

  /**
   * Constructs a CommandSavePortfolio object with the specified GUIView.
   *
   * @param view the GUIView associated with this command
   */
  public CommandSavePortfolio(GUIView view) {
    this.view = view;
  }

  @Override
  public JPanel makePanels() {
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
        int retvalue = fchooser.showSaveDialog(view);
        if (retvalue == JFileChooser.APPROVE_OPTION) {
          File f = fchooser.getSelectedFile();
          view.savePath = f.getAbsolutePath();
          view.savePathSet = true;
        }
      }
    });
    filesavePanel.add(fileSaveButton);
    savePortfolioPane.add(filesavePanel);
    return savePortfolioPane;
  }

  @Override
  public String getMenuItem() {
    return "Save portfolio to file";
  }

}
