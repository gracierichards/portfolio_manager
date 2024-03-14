STARTING THE PROGRAM
First, note that the program can create portfolio files and a directory in the same folder as the
.jar file, so place the .jar file in your desired location. It does not depend on any input files
to be present to run.
Open the terminal and navigate to the directory where the .jar file is located. To start the
program, simply enter "java -jar assignment4.jar".



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

quit
    Terminates the program.



How to create a portfolio with 3 stocks:
create portfolio portfolio1 MSFT:20 AAPL:10 NVDA:30

Portfolio with 2 stocks:
create portfolio portfolio2 AAPL:40 TSLA:5

Query their value:
value portfolio1 01/01/2024
value portfolio2 01/01/2024



STOCKS SUPPORTED
Any U.S. stock available on Alpha Vantage. Search for valid stocks using the search command.
DATES SUPPORTED
Any date.
