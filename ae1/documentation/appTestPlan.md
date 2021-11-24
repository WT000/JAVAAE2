# TEST PLAN

Click [here](https://github.com/WT000/COM528AE1/blob/main/ae1/documentation/appDesign.md) to go back to the main documentation for the app.

The code already has unit tests to ensure classes and methods work as intended, but it's always good to ensure that the front-end is properly working with everything too. Here's a recap of the use cases:
- **UC1**: Admins must be able to log into a secure control panel
- **UC2**: Admins must be able to enter new properties / overwrite default properties
- **UC3**: Admins must be able to (after validation) refund to cards
- **UC4**: Users must be able to add their card details and (after validation) review their details
- **UC5**: Users must be able to submit the transaction, sending the amount to the REST URL
- **UC6**: The bank must be able to transfer money between the accounts during transactions and refunds

# TESTS

**Test Number**|**Use Case(s)**|**Test to Perform**|**Method**|**Result**
:-----:|:-----:|:-----:|:-----:|:-----:
1.1|UC1|Users must be able to log into the control panel with the correct details|Attempt to enter "testuser2" and "defaulttestpass" and see if it works|PASS ![Test](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/images/1.1.PNG)
1.2|UC1|Users must be rejected when entering the wrong username or password|Attempt to enter invalid username and password combination|PASS ![Test](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/images/1.2.PNG)
1.3|UC1|Users must skip the login page if properties values don't exist|Wipe the properties file (but not delete it as this would reform the default values) and attempt to go to the control panel|PASS ![Test](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/images/1.3.PNG)
1.4|UC1|User login credentials must be wiped when returning to the saleapp|Go back to the saleapp page|PASS ![Test](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/images/1.4.PNG)
2.1|UC2|Users must be able to submit valid properties|Attempt to change the properties to the testuser1 details|PASS ![Test](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/images/2.1.PNG)
2.2|UC2|Users must not be able to set the properties to invalid values|Attempt to use an invalid URL, username, password and card number|PASS ![Test](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/images/2.2A.PNG) ![Test](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/images/2.2B.PNG) ![Test](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/images/2.2C.PNG)
2.3|UC2|Properties must be written to a file|Check the properties after setting them to see if they exist, especially to see if the plain text password has been hashed|PASS ![Test](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/images/2.3.PNG)
3.1|UC3|Users must be able to refund to card|Attempt to refund £5 to 5133880000000012|PASS ![Test](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/images/3.1A.PNG) ![Test](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/images/3.1B.PNG) ![Test](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/images/3.1C.PNG) ![Test](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/images/3.1D.PNG)
3.2|UC3|Users must not be able to enter invalid cards - it shouldn't do the ReST refund|Attempt to enter an invalid card (1111222233334444) to stop the transfer early|PASS ![Test](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/images/3.2.PNG)
4.1|UC4|Users must be able to add their card details|Attempt to use the virtual keypad and get to the submit screen at the end|PASS ![Test](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/images/4.1.PNG)
4.2|UC4|Users must not be able to enter a card below or above 16 digits|Attempt to submit a card under 16 digits (going over is impossible with the system)|PASS ![Test](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/images/4.2.PNG)
4.3|UC4|Users must only be able to enter dates in MM/YY or MM/YYYY format|Attempt to enter invalid dates|PASS ![Test](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/images/4.3A.PNG) ![Test](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/images/4.3B.PNG)
4.4|UC4|Users must only be able to enter a 3 digit length cvv|Attempt to enter invalid cvv's|PASS ![Test](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/images/4.4.PNG)
4.5|UC4|Users must only be able to place one decimal place in the amount|Attempt to enter . more than once|PASS ![Test](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/images/4.5.PNG)
4.6|UC4|Users must be able to cancel their transaction|Attempt to click the X button during the transaction process|PASS ![Test](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/images/4.6.PNG)
4.7|UC4|Users must only be able to place a space once in their name|Attempt to click the space button after already using it|PASS ![Test](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/images/4.7.PNG)
5.1|UC5/6|Transactions must be sent to the appropriate ReST URL|Attempt to send a transaction using 5133880000000012 and view the transactions page|PASS ![Test](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/images/5.1A.PNG) ![Test](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/images/5.1B.PNG) ![Test](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/images/5.1C.PNG) ![Test](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/images/5.1D.PNG)
5.2|UC5/6|Refunds must be sent to the appropriate ReST URL|Attempt to refund the previous transaction|PASS ![Test](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/images/5.2A.PNG) ![Test](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/images/5.2B.PNG) ![Test](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/images/5.2C.PNG) 
5.3|UC5/6|Transactions must be logged to a file|Check the generated logs file in tomcat/logs/ae1/ae1-perf.log|PASS ![Test](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/images/5.3.PNG)
5.4|UC5/6|Users and admins must not be able to send transactions if they don't have the required amount|Attempt to send an absurd amount using the ReST client|PASS ![Test](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/images/5.4.PNG)
