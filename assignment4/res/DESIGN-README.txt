DESIGN CHANGES

1. When running the program "java -jar assignment6.jar", one argument can be given, "gui" or
"nongui". The former starts the GUI, while the latter starts the command-line interface. The
default if no argument is given is GUI.
2. A new GUIViewInterface extends ViewInterface. GUIView is an implementation of GUIViewInterface.
3. There are now two model classes. One, called Model, has the methods used for inflexible
portfolios and the common methods for both kinds of portfolios. FlexiblePortfolioModel extends
Model, and is used for flexible portfolios. FlexiblePortfolioModel implements
FlexiblePortfolioModelInterface.
4. There is now an InflexiblePortfolio class. Portfolio represents flexible portfolios; it extends
InflexiblePortfolio.
5. Created a new API_Interface for any usage of stock APIs, not just Alpha Vantage. Created an API
class, which implements the three methods for usage with Alpha Vantage. These three methods were in
the model class in previous assignments.
6. Shortened the processCommand method in the Controller by making each case in the switch
statement a single method call.
7. Edited the createCommand method in the Controller to differentiate between creating a flexible
or inflexible portfolio. There are now two separate commands in the CLI for creating a flexible or
inflexible portfolio.
8. There is a new method to find the stock price on a given day, and give the stock price on the
next day if given a holiday. Before we only had a method that gives the stock price on the previous
day if given a holiday.
9. Added a new class called GUIController. It creates a GUIView, Model, and FlexiblePortfolioModel,
and delegates to an object of the original controller.

_________________________________________________________________________________________________________________________________________________________

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
