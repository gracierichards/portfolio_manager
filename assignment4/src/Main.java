import java.util.Scanner;

/**
 * The class to start running the program. It instantiates the Model, View, and Controller. It also
 * continuously accepts input from the command line, and passes commands to the controller.
 */
public class Main {
  /**
   * The method that starts running the program. It instantiates the Model, View, and Controller.
   * It also continuously accepts input from the command line, and passes commands to the
   * controller.
   *
   * @param args there is a single argument - the GUI/nonGUI setting. The valid options are "gui"
   *             or "nongui", the default is gui. gui will open the GUI, nongui will use the
   *             command line interface.
   */
  public static void main(String[] args) {
    if (args.length == 0 || args[0].equalsIgnoreCase("gui")) {
      GUIController controller = new GUIController();
    } else if (args[0].equalsIgnoreCase("nongui")) {
      Model model = new Model();
      FlexiblePortfolioModel fleximodel = new FlexiblePortfolioModel();
      View view = new View();
      Scanner s = new Scanner(System.in);
      Controller controller = new Controller(model, fleximodel, view, s);
      while (true) {
        view.printMenu();
        String command = s.nextLine();
        if (command.equals("quit")) {
          break;
        }
        controller.processCommand(command);
      }
    } else {
      System.out.println("Invalid GUI specification.");
    }
  }
}
