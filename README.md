**Rest-API Automation Framework:**

Install Maven and Import as Maven project in IntelliJ or Eclipse

**Framework Structure:**

**base**:It consists of all the reusable methods which common and reusable throughout the framework

**db**:It consists of db queries and setting and getting the data from the pojo class

**pojo**:It consists of db attributes/columns which needs to be validated in db wrt to the response

**requestBuilder**:It consists of the requestBuilder(RequestBody) methods

**responseParser**:It consists of the response validation methods

**utils**: It consists of all the utilities which are common and reusable throughout the framework

**resources**: 

**config.properties** - It contains all the service urls/endpoints , db connections and usernames

**testData**:It consists of all the testData(.csv) files

**test**: All Success and Error test cases

**testng.xml**: It contains all the tests/packages to be run in the suite

**Note**:

Java version 11 is used for implementing this framework

Java Faker library is used to Generate fake test data for testing

TestNG is framework is used for test Assertions

Lombok library has been used to generate Getter and Setters automatically

Maven is used as build tool for this framework

How to run test cases:

* Click on testng.xml to execute the complete suite

* Eclipse/IntelliJ -Right click to any test class and run

**Running from CMD Line :**

1.Navigate to framework/project path and use the below command

2.mvn clean install 

**test-output:**

Execution Reports(open REST-API-AutomationReport.html in a browser to see the detailed report)