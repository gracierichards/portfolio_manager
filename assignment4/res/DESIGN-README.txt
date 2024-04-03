DESIGN CHANGES

  1. Evey stock added to portfolio is now associated with a purchase date which is stored in a new hashmap, purchaseDates<>, along with their tickersymbols.
     Stock added at the date of creation of the portfolio are assigned the creation Date as their purchase date and other additional stocks purchased later
     require the user to manually enter a date of purchase.

  2. Unlike in Assignment 4, the controller now takes in the scanner created by Main. This is
     because for one of the commands, moving-crossovers, the controller has to prompt the user for
     two ints, x and y, after entering the initial command. Therefore, the controller needs access
     to the scanner for user input.
         The design of the scanner being instantiated in Main was kept, so Main can check whether
     the command says quit and terminate the program from there. The controller does not take in a
     more general input like an InputStream, because this would not be compatible with the scanner
     taking input in Main.

  3. The logic for checking if a ticker symbol is a valid stock has been taken out of
     createPortfolio and abstracted into a helper function called isValidTicker. This is different
     from isValidTicker in Assignment 4, which could only check validity in certain cases, and the
     old method has been renamed and is now used as a helper for the new isValidTicker. The new
     method is a general function that any method in Model can use to determine if a ticker symbol
     is valid or download stock data.
         This is an improvement over the original design because functionality that is required for
     many parts of the program, determining if a ticker symbol is valid, is now in a separate
     method. And it ensures that the stock data is always downloaded before it is needed, because
     isValidTicker will always be called when the program receives a ticker symbol as input.

  4. The logic for finding the price of a stock on a certain date has been taken out of
     determineValue and abstracted into a helper function called getStockPrice. Now any class in
     this package can use this method to find the price of stock. It takes in a newly created ENUM
     that specifies whether to return the opening or closing price.
         This is an improvement over the original design because functionality that is required for
     many parts of the program, finding the price of a stock, is now in a method accessible by the
     rest of the program.

OVERVIEW

  The Portfolio Management System is designed to provide users with tools for managing investment portfolios. It allows users to create portfolios, 
  add stocks to them, save portfolios to files, load portfolios from files, examine portfolio composition, determine portfolio values, search
  for ticker symbols, purchase and sell stocks/shares, find the cost basis and portfolio value on a particular date, the net gain/loss over a day 
  and over a period of time, find out the moving averages, crossovers and moving crossovers and also creates a performance chart of a portfolio on
  a give date. The system is designed with a Model-View-Controller (MVC) architecture to ensure modularity and separation of concerns.

__________________________________________________________________________________________________________________________________________________________

COMPONENTS 

Model
1.The Model component represents the core logic of the system. It encapsulates data and business logic related to portfolios, stocks, and interactions
  with external data sources such as stock price APIs. It stores portfolios as a HashMap of portfolio names to corresponding Portfolio objects. 
  Key classes in the Model component include:

2.Portfolio: Represents a user's investment portfolio, containing a collection of stocks, the purchase dates of each stock and the cost basis of these 
  stocks. Stocks are represented as a string containing the ticker symbol of the stock, which maps to the amount of the stock in the portfolio.
   
3.Model: Implements the core functionality of the system, including portfolio creation, management, and data retrieval.


View

  The View component handles user interface elements and presentation logic. It provides visual representations of portfolio data and interacts with users 
  through a command-line interface (CLI). Key classes in the View component include:

  View: Implements methods for displaying portfolio data, ticker symbol matches, portfolio values to users, shows crossovers, displays the cost basis and
  portfolio value on given dates to the user.

Main: Instantiates the Model, View, and Controller. Implements a command-line interface for user interaction, continuously accepting input from the
      command-line, and passes commands to the controller.

Controller

  The Controller component acts as an intermediary between the Model and View components. It receives user input, invokes appropriate methods on the Model, 
  and updates the View   accordingly. Key classes in the Controller component include:

  Controller: Coordinates user interactions, delegates tasks to the Model, and updates the View with relevant data.
____________________________________________________________________________________________________________________________________________________________

INTERACTION FLOW

1. User interacts with the CLI by entering commands and parameters.
2. Main passes user input to Controller.
3. Controller processes user commands, delegates tasks to the Model, and retrieves data.
4. Model performs requested operations, interacts with external data sources if necessary, and returns results to the Controller.
5. Controller updates the View with the retrieved data or error messages.
6. CLI displays the updated interface to the user and Main awaits further input.

_____________________________________________________________________________________________________________________________________________________________

DESIGN PATTERNS

  1.Model-View-Controller (MVC): Used to separate concerns and achieve modularity by dividing the system into Model, View, and Controller components.

  2.Singleton Pattern: Implemented in the Model component to ensure that only one instance of the Model exists throughout the application's lifecycle.

  3.Factory Method Pattern: Potentially used for creating instances of Portfolio objects or other entities within the Model component.
_____________________________________________________________________________________________________________________________________________________________

MISCELLANEOUS

1. Each time the program queries the stock prices from Alpha Vantage, it stores the data in a csv file in the stockcsvs folder. The daily price info for each 
   stock is in a separate file. The stockcsvs folder is located in the same folder as the .jar file. When the program needs data for a stock, it checks if the
   csv file for the stock exists before querying Alpha Vantage.

2. It has some flexibility in the commands it accepts, for example omitting the word "portfolio" for the load portfolio command.
