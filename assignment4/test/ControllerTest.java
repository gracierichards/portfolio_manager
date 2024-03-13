import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ControllerTest {

  private Model model;
  private View view;
  private Controller controller;
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;

  @Before
  public void setUp() {
    model = new Model();
    view = new View();
    controller = new Controller(model, view);
    System.setOut(new PrintStream(outContent));
  }

  @Test
  public void testProcessCommand_CreatePortfolio() {
    String input = "create portfolio TestPortfolio AAPL:10 MSFT:20";

    controller.processCommand(input);

    assertTrue(model.portfolioList.containsKey("TestPortfolio"));
    assertEquals("Portfolio created successfully.\r\n", outContent.toString());
  }

  @Test
  public void testProcessCommand_LoadPortfolioFromFile() {
    String input = "load portfolio TestPortfolio test_portfolio.txt";

    controller.processCommand(input);

    assertTrue(model.portfolioList.containsKey("TestPortfolio"));
    assertEquals("Portfolio loaded successfully from file.\n", outContent.toString());
  }

  @Test
  public void testProcessCommand_SavePortfolioToFile() {
    String input = "save portfolio TestPortfolio test_portfolio.txt";
    model.createPortfolio("TestPortfolio", new String[]{"AAPL", "MSFT"}, new float[]{10.0f, 20.0f});

    controller.processCommand(input);

    assertEquals("Portfolio saved successfully to file.\r\n", outContent.toString());
  }

  @Test
  public void testProcessCommand_ListPortfolio() {
    String input = "list TestPortfolio";
    model.createPortfolio("TestPortfolio", new String[]{"AAPL", "MSFT"}, new float[]{10.0f, 20.0f});

    controller.processCommand(input);

    // Check if the output contains the portfolio information
    assertTrue(outContent.toString().contains("AAPL: 10"));
    assertTrue(outContent.toString().contains("MSFT: 20"));
  }

  @Test
  public void testProcessCommand_ValuePortfolio() {
    String input = "value TestPortfolio 01/01/2024";
    model.createPortfolio("TestPortfolio", new String[]{"AAPL", "MSFT"}, new float[]{10.0f, 20.0f});

    controller.processCommand(input);

    // Check if the output contains the portfolio value
    assertTrue(outContent.toString().contains("Portfolio value on 01/01/2024:"));
  }

  @Test
  public void testProcessCommand_Search() {
    String input = "search AAPL";

    controller.processCommand(input);

    // Check if the output contains the matching stocks
    assertTrue(outContent.toString().contains("AAPL"));
  }

  @After
  public void restoreStreams() {
    System.setOut(originalOut);
  }
}
