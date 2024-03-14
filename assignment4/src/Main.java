import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Model model = new Model();
    View view = new View();
    Controller controller = new Controller(model, view);
    Scanner s = new Scanner(System.in);
    System.out.println("Please specify an action to take. See SETUP-README.txt for available "
            + "commands and usage.");
    while (true) {
      String command = s.nextLine();
      if (command.equals("quit" + System.lineSeparator())) {
        break;
      }
      controller.processCommand(command);
      System.out.println("Waiting for command");
    }
  }
}
