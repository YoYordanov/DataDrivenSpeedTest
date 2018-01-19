# README #

# Data Driven Speed Test

These are automated parameterized data-driven speed tests written in Java with Selenium WebDriver and JUnit.<br>

The tests are using data from an object array and CSV file and perform speed test using an online tool - GtMetrix (https://gtmetrix.com/)<br>

The tests perform the following steps:<br>
- Navigating to the tool webpage and login<br>
- Passing the Url that will be tested, select testing options like test region (country) and connection type (3G, DSL, Cable) and run the speed test<br>
- Verifying visibility of the test results and printing them on the console output (creating a simple report)<br>

## Prerequisites to run the tests

To run these tests you have to install Java and Maven on your machine.

## Running the tests

Run the tests true your IDE or with maven command. 

```
mvn test
```

## Author

* **Yordan Yordanov**