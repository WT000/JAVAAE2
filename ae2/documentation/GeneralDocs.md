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

### BANK Actor
- **UC13**: Money should be transfered between accounts when a transaction or refund takes place

Below is a visual representation of the main use cases for the main Actor's of the system, those being CUSTOMER, ADMIN, and BANK:
![UML Diagram](https://github.com/WT000/JAVAAE2/blob/main/ae2/documentation/drawio/ae2usecases.png "UML Diagram")

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

In total there are 5 MVC Controllers, each of which can handle errors and show an error page:
| Controller Name  | Purpose                                                                                                                          | Additional Notes                                                                                                                           |
|------------------|----------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------|
| GenericMVC       | Used to display generic pages without much code behind them, like the home or about page.                                        |                                                                                                                                            |
| UserMVC          | Used to handle user registration, user login, and user modification through the user database.                                   |                                                                                                                                            |
| CatalogueCartMVC | Used to handle item creation, item viewing, item modification, item deletion, cart control, cart checkout, and invoice creation. | The checkout code is within the MVC itself instead of a Service object, perhaps this could be changed in the future to aid in scalability. |
| OrdersMVC        | Used to handle order viewing, order modification, and order refunding.                                                           | The same point as above, the refund code is within the MVC itself.                                                                         |
| PropertiesMVC    | Used to view and set new remote bank properties.                                                                                 |                                                                                                                                            |

[Interested in seeing the test plan? Click here.](https://github.com/WT000/JAVAAE2/blob/main/ae2/documentation/TestDocs.md)
## <a name="diagrams"></a> Model and Diagrams
### Model / UML Class diagram (simplified)
The class diagram below includes the main parts of the system, in particular the databases, what classes will be stored in them, etc. Some relations and classes (such as the MVC's) have been removed for the sake of viewing, otherwise there would be relations across everything and make it impossible to quickly understand what's going on.

![class diagram](https://github.com/WT000/JAVAAE2/blob/main/ae2/documentation/images/ae2ClassDiagram.png "web class diagram")

### Sequence and Robustness diagrams
To visually demonstrate some of the use cases, here's some diagrams which follow the guidelines / rules of [this](https://www.visual-paradigm.com/guide/uml-unified-modeling-language/robustness-analysis-tutorial/) tutorial:

- **CUSTOMER ACTOR**

![customer sequence diagram](https://github.com/WT000/JAVAAE2/blob/main/ae2/documentation/drawio/ae2UserActorSequence.png "customer sequence diagram")
![properties robustness diagram](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/UML/robustnessAdminProperties.png "properties robustness diagram")

- **ADMIN ACTOR**

![login diagram](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/UML/sequencelogin.png "login diagram")

## <a name="javadoc"></a> Javadoc
For those who want a greater understanding of how the program works, Javadoc has been used across the code to give details on how things are done. To view it, go to the ae2 parent folder and use ``` mvn javadoc:javadoc ```. Or, you could generate the Javadoc in [Netbeans](https://netbeans.apache.org/) by right clicking an open Maven module and selecting ***Generate Javadoc***.

## <a name="notes"></a> Additional notes
### CI / CD
I decided to use this to ensure that my code is still functioning after each push to the repo, it's especially useful for finding commits which accidentally break the system and require fixing. It also saves time as I don't need to manually run tests right as I'm about to push, I can simply look at the status on GitHub and see if the app is okay!
### Additional Tests
I've added additional tests when it comes to testing the database, classes, and objects, ensuring that there's as little bugs as possible.
