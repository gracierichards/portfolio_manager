FEATURES
---------


 I. Complete Features:

	1. Create Portfolio: Users can create portfolios and add stocks to them along with the number of shares.

	2. Save Portfolio to File: Portfolios can be saved to a file with their stock composition for future reference.

	3. Load Portfolio from File: Users can load portfolios from files, including the stock composition.

	4. Examine Portfolio Composition: Users can view the composition of a portfolio, listing the stocks and their quantities.

	5. Determine Portfolio Value: The system can calculate the total value of a portfolio on a specific date by fetching stock prices from CSV files.

	6. Search Ticker Symbols: Users can search for valid ticker symbols based on company names and vice versa.

	7. Interactive Command-Line Interface (CLI): Users can interact with the system through a command-line interface, which provides a user-friendly way to input commands and view 	   results.

	8. Obtain up-to-date stock data: The program queries Alpha Vantage to obtain the real up-to-date values for stocks in the U.S. stock market. It stores the data in files, so once a        	   certain ticker is queried, it knows all prices for that ticker without needing to connect to Alpha Vantage again.
	
	9. Purchase Shares: Allows the user to purchase a specified number of shares of a stock with the given ticker symbol for the specified portfolio and date.

       10. Sell Shares: Enables the user to sell a specified number of shares of a stock with the given ticker symbol from the specified portfolio and date.
	
       11. Total Cost Basis: Calculates the total cost basis of stocks in a specified portfolio up to a given date. It takes the name of the portfolio and the date as parameters.
 
       12. Portfolio Value On Date: Computes the total value of a portfolio on a specified date. It takes the name of the portfolio and the date as parameters.

       13. Stock Direction: Determines whether the given stock gained or lost on the given day. Using opening and closing values.

       14. Stock Direction(Period): Determines whether the given stock gained or lost over the given period of time. Uses closing values.   

       15. Moving Average: Calculates the average price for the given stock in the last x days, starting from the given date. It includes the last x days for which stock prices are    	   available.

       16. Find Crossovers: Gives a list of the positive crossovers and negative crossovers within the given time period.

       17. Moving Crossovers: Gives a list of the positive crossovers and negative crossovers within the given time period. A moving crossover happens when an x-day moving average 		   crosses from one side to another side of a y-day moving average, with x lesser than y.

       18. Performance Chart: Users can create horizontal bar chart of the performance of a portfolio or stock over time. It has bars for regular time intervals from startDate to endDate, and the 	   bar is made up of asterisks, with each asterisk representing the number of dollars indicated in the scale.

II. Incomplete Features:

    	 None