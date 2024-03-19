import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class ModelTest {

  private Model model;
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;

  @Before
  public void setUp() {
    model = new Model();
    System.setOut(new PrintStream(outContent));
  }

  @Test
  public void testCreatePortfolio() throws FileNotFoundException {
    String portfolioName = "TestPortfolio";
    String[] tickerSymbols = {"AAPL", "MSFT"};
    float[] stockAmounts = {10.0f, 20.0f};

    model.createPortfolio(portfolioName, tickerSymbols, stockAmounts);

    assertNotNull(model.getPortfolio(portfolioName));
  }

  @Test
  public void testSavePortfolioToFile() {
    String portfolioName = "TestPortfolio";
    Portfolio portfolio = new Portfolio(portfolioName);
    portfolio.addStock("AAPL", 10);
    portfolio.addStock("MSFT", 20);
    model.portfolioList.put(portfolioName, portfolio);
    String filename = "test_portfolio.txt";

    model.savePortfolioToFile(portfolioName, filename);

    File file = new File(filename);
    assertTrue(file.exists());
    try (Scanner scanner = new Scanner(file)) {
      String line = scanner.nextLine();
      assertEquals("Portfolio Name: TestPortfolio", line);
    } catch (FileNotFoundException e) {
      fail("File not found: " + e.getMessage());
    }
  }

  @Test
  public void testCreatePortfolioFromFile() throws FileNotFoundException {
    String filename = "test_portfolio_from_file.txt";
    try (PrintWriter writer = new PrintWriter(filename)) {
      writer.println("AAPL:10");
      writer.println("MSFT:20");
    } catch (FileNotFoundException e) {
      fail("File not found: " + e.getMessage());
    }

    model.createPortfolioFromFile("TestPortfolio", filename);

    assertNotNull(model.getPortfolio("TestPortfolio"));
  }

  @Test
  public void testDetermineValue() {
    String portfolioName = "TestPortfolio";
    Portfolio portfolio = new Portfolio(portfolioName);
    portfolio.addStock("AAPL", 10);
    portfolio.addStock("MSFT", 20);
    model.portfolioList.put(portfolioName, portfolio);
    // Assume the data is available in stockcsvs folder
    String date = "01/01/2024";

    float value = model.determineValue(portfolioName, date);

    assertTrue(value > 0.0f);
  }

  @Test
  public void testDetermineValue2() {
    String portfolioName = "TestPortfolio";
    Portfolio portfolio = new Portfolio(portfolioName);
    portfolio.addStock("AAPL", 1);
    model.portfolioList.put(portfolioName, portfolio);
    // Assume the data is available in stockcsvs folder
    String date = "01/01/2024";

    float value = model.determineValue(portfolioName, date);
    assertEquals(193.9000, value, 0.001);
  }

  @After
  public void restoreStreams() {
    System.setOut(originalOut);
  }
}
