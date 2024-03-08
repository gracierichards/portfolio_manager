/**
 *
 */
public class Stock {
  private String tickerSymbol;
  private float numOwned;
  Stock(String tickerSymbol, float numOwned) {
    this.tickerSymbol = tickerSymbol;
    this.numOwned = numOwned;
  }

  /**
   * Makes a new stock object with the same tickerSymbol and numOwned as this.
   * @return a copy of this Stock object.
   */
  Stock copy() {
    return new Stock(tickerSymbol, numOwned);
  }
}
