public class View {
  public void showTickerMatches(String csvContents) {
    System.out.println("Matching stocks:");
    System.out.println("symbol\tname\t");
    String[] lines = csvContents.split("\n");
    for (int i = 1; i < lines.length; i++) {
      String[] elements = lines[i].split(",");
      String type = elements[2];
      String region = elements[3];
      if (type.equals("Equity") && region.equals("United States")) {
        System.out.println(elements[0] + "\t" + elements[1] + "\n");
      }
    }
  }
}
