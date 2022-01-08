# JAVAAE2
The AE2 for OODD, similar to the AE1 but done individually and with a database connected through Spring Boot.

# HOW TO RUN
Firstly, ensure that you've got [Java JDK 8](https://www.oracle.com/java/technologies/downloads/#java8) or newer installed on your system. You'll also need [Apache Maven](https://maven.apache.org/download.cgi) to ensure the application can build its dependencies and function correctly.

Next, go to the root of the project (ae2) and run:
```
mvn clean install
```
Now that you've got the dependencies you can move to the web module and run the project through [Tomcat](https://tomcat.apache.org/download-90.cgi):
```
cd web
mvn cargo:run
```
The project will now be hosted at http://localhost:8080/ae2ShoppingCartApplication/index.html and will show you the default admin password and log location.

# DOCUMENTATION
General documentation can be found [here](https://github.com/WT000/JAVAAE2/blob/main/ae2/documentation/GeneralDocs.md).

The test documentation can be found [here](https://github.com/WT000/JAVAAE2/blob/main/ae2/documentation/TestDocs.md).
