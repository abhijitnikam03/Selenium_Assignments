# LambdaTest Cloud Selenium Grid Parallel Execution Assignment

This project demonstrates parallel test execution across multiple browsers/OS combinations using TestNG and the LambdaTest Cloud Selenium Grid.

The project is configured to run three scenarios (`Scenario_1`, `Scenario_2`, and `Scenario_3`) concurrently on **Windows 10 Chrome** and **Firefox**, generating unique Test IDs for submission.

## 🚀 Single-Click Setup (Gitpod Dev Environment)

Click the button below to launch the project in a pre-configured Gitpod workspace:

[![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/YOUR_GITHUB_USERNAME/YOUR_REPO_NAME)

*(Note: Replace `YOUR_GITHUB_USERNAME` and `YOUR_REPO_NAME` in the URL above with your actual repository details.)*

---

## 🔑 Prerequisites & Credentials Setup

Since this assignment uses the LambdaTest Cloud Grid, you must provide your credentials for the tests to run.

**The code reads your credentials from Environment Variables.**

### **Steps to Set Environment Variables in Gitpod:**

1.  **Stop** the running Gitpod workspace (if it's already open).
2.  Go to your Gitpod dashboard settings.
3.  Navigate to the **Variables** section.
4.  Add the following two variables, scoped to your repository:
    * **Name:** `LT_USERNAME`
    * **Value:** Your LambdaTest Username (email address)
    * **Name:** `LT_ACCESS_KEY`
    * **Value:** Your LambdaTest Access Key

5.  Restart the Gitpod workspace.

## ▶️ Instructions to Run the Test Suite

The tests are configured to run in parallel using the `testng.xml` file.

1.  **Open Terminal:** Ensure the terminal is open in your Gitpod workspace.
2.  **Run Command:** Execute the following command to start the parallel TestNG execution:

    ```bash
    # This command compiles the code and runs the TestNG suite defined in testng.xml
    mvn clean test
    ```

## 📝 Submission Requirements

After running the tests, the console output will contain the required Test IDs.

1.  **Parallel Execution:** The `testng.xml` runs 3 scenarios across 2 browsers, resulting in 6 parallel sessions.
2.  **Test ID Reporting:** The `BaseTest.java` class is configured to print the unique LambdaTest Session ID for every completed test.
3.  **Submission:** Copy **all six** `Final Test ID` values from the console output and include them in your final submission document.

***

## 2. `.gitpod.yml` (Gitpod Environment Configuration)

This file tells Gitpod to set up a Java environment, install Maven, and display the TestNG XML structure upon startup.

**File Name:** `.gitpod.yml`

```yaml
# Gitpod configuration file

# 1. Define the base image (Must include Java and necessary tools)
image: gitpod/workspace-java-17

# 2. Command to run immediately after the workspace is ready
tasks:
  - name: Setup & Build
    init: |
      # Install TestNG CLI (optional but helpful)
      # echo "Setting up Maven environment..."
      
    # Command to execute when the environment is ready
    command: |
      echo "--------------------------------------------------------"
      echo "Gitpod environment is ready. Running initial Maven build..."
      mvn clean install -DskipTests
      echo "Build complete. Run 'mvn test' to execute the test suite."
      echo "--------------------------------------------------------"
      echo "TestNG XML structure (for reference):"
      cat testng.xml
      
# 3. Port Configuration (Not strictly needed for Selenium Grid connection, but good practice)
ports:
  - port: 8080
    onOpen: ignore
