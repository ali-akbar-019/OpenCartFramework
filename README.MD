# OpenCart Automation Testing Framework

## Project Overview

This project is an automation testing framework developed for the OpenCart web application. The framework is built using Selenium WebDriver, TestNG, and Maven, following the Page Object Model (POM) design pattern. It is designed to support functional testing, regression testing, API testing, performance testing, and basic security testing.

The main goal of this project is to demonstrate a complete software testing lifecycle including test design, automation implementation, execution, and reporting.

---

## Project Team Members

- Ali Akbar
- Zainab Fatima
- Laiba Zulfiqar

---

## Objectives

The main objectives of this project are:

- To automate functional test cases for the OpenCart application
- To implement a maintainable automation framework using Page Object Model
- To perform cross-browser testing using TestNG
- To validate API functionality using Postman and Newman
- To conduct performance testing using Apache JMeter
- To perform basic security testing including SQL Injection and XSS validation
- To generate test execution reports and logs

---

## Tools and Technologies

- Java
- Selenium WebDriver
- TestNG
- Maven
- Page Object Model (POM)
- Postman
- Newman
- Apache JMeter
- Lighthouse
- Eclipse IDE
- Git and GitHub

---

## Project Structure

The framework follows a modular structure based on Page Object Model design.

src/test/java contains the main framework:

- pageObjects: Contains page classes representing UI pages
- testCases: Contains test scripts and test execution logic
- utilities: Contains helper classes and reusable utilities

Key components include:

pageObjects:

- HomePage
- LoginPage
- RegisterPage
- SearchPage
- ProductPage
- CartPage
- CheckoutPage
- MyAccountPage

testCases:

- BaseClass
- TC_001_AccountRegistrationTest
- TC_002_LoginTest
- TC_003_SearchTest
- TC_004_MyAccountTest
- TC_005_CartTest
- TC_006_RegisterNegativeTest
- TC_007_CheckoutTest
- TC_008_ProductPageTest
- TC_009_SearchBoundaryTest
- TC_010_EndToEndTest

utilities:

- DataProviders
- ExtentReportManager
- XLUtility
- EmailValidator
- PasswordValidator
- CheckoutTotalCalculatorTest
- ProductQuantityValidatorTest

---

## Testing Scope

### Functional Testing

- User registration
- Login functionality
- Product search
- Cart operations
- Checkout process

### UI Automation Testing

- Selenium WebDriver automation
- Cross-browser execution
- Page Object Model implementation

### API Testing

- Performed using Postman
- Execution automated using Newman
- Covered CRUD operations, authentication, and negative test cases

### Performance Testing

- Conducted using Apache JMeter
- Load testing for homepage, login, and search functionality
- Lighthouse used for performance audit

### Security Testing

- SQL Injection testing on input fields
- Cross Site Scripting (XSS) validation
- Input validation and boundary testing

---

## Execution Instructions

To run the automation tests:

1. Clone the repository
2. Import project into Eclipse IDE as a Maven project
3. Update dependencies using Maven
4. Run testng.xml or specific suite files

Test suites available:

- testng.xml
- testng-chrome.xml
- testng-firefox.xml
- testng-edge.xml
- testng-unit.xml

To execute using Maven:

mvn test

---

## Reports and Outputs

The framework generates the following outputs:

- TestNG execution reports
- Extent HTML reports
- API execution reports (Newman)
- JMeter performance reports
- Lighthouse performance report
- Screenshots for failed test cases
- Execution logs

All reports are stored in the reports, logs, screenshots, and performance testing directories.

---

## Key Features

- Page Object Model architecture for maintainability
- Data-driven testing support
- Cross-browser test execution
- Integration with TestNG for test management
- API testing integration using Postman and Newman
- Performance testing using JMeter
- Basic security testing for input validation
- Detailed reporting and logging system

---

## Notes

This project is developed for academic purposes and demonstrates a complete automation testing framework covering UI, API, performance, and security testing levels.
