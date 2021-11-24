## Contents
- **APP DESIGN**
- [Requirements, Planning & Features](#requirements)
- [Use Cases](#usecases)
- [Initial Designs](#designs)
- **FINISHED APP**
- [List of finished features & test plan](#features)
- [Diagrams](#diagrams)
- [Javadoc](#javadoc)
- [Additional notes](#notes)

# APP DESIGN

## <a name="requirements"></a> Requirements, Planning & Features
This application is built to resemble a modern point of sale application, allowing users to enter their credit card details into a website and see a result depending on the information they put in. From knowing this, it allows us to split the program into a short list of simple requirements: 

- **Users must be able to enter credit card details**
- **Users must be able to enter an amount to send**
- **Users must be able to send that amount to the stored bank card, but only if the entered card exists on the bank service**

However, not just users will be operating on this application. Administrators (the owner of the point of sale application in this case) will need to be able to perform the following operations using the same service:

- **Update properties such as the remote bank URL, stored card and credentials**
- **Issue refunds to entered cards**

There will also be a few miscellaneous properties of the application that will allow for higher security and easier diagnosis of errors caused:

- **The system needs an appropriate amount of try / catch blocks and form validation to ensure it doesn't crash or use incorrect information**
- **The system needs an intuitive UI so users don't feel like they're using an outdated site**
- **The system should log transactions to a rolling file and console**

From these requirements, a set of development phases could be created and tasks could be picked by group members to complete. You can look at the [Projects](https://github.com/WT000/COM528AE1/projects) board for identified features and specific details on who did what, but in a nutshell:

| Github User | Phase 1                                                                                       | Phase 2                                          | Phase 3                                                                                |
|-------------|-----------------------------------------------------------------------------------------------|--------------------------------------------------|----------------------------------------------------------------------------------------|
| [Will](https://github.com/WT000)        | Developed designs for the site and implemented ReST, logging, properties, and error detection | Improved the design and secured the admin page   | Secured the admin page, rewrote the front-end, wrote Javadoc / general documentation and implemented SpringMVC                        |
| [Nastaran](https://github.com/nastaransharifisadr)    | Aided with design and tested CreditCard, ReST, Properties along with a test plan              | Created further documentation and tested classes | Finalised unit tests and designed the UML use cases & sequence diagrams, along with refining the test plan |
| [Hayri](https://github.com/hairicko21)       | Nothing                                                                                       | Nothing                                          | Nothing                                                                                |
| Benjamin (unknown github)    | Nothing                                                                                       | Nothing                                          | Nothing                                                                                |
  
## <a name="usecases"></a> Use Cases
The following are use cases for the application based on the previously identified requirements:

### Admin Actor
- **UC1**: Admins must be able to log into a secure control panel
- **UC2**: Admins must be able to enter new properties / overwrite default properties
- **UC3**: Admins must be able to (after validation) refund to cards

### User Actor
- **UC4**: Users must be able to add their card details and (after validation) review their details
- **UC5**: Users must be able to submit the transaction, sending the amount to the REST URL

### Bank Actor
- **UC6**: The bank must be able to transfer money between the accounts during transactions and refunds

![UML Diagram](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/UML/use-case-v2.drawio.png "UML Diagram")

## <a name="designs"></a> Initial Designs
Before creating the webapp, we created a few initial ideas for how the pages would look and how events could be handled. Below are mine and Nastaran's initial ideas for the site:

### User Actor
- [Sale App](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/legacy/images/Website%20Ideas/saleapp.PNG)
- [Sale App SUCCESS](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/legacy/images/Website%20Ideas/saleappsucc.PNG)
- [Sale App FAILURE](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/legacy/images/Website%20Ideas/saleappfail.PNG)
- [Sale App Card Reader Design](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/legacy/images/Website%20Ideas/designA.PNG)
- [Legacy Sale App Card Reader Design](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/legacy/images/Website%20Ideas/designB.PNG)

### Admin Actor
- [Login Form](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/legacy/images/Website%20Ideas/adminlogin.PNG)
- [Control Panel](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/legacy/images/Website%20Ideas/controlpanel.PNG)

# FINISHED APP

## <a name="features"></a> List of finished features & test plan

| Use Case Feature | Implementation                                                                                                                                | Additional Notes                                                                                                                                                                                                                    |
|------------------|-----------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| UC1              | Admins are able to enter a username and password (of which is stored in the properties file) to gain access to the page.                      | The password which is checked against is hashed. Login access is granted automatically if required values are missing.                                                                                                              |
| UC2              | Once logged in, the admin is able to view current properties or add new properties in a form.                                                 | Default properties are loaded when no properties are currently present, meaning testuser2 and defaulttestpass are the default credentials. Furthermore, the entered properties are tested against the bank to ensure they're valid. |
| UC3              | Once logged in, admins are able to type card numbers to refund to and transfer money into the account.                                        | The from card (which would be the bank card in this case) is always retrieved from the properties file.                                                                                                                                                                           |
| UC4              | Users are able to enter their bank card details (card number, card name, card date, and card cvv) and review their entered details at the end. | The card number and date are validated before sending to the bank, meaning the transaction process doesn't even need to begin.                                                                                                      |
| UC5              | Once the form is validated, the card details are sent to the bank and an appropriate result is given to the user.                             | The ReST connection catches any exceptions or errors that happen, ensuring the user gets a relevant result.                                                                                                                         |
| UC6              | The cards and an appropriate amount are sent to the ReST URL and transactions / refunds send to the correct account.                          | Like above, error detection is implemented to ensure the system doesn't crash, the URL can be updated in the properties control form too.                                                                                           |

[Interested in seeing the test plan and images of the app working? Click here.](https://github.com/WT000/GROUPA5AE1/blob/main/ae1/documentation/appTestPlan.md)
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
For those who want a greater understanding of our program works, Javadoc has been used across the code to give details on how things are done. To view it, go to the ae1 parent folder and use ``` mvn javadoc:javadoc ```. Or, you could generate the Javadoc in [Netbeans](https://netbeans.apache.org/) by right clicking an open maven module and selecting ***Generate Javadoc***.

## <a name="notes"></a> Additional notes
### CI / CD
We decided to use this to ensure that our code is still functioning after each push to the repo, it's especially useful for finding commits that accidentally break the system and need someone to fix the program. It also saves time as we don't need to manually run tests right as we're about to push, we can simply look at the status on GitHub and see if the app is okay.
