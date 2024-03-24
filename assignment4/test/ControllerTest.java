import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
    assertEquals("Portfolio created successfully." + System.lineSeparator(),
            outContent.toString());
  }

  @Test
  public void testProcessCommand_LoadPortfolioFromFile() {
    String input = "load portfolio TestPortfolio test_portfolio_from_file.txt";

    controller.processCommand(input);

    assertTrue(model.portfolioList.containsKey("TestPortfolio"));
    assertEquals("Portfolio loaded successfully from file." + System.lineSeparator(),
            outContent.toString());
  }

  @Test
  public void testProcessCommand_SavePortfolioToFile() {
    String input = "save portfolio TestPortfolio test_portfolio.txt";
    model.createPortfolio("TestPortfolio", new String[]{"AAPL", "MSFT"},
            new float[]{10.0f, 20.0f});

    controller.processCommand(input);

    assertEquals("Portfolio saved successfully to file." + System.lineSeparator(),
            outContent.toString());
  }

  @Test
  public void testProcessCommand_ListPortfolio() {
    String input = "list TestPortfolio";
    model.createPortfolio("TestPortfolio", new String[]{"AAPL", "MSFT"},
            new float[]{10.0f, 20.0f});

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
    //assertTrue(outContent.toString().contains("Portfolio value on 01/01/2024:"));
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

  @Test
  public void testProcessCommand_CreatePortfolio_InvalidSyntax() {
    String input = "create portfolio AAPL:10 MSFT:20";
    controller.processCommand(input);
    input = "create portfolio TestPortfolio AAPL:1.5 MSFT:20";
    controller.processCommand(input);

    assertEquals("Please provide a name for your portfolio. The name cannot contain a "
            + "colon.\nCannot purchase a fractional number of shares. Not including stock AAPL in "
            + "the portfolio.\nPortfolio created successfully.\n", outContent.toString());
    assertTrue(model.portfolioList.containsKey("TestPortfolio"));
    controller.processCommand("list TestPortfolio");

    // Check if the output contains the portfolio information
    assertFalse(outContent.toString().contains("AAPL: 1.5"));
    assertTrue(outContent.toString().contains("MSFT: 20"));
  }

  @Test
  public void testProcessCommand_CreatePortfolio_InvalidTicker() {
    String input = "create portfolio TestPortfolio AAAA:10";
    controller.processCommand(input);

    assertEquals("AAAA is not a valid ticker symbol. Not adding to portfolio."
                    + System.lineSeparator() + "Portfolio created successfully."
                    + System.lineSeparator(), outContent.toString());
  }

  @Test
  public void testProcessCommand_StockDirectionDay() {
    String input = "stock-direction-day T 03/15/2024";
    controller.processCommand(input);
    assertEquals("T gained value." + System.lineSeparator(), outContent.toString());
    input = "stock-direction-day VZ 03/14/2024";
    controller.processCommand(input);
    input = "stock-direction-day T 03/09/2024";
    controller.processCommand(input);
    assertEquals("T gained value." + System.lineSeparator() + "VZ lost value."
            + System.lineSeparator() + "T gained value." + System.lineSeparator(),
            outContent.toString());
  }

  @Test
  public void testProcessCommand_StockDirectionOverTime() {
    String input = "stock-direction-over-time T 03/15/2024 03/13/2024";
    controller.processCommand(input);
    input = "stock-direction-over-time T 03/11/2024 03/18/2024";
    controller.processCommand(input);
    input = "stock-direction-over-time VZ 02/26/2024 03/18/2024";
    controller.processCommand(input);
    assertEquals("End date cannot be before the start date." + System.lineSeparator()
                    + "T lost value." + System.lineSeparator() + "VZ gained value."
                    + System.lineSeparator(), outContent.toString());
  }

  @Test
  public void testProcessCommand_MovingAverage() {
    String input = "moving-average 2.5 T 03/15/2024";
    controller.processCommand(input);
    input = "moving-average 3 T 03/15/2024";
    controller.processCommand(input);
    input = "moving-average 4 VZ 03/11/2024";
    controller.processCommand(input);
    assertEquals("Please provide an integer number of days." + System.lineSeparator()
            + "The 3-day moving average is 17.083334" + System.lineSeparator() + "The 4-day moving "
            + "average is 39.775"
            + System.lineSeparator(), outContent.toString());
  }

  @Test
  public void testProcessCommand_Crossovers() {
    float avg1 = model.movingAverage(30, "T", "03/14/2024"); // = 17.094
    float avg2 = model.movingAverage(30, "T", "03/15/2024"); // = 17.061
    float avg3 = model.movingAverage(30, "T", "03/18/2024"); // = 17.043
    String input = "crossovers T 03/14/2024 03/18/2024";
    controller.processCommand(input);
    // 03/18 should be a positive crossover
    assertEquals("Positive crossovers:" + System.lineSeparator() + "None"
            + System.lineSeparator() + "Negative crossovers:" + System.lineSeparator()
            + "3/14/2024" + System.lineSeparator(), outContent.toString());
    input = "crossovers T 02/13/2024 03/13/2024";
    controller.processCommand(input);
    String output = outContent.toString();
  }

  @Test
  public void testProcessCommand_MovingCrossovers() {
    float yavg1 = model.movingAverage(30, "T", "03/14/2024"); // = 17.094
    float yavg2 = model.movingAverage(30, "T", "03/15/2024"); // = 17.061
    float yavg3 = model.movingAverage(30, "T", "03/18/2024"); // = 17.043
    float xavg1 = model.movingAverage(3, "T", "03/14/2024"); // = 17.133
    float xavg2 = model.movingAverage(3, "T", "03/15/2024"); // = 17.083
    float xavg3 = model.movingAverage(3, "T", "03/18/2024"); // = 17.12
    String input = "moving-crossovers T 03/14/2024 03/18/2024" + System.lineSeparator() + "3"
            + System.lineSeparator() + "30";
    controller.processCommand(input);
    String output = outContent.toString();
    //assertEquals("Positive crossovers:" + System.lineSeparator() + "None"
    //        + System.lineSeparator() + "Negative crossovers:" + System.lineSeparator()
    //        + "3/14/2024" + System.lineSeparator(), outContent.toString());
    //input = "crossovers T 02/13/2024 03/13/2024";
    //controller.processCommand(input);
    //output = outContent.toString();
  }
}
