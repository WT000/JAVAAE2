## Contents
- **General Documentation**
- [Introduction](#introduction)
- [Use Cases](#usecases)
- **FINISHED APP**
- [Implemented Features and Testing](#features)
- [Model and Diagrams](#diagrams)
- [Javadoc](#javadoc)
- [Additional notes](#notes)

# APP DESIGN

## <a name="introduction"></a> Introduction
Welcome to the documentation for the application! Here you'll find the standard stuff, such as its use cases (features), model, diagrams, etc.

## <a name="usecases"></a> Use Cases
The following are the identified use cases for the application, each of which covers the different actors (User roles) in the system:

### ADMIN Actor
- **UC1**: Admins can set remote bank properties which are validated
- **UC2**: Admins can view, add, update, and remove items in the catalogue, a quantity of 0 should automatically disable it
- **UC3**: Admins can view and modify ALL users in the system
- **UC4**: Admins can view and modify ALL orders in the system, specifically their status
- **UC5**: Admins can refund orders, logging the transaction to a file

### ANONYMOUS / CUSTOMER Actor
- **UC6**: Anons/Customers can view items in the catalogue, only viewing out of stock / disabled items if they search for it
- **UC7**: Anons/Customers can add and remove items from their basket, but only if they're in stock

### ANONYMOUS Actor
- **UC8**: Anons can sign up for an account, giving them access to viewModifyUser (self), checkout, and orders

### CUSTOMER Actor
- **UC9**: Customers can add their personal details to their account, credit card's can be left, stored in the session, or stored in the DB
- **UC10**: Customers can checkout their basket which (after verifying payment / stock levels) decrements stock, creates an order / invoice, and logs to a file
- **UC11**: Customers can view their own orders

### DEACTIVATED Actor
- **UC12**: Deactiavted accounts cannot be logged into

![UML Diagram](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/UML/use-case-v2.drawio.png "UML Diagram")

# FINISHED APP

## <a name="features"></a> List of finished features from the AE2 brief & test plan

| Feature No (Use Cases) | Implementation                                                                                                                                      | Additional Notes                                                                                                                                  |
|------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------|
| 1 (1-12)               | All four major roles have been added to the system, all requirements for each have been met.                                                        | Deactivated users are technically counted as Customer users, but the deactivation still works.                                                    |
| 2 (4, 5, 10)           | Cart items are converted into an order / invoice which can be viewed by the customer themselves or an admin (who can edit the status or refund it). | Shopping Items are kept in their current state through InvoiceItem entities, meaning editing or deleting the original item won't change the invoice.|
| 3 (10)                 | Stock in the DB is decremented by the amount in the Customers cart if the transaction is successful.                                                |                                                                                                                                                   |
| 4 (4, 5, 10)           | Customers can view orders / invoices related to them, whilst Admins see them ALL and can search by username.                                        |                                                                                                                                                   |
| 5 (1, 5, 10)           | All transactions use the ReST client against the remote bank, specifically using the details currently configured in the properties.                |                                                                                                                                                   |
| 6 (10)                 | Failed transactions simply return the Customer back to their basket without doing anything else.                                                    |                                                                                                                                                   |
| 7                      | The app uses databases for Users, Catalogue Items, Invoice Items and Invoices.                                                                      |                                                                                                                                                   |
| 8                      | The app uses JSP's through Spring, which can be hosted through Tomcat.                                                                              |                                                                                                                                                   |
| 9 (2)                  | Admins can add, edit, and remove items along with their stock count.                                                                                | Category types have been provided such as JEWELRY and TECH.                                                                                       |
| 10 (9)                 | Users (apart from Anonymous and Deactivated users) can create accounts and edit their details, excluding their cvv.                                 | The user can save their card in the session or database. Furthermore, the checkout page has a card form in case they haven't saved a card.        |

[Interested in seeing the test plan? Click here.](https://github.com/WT000/JAVAAE2/blob/main/ae2/documentation/TestDocs.md)
## <a name="diagrams"></a> Diagrams & Screenshots
### UML Class diagrams
the raw cdg files are contained [here](https://github.com/WT000/GROUPA5AE1/tree/main/ae1/documentation/UML/raw-uml) if you wish to view them in that format.

- card-checker (handles parts of card validation before the ReST transfer)

![card-checker class diagram](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/UML/card-checker-uml.png "card-checker class diagram")

- bank-client (handles the ReST transfer and contains DAO objects)

![bank-client class diagram](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/UML/bank-client-uml.png "bank-client class diagram")

- web (brings everything together in a web app and controls properties)

![web class diagram](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/UML/web-uml.png "web class diagram")

### Sequence and Robustness diagrams
To visually demonstrate some of our use cases, here's some diagrams which follow the guidelines / rules of [this](https://www.visual-paradigm.com/guide/uml-unified-modeling-language/robustness-analysis-tutorial/) tutorial:

- **UC1 (login)**

![login diagram](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/UML/sequencelogin.png "login diagram")

- **UC2 (properties)**

![properties sequence diagram](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/UML/sequenceproperties.png "properties sequence diagram")
![properties robustness diagram](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/UML/robustnessAdminProperties.png "properties robustness diagram")

- **UC3 (refund)** 

![refund sequence diagram](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/UML/sequencerefund.png "refund sequence diagram")
![refund robustness diagram](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/UML/robustnessAdminRefund.png "refund robustness diagram")

- **UC4 / UC5 (add card and transaction)**

![transaction sequence diagram](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/UML/sequenceadd_transaction.png "transaction sequence diagram")
![transaction robustness diagram](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/UML/robustnessUser.png "transaction robustness diagram")

## <a name="javadoc"></a> Javadoc
For those who want a greater understanding of how the program works, Javadoc has been used across the code to give details on how things are done. To view it, go to the ae2 parent folder and use ``` mvn javadoc:javadoc ```. Or, you could generate the Javadoc in [Netbeans](https://netbeans.apache.org/) by right clicking an open Maven module and selecting ***Generate Javadoc***.

## <a name="notes"></a> Additional notes
### CI / CD
I decided to use this to ensure that my code is still functioning after each push to the repo, it's especially useful for finding commits which accidentally break the system and require fixing. It also saves time as I don't need to manually run tests right as I'm about to push, I can simply look at the status on GitHub and see if the app is okay!
