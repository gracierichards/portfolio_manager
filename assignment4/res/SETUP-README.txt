STARTING THE PROGRAM
First, note that the program can create portfolio files and a directory in the same folder as the
.jar file, so place the .jar file in your desired location. It does not depend on any input files
to be present to run.
Open the terminal and navigate to the directory where the .jar file is located. To start the
program, simply enter "java -jar assignment5.jar".



SUPPORTED COMMANDS
Below is the syntax for all commands this program can take.

create portfolio <portfolio_name> <ticker_symbol>:<integer> [<ticker_symbol>:<integer>...]
    Creates a portfolio with the given name and one or more stocks. Each stock must be provided an
    integer for the number of that stock in the portfolio. For example,
    create portfolio <portfolio_name> MSFT:20 AAPL:10 NVDA:30
    would make a portfolio with 20 shares of MSFT, 10 shares of AAPL, and 30 shares of NVDA

load portfolio <portfolio_name> <filename>
    Loads a portfolio from the file "filename". Names the resulting portfolio portfolio_name

save <portfolio_name> <filename>
    Saves the specified portfolio to a file named "filename"

list <portfolio_name>
    Displays the composition of the portfolio.

value <portfolio_name> MM/DD/YYYY
    Determines the total value of the portfolio on the given date. The date must follow MM/DD/YYYY
    format.

search <company_name>
OR
search <ticker_symbol>
    Allows the user to look up ticker symbols that match the name of a company, or all ticker
    symbols that start with the inputted string. Prints all search results matching the given
    text.

stock-direction-day <ticker_symbol> MM/DD/YYYY
    Tells you whether the given stock gained or lost on the given day.

stock-direction-over-time <ticker_symbol> start_date end_date
    Tells you whether the given stock gained or lost over the given period of time, from start_date
    to end_date (both in MM/DD/YYYY)

moving-average x <ticker_symbol> MM/DD/YYYY
    The command for an x-day moving average. Calculates the average price for the given stock in
    the last x days, starting from the given date. It includes the last x days for which stock
    prices are available.

crossovers <ticker_symbol> start_date end_date
    Returns a list of the positive crossovers and negative crossovers within the given time period.
    A crossover day means that the closing price for the day and the closing price for the previous
    day are on opposite sides of the 30-day moving average.

moving-crossovers <ticker_symbol> start_date end_date
    After entering this command, the user will be prompted twice, once for x, the number of days for
    the smaller moving average, and then for y, the number of days for the larger moving average.
    If the second amount is not longer than the first amount, then it will provide an error message.

CostBasis <portfolio-name> MM/DD/YYYY
    Calculates the cost basis, or the total amount of money invested in the given portfolio, by a
    specific date.

portfolioValueOnDate <portfolio-name> MM/DD/YYYY
    Calculates the value of the given portfolio at the end of the specified day.

chart-portfolio <portfolio-name> start_date end_date
    Creates a horizontal bar chart of the performance of a portfolio over time. It has bars for
    regular time intervals from startDate to endDate, and the bar is made up of asterisks, with
    each asterisk representing the number of dollars indicated in the scale.

chart-stock <ticker_symbol> start_date end_date
    Creates a horizontal bar chart of the performance of a stock over time. It has bars for
    regular time intervals from startDate to endDate, and the bar is made up of asterisks, with
    each asterisk representing the number of dollars indicated in the scale.

purchase <portfolio_name> <ticker_symbol> <date> <numShares>
    Buys stocks and adds them to the portfolio

sell {portfolio_name} {ticker_symbol} {date} {numShares}
    Sells the specifed stocks from the portfolio

quit
    Terminates the program.



How to create a portfolio with 3 stocks purchased on different dates:
create portfolio portfolio1
purchase portfolio1 MSFT 01/01/2019 20
purchase portfolio1 AAPL 01/11/2019 10
purchase portfolio1 NVDA 02/01/2019 30

How to query the value and cost basis on two different dates:
portfolioValueOnDate portfolio1 01/20/2019
portfolioValueOnDate portfolio1 02/15/2019
CostBasis portfolio1 01/20/2019
CostBasis portfolio1 02/15/2019



STOCKS SUPPORTED
Any U.S. stock available on Alpha Vantage. Search for valid stocks using the search command.
DATES SUPPORTED
Any date.
