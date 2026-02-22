# 🚀 SeleniumCucumber-BDD-Enterprise-Framework

[![CI - Selenium Cucumber BDD Tests](https://github.com/wbandara-tech/SeleniumCucumber-BDD-Enterprise-Framework/actions/workflows/ci.yml/badge.svg)](https://github.com/wbandara-tech/SeleniumCucumber-BDD-Enterprise-Framework/actions/workflows/ci.yml)
![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)
![Selenium](https://img.shields.io/badge/Selenium-4.27-green?logo=selenium)
![Cucumber](https://img.shields.io/badge/Cucumber-7.20-brightgreen?logo=cucumber)
![TestNG](https://img.shields.io/badge/TestNG-7.10-blue)
![Maven](https://img.shields.io/badge/Maven-Build-red?logo=apachemaven)
![Allure](https://img.shields.io/badge/Allure-Reports-blueviolet)
![License](https://img.shields.io/badge/License-MIT-yellow)

A **production-grade, enterprise-level** BDD Test Automation Framework built with **Selenium 4**, **Cucumber**, and **TestNG**. Designed with **SOLID principles**, **clean architecture**, and **industry best practices** for scalable, maintainable, and reliable test automation.

---

## 📋 Table of Contents

- [Architecture Overview](#-architecture-overview)
- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [Prerequisites](#-prerequisites)
- [Setup & Installation](#-setup--installation)
- [Running Tests](#-running-tests)
- [Tag-Based Execution](#-tag-based-execution)
- [Environment Configuration](#-environment-configuration)
- [CI/CD Pipeline](#-cicd-pipeline)
- [Reporting](#-reporting)
- [Key Features](#-key-features)
- [Test Scenarios](#-test-scenarios)
- [Author](#-author)

---

## 🏗 Architecture Overview

```
┌──────────────────────────────────────────────────────────────┐
│                    TEST EXECUTION LAYER                       │
│  ┌──────────┐  ┌──────────────┐  ┌────────────────────────┐ │
│  │  TestNG   │  │  Cucumber    │  │  GitHub Actions CI/CD  │ │
│  │  Runner   │  │  Features    │  │  Pipeline              │ │
│  └────┬─────┘  └──────┬───────┘  └────────────────────────┘ │
│       │               │                                      │
│  ┌────▼───────────────▼─────────────────────────────────────┐│
│  │              STEP DEFINITIONS + HOOKS                     ││
│  │  (Glue code binding Gherkin to Page Objects)              ││
│  └────┬──────────────────────────────────────────────────────┘│
│       │                                                       │
│  ┌────▼──────────────────────────────────────────────────────┐│
│  │                 PAGE OBJECT LAYER                          ││
│  │  HomePage │ LoginPage │ SignupPage │ ProductsPage │ etc.   ││
│  └────┬──────────────────────────────────────────────────────┘│
│       │                                                       │
│  ┌────▼──────────────────────────────────────────────────────┐│
│  │               BASE PAGE (Abstract)                         ││
│  │  click() │ type() │ getText() │ waitForVisible() │ etc.    ││
│  └────┬──────────────────────────────────────────────────────┘│
│       │                                                       │
│  ┌────▼──────────────────────────────────────────────────────┐│
│  │            CORE FRAMEWORK LAYER                            ││
│  │  DriverManager │ DriverFactory │ ConfigReader │ Utils      ││
│  │  (ThreadLocal)   (Factory)       (Singleton)    (Static)   ││
│  └───────────────────────────────────────────────────────────┘│
└──────────────────────────────────────────────────────────────┘
```

---

## 🛠 Tech Stack

| Technology | Version | Purpose |
|---|---|---|
| **Java** | 17 | Programming Language |
| **Selenium** | 4.27 | Browser Automation |
| **Cucumber** | 7.20 | BDD Framework |
| **TestNG** | 7.10 | Test Runner & Assertions |
| **Maven** | 3.x | Build & Dependency Management |
| **WebDriverManager** | 5.9 | Automatic Driver Management |
| **Log4j2** | 2.24 | Centralized Logging |
| **Allure** | 2.29 | Rich Test Reporting |
| **Apache Commons CSV** | 1.12 | CSV Data Processing |
| **Jackson** | 2.18 | JSON Data Processing |
| **GitHub Actions** | - | CI/CD Pipeline |

---

## 📁 Project Structure

```
SeleniumCucumber-BDD-Enterprise-Framework/
├── .github/
│   └── workflows/
│       └── ci.yml                          # GitHub Actions CI/CD pipeline
├── src/
│   ├── main/java/com/wbandara/enterprise/
│   │   ├── base/
│   │   │   └── BasePage.java               # Abstract base page with reusable methods
│   │   ├── constants/
│   │   │   ├── EndpointConstants.java       # URL endpoint constants
│   │   │   └── FrameworkConstants.java      # Framework configuration constants
│   │   ├── driver/
│   │   │   ├── DriverFactory.java           # WebDriver factory (Chrome, Firefox, Edge)
│   │   │   └── DriverManager.java           # ThreadLocal WebDriver management
│   │   ├── exceptions/
│   │   │   ├── ElementNotFoundException.java
│   │   │   ├── FrameworkException.java      # Base framework exception
│   │   │   └── PageNotFoundException.java
│   │   ├── pages/
│   │   │   ├── CartPage.java
│   │   │   ├── CheckoutPage.java
│   │   │   ├── HomePage.java
│   │   │   ├── LoginPage.java
│   │   │   ├── ProductsPage.java
│   │   │   └── SignupPage.java
│   │   └── utils/
│   │       ├── ConfigReader.java            # Environment-based config reader
│   │       ├── LoggerUtils.java             # Log4j2 wrapper
│   │       ├── ScreenshotUtils.java         # Screenshot capture & Allure attach
│   │       └── WaitUtils.java               # Explicit wait utilities
│   └── test/
│       ├── java/com/wbandara/enterprise/
│       │   ├── dataproviders/
│       │   │   ├── CsvDataReader.java       # CSV test data reader
│       │   │   └── JsonDataReader.java      # JSON test data reader
│       │   ├── hooks/
│       │   │   └── Hooks.java               # Cucumber @Before/@After hooks
│       │   ├── runners/
│       │   │   ├── FailedTestRunner.java     # Rerun failed scenarios
│       │   │   └── TestRunner.java           # Main test runner
│       │   └── stepdefinitions/
│       │       ├── CartSteps.java
│       │       ├── CheckoutSteps.java
│       │       ├── HomeSteps.java
│       │       ├── LoginSteps.java
│       │       ├── ProductSteps.java
│       │       └── SignupSteps.java
│       └── resources/
│           ├── config/
│           │   ├── qa.properties             # QA environment config
│           │   └── uat.properties            # UAT environment config
│           ├── features/
│           │   ├── add_to_cart.feature
│           │   ├── checkout.feature
│           │   ├── product_search.feature
│           │   ├── remove_from_cart.feature
│           │   ├── ui_validation.feature
│           │   ├── user_login.feature
│           │   ├── user_logout.feature
│           │   └── user_registration.feature
│           ├── testdata/
│           │   ├── products.json
│           │   └── users.csv
│           ├── allure.properties
│           └── log4j2.xml
├── .gitignore
├── pom.xml
├── testng.xml
└── README.md
```

---

## ✅ Prerequisites

- **Java JDK 17** or higher
- **Apache Maven 3.8+**
- **Chrome** or **Edge** browser installed
- **IntelliJ IDEA** (recommended) or any Java IDE
- **Git**

---

## ⚙ Setup & Installation

1. **Clone the repository:**
```bash
git clone https://github.com/wbandara-tech/SeleniumCucumber-BDD-Enterprise-Framework.git
cd SeleniumCucumber-BDD-Enterprise-Framework
```

2. **Install dependencies:**
```bash
mvn clean install -DskipTests
```

3. **Verify setup:**
```bash
mvn compile
```

---

## ▶ Running Tests

### Run all tests:
```bash
mvn clean test
```

### Run with specific environment:
```bash
mvn clean test -Denv=qa
mvn clean test -Denv=uat
```

### Run with specific browser:
```bash
mvn clean test -Dbrowser=chrome
mvn clean test -Dbrowser=edge
mvn clean test -Dbrowser=firefox
```

### Run in headless mode:
```bash
mvn clean test -Dheadless=true
```

### Run with combined options:
```bash
mvn clean test -Denv=qa -Dbrowser=chrome -Dheadless=true
```

---

## 🏷 Tag-Based Execution

### Run smoke tests:
```bash
mvn clean test -Dcucumber.filter.tags="@smoke"
```

### Run regression tests:
```bash
mvn clean test -Dcucumber.filter.tags="@regression"
```

### Run sanity tests:
```bash
mvn clean test -Dcucumber.filter.tags="@sanity"
```

### Run specific feature tests:
```bash
mvn clean test -Dcucumber.filter.tags="@login"
mvn clean test -Dcucumber.filter.tags="@cart"
mvn clean test -Dcucumber.filter.tags="@checkout"
mvn clean test -Dcucumber.filter.tags="@search"
mvn clean test -Dcucumber.filter.tags="@registration"
mvn clean test -Dcucumber.filter.tags="@ui"
```

### Run with tag combinations:
```bash
mvn clean test -Dcucumber.filter.tags="@smoke and @login"
mvn clean test -Dcucumber.filter.tags="@regression and not @e2e"
```

---

## 🌍 Environment Configuration

The framework supports multiple environments through property files:

| Property | Description | Default |
|---|---|---|
| `base.url` | Application base URL | `https://automationexercise.com` |
| `browser` | Browser to use | `chrome` |
| `headless` | Run in headless mode | `true` |
| `implicit.wait` | Implicit wait (seconds) | `10` |
| `explicit.wait` | Explicit wait (seconds) | `15` |
| `page.load.timeout` | Page load timeout (seconds) | `30` |
| `screenshot.on.failure` | Capture screenshot on failure | `true` |

---

## 🔄 CI/CD Pipeline

The project includes a **GitHub Actions** workflow that:

1. ✅ Triggers on push/PR to `main` branch
2. ✅ Sets up Java 17 on `ubuntu-latest`
3. ✅ Caches Maven dependencies
4. ✅ Installs Chrome browser
5. ✅ Runs smoke tests in headless mode
6. ✅ Generates Allure reports
7. ✅ Uploads artifacts (reports, screenshots, logs)

---

## 📊 Reporting

### Generate Allure Report:
```bash
mvn allure:serve
```

### Generate report without opening:
```bash
mvn allure:report
```

Reports are available in:
- **Allure:** `target/site/allure-maven-plugin/`
- **Cucumber HTML:** `target/cucumber-reports/cucumber.html`
- **Cucumber JSON:** `target/cucumber-reports/cucumber.json`

---

## ⭐ Key Features

| # | Feature | Description |
|---|---|---|
| 1 | **Thread-Safe WebDriver** | ThreadLocal implementation for parallel execution |
| 2 | **Parallel Execution** | TestNG DataProvider with configurable thread count |
| 3 | **Cross-Browser** | Chrome, Firefox, Edge support |
| 4 | **Multi-Environment** | QA, UAT environment configurations |
| 5 | **Screenshot on Failure** | Timestamped screenshots with Allure attachment |
| 6 | **Explicit Wait Wrapper** | Centralized wait utilities |
| 7 | **Centralized Logging** | Log4j2 with console and file appenders |
| 8 | **Failed Test Rerun** | Dedicated runner for failed scenarios |
| 9 | **Custom Exceptions** | Framework-specific exception hierarchy |
| 10 | **Tag-Based Execution** | @smoke, @regression, @sanity tag support |
| 11 | **Scenario Outline** | Data-driven testing with Examples tables |
| 12 | **Data-Driven Testing** | Cucumber DataTable, CSV, JSON support |
| 13 | **Page Object Model** | Clean POM with base page abstraction |
| 14 | **SOLID Principles** | SRP, OCP, LSP, ISP, DIP compliance |

---

## 🧪 Test Scenarios

| # | Scenario | Tags |
|---|---|---|
| 1 | User Registration | `@registration` `@smoke` |
| 2 | Login (Valid + Invalid) | `@login` `@smoke` |
| 3 | Logout | `@logout` `@smoke` |
| 4 | Add Product to Cart | `@cart` `@smoke` |
| 5 | Remove Product from Cart | `@cart` `@remove` |
| 6 | Search Product | `@search` `@smoke` |
| 7 | Checkout Flow | `@checkout` `@e2e` |
| 8 | UI Validation | `@ui` `@smoke` |

---

## 👨‍💻 Author

**Wasantha Bandara**

[![GitHub](https://img.shields.io/badge/GitHub-wbandara--tech-181717?logo=github)](https://github.com/wbandara-tech)

---

## 📄 License

This project is licensed under the MIT License.

---

<p align="center">
  <b>⭐ If you found this project helpful, please give it a star! ⭐</b>
</p>

