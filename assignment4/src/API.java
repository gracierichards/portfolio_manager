import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Implementation of the API_Interface interface for interacting with the AlphaVantage API.
 */
public class API implements APIInterface {

  @Override
  public String getStockData(String tickerSymbol) {
    return getAlphaVantageData("function=TIME_SERIES_DAILY"
            + "&outputsize=full"
            + "&symbol"
            + "=" + tickerSymbol);
  }

  @Override
  public String getAlphaVantageData(String urlPart) {
    //old low volume api key
    //String apiKey = "8FDS9CHM4YROZVC5";
    String apiKey = "QEHLSEQZWQ0SZGJA";
    URL url = null;
    try {
      url = new URI("https://www.alphavantage" + ".co/query?" + urlPart + "&apikey=" + apiKey + "&datatype=csv").toURL();
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    } catch (URISyntaxException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }
    InputStream in = null;
    StringBuilder output = new StringBuilder();
    String tickerSymbol = urlPart.substring(urlPart.indexOf("symbol") + "symbol".length()
            + 1);
    try {
      in = url.openStream();
      int b;

      while ((b = in.read()) != -1) {
        output.append((char) b);
      }

      //if (output.charAt(0) == '{') {
      //  System.out.println(tickerSymbol + " is not a valid ticker symbol.");
      //}
    } catch (IOException e) {
      if (urlPart.contains("TIME_SERIES_DAILY")) {
        System.out.println("No price data found for " + tickerSymbol);
      } else {
        String query = urlPart.substring(urlPart.indexOf("keywords")
                + "keywords".length() + 1);
        System.out.println("No ticker symbols or companies found for " + query);
      }
    }
    return output.toString();
  }

  @Override
  public String getTickerMatches(String query) {
    return getAlphaVantageData("function=SYMBOL_SEARCH"
            + "&keywords=" + query);
  }
}
