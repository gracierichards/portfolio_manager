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
   * @param args not used
   */
  public static void main(String[] args) {
    Model model = new Model();
    View view = new View();
    Controller controller = new Controller(model, view);
    Scanner s = new Scanner(System.in);
    System.out.println("Please specify an action to take. See SETUP-README.txt for available "
            + "commands and usage.");
    while (true) {
      String command = s.nextLine();
      if (command.equals("quit")) {
        break;
      }
      controller.processCommand(command);
    }
  }
}
