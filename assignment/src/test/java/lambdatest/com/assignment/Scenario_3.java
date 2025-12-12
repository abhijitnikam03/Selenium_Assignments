package lambdatest.com.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

public class Scenario_3 {
	
	private RemoteWebDriver driver;
    private final String BASE_URL = "https://www.lambdatest.com/selenium-playground";
    private final String SUCCESS_MESSAGE = "Thanks for contacting us, we will get back to you shortly.";


    // REPLACE THESE WITH YOUR ACTUAL LAMBDATEST CREDENTIALS
    private final String USERNAME = "abhijitnikam03"; 
    private final String ACCESS_KEY = "LT_hKl7qpVCxBvfH4Ubfcoglfydny0YCEimN8cYrptmS3ZcLxR"; 
    private final String GRID_URL = "@hub.lambdatest.com/wd/hub";

    // Test Data
    private final String NAME = "Jane Doe";
    private final String EMAIL = "jane.doe@test.com";
    private final String PASSWORD = "password123";
    private final String COMPANY = "Automation Inc.";
    private final String WEBSITE = "www.automation.com";
    private final String CITY = "New York";
    private final String State = "Ohio";
    private final String ADDRESS1 = "123 Test St";
    private final String ADDRESS2 = "Apt 4B";
    private final String ZIPCODE = "10001";
    private final String COUNTRY = "United States";

    @SuppressWarnings("deprecation")
	@BeforeMethod
    @Parameters({"browser"})
    public void setUp(String browser) throws MalformedURLException {
    	
    	
    	DesiredCapabilities capabilities = new DesiredCapabilities();
        
        capabilities.setCapability("browserName", browser);
        capabilities.setCapability("browserVersion", "latest"); 
        capabilities.setCapability("platformName", "Windows 10"); 

        // 2. Define W3C-Compliant LambdaTest Options
        Map<String, Object> ltOptions = new HashMap<>();
        
        // ESSENTIAL: Include credentials inside the lt:options map for modern connection
        ltOptions.put("username", USERNAME); 
        ltOptions.put("accessKey", ACCESS_KEY);
        
        // Project/Build/Name (moved inside lt:options)
        ltOptions.put("project", "LambdaTest Parallel Assignment");
        ltOptions.put("build", "W3C Capabilities Fix");
        ltOptions.put("name", "Test on Windows 10 " + browser);
        
        // 3. Attach LT Options to main Capabilities
        capabilities.setCapability("lt:options", ltOptions); 
        
        // 4. Connect
        // The credentials are now passed via the capabilities map, but we still build the URL for the hub connection point.
        String remoteUrl = "https://" + USERNAME + ":" + ACCESS_KEY + GRID_URL;
        
        // This constructor signature is correct for the capabilities we are building:
        driver = new RemoteWebDriver(new URL(remoteUrl), capabilities);
        
        driver.manage().window().maximize();
        
   } 	  

    @org.testng.annotations.Test
    public void verifyFormSubmission() {
        // --- Step 1: Navigate and Click ---
        driver.get(BASE_URL);
        String sessionId = ((RemoteWebDriver) driver).getSessionId().toString();

        // Click "Input Form Submit" link
        WebElement inputFormLink = driver.findElement(By.linkText("Input Form Submit"));
        inputFormLink.click();

        // --- Step 2 & 3: Initial Validation ---
        
        // Locate the Submit button (it is typically a submit input or button element)
        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Submit']"));
        
        // 2. Click "Submit" without filling in any information
        submitButton.click();

        // 3. Assert "Please fill out this field." error message.
        // The HTML5 validation message is NOT directly accessible by Selenium's getText().
        // Instead, we check if the first required input field (usually 'Name') has the ':invalid' CSS pseudo-class,
        // or a specific attribute indicating an error, which is the reliable way to confirm failure.
        WebElement nameInput = driver.findElement(By.id("name"));

        // The most reliable check for the HTML5 error is usually done via a JS executor, 
        // but often the presence of a specific class (like 'is-invalid' in Bootstrap) or an attribute is checked.
        // For this scenario, we'll check if the 'required' field is present and use a basic attribute check.
        
        // Assert that the 'Name' field is marked as invalid by the browser
        String validationMessage = (String) ((org.openqa.selenium.JavascriptExecutor) driver)
            .executeScript("return arguments[0].validationMessage;", nameInput);

        // The exact message can vary by browser/language, but we check if it's not empty, 
        // or check against the expected English message.
        assertTrue(validationMessage.contains("Please fill out this field."), 
                   "Initial validation failed. Expected HTML5 error message not displayed for Name field.");

        // --- Step 4, 5, & 6: Fill and Final Submit ---
        
        // 4. Fill in Name, Email, and other fields.
        nameInput.sendKeys(NAME);
        driver.findElement(By.id("inputEmail4")).sendKeys(EMAIL);
        driver.findElement(By.id("inputPassword4")).sendKeys(PASSWORD);
        driver.findElement(By.id("company")).sendKeys(COMPANY);
        driver.findElement(By.id("websitename")).sendKeys(WEBSITE);
        driver.findElement(By.id("inputCity")).sendKeys(CITY);
        driver.findElement(By.id("inputAddress1")).sendKeys(ADDRESS1);
        driver.findElement(By.id("inputAddress2")).sendKeys(ADDRESS2);
        driver.findElement(By.id("inputState")).sendKeys(State);
        driver.findElement(By.id("inputZip")).sendKeys(ZIPCODE);

        // 5. From the Country drop-down, select "United States"
        WebElement countryDropdown = driver.findElement(By.name("country"));
        Select selectCountry = new Select(countryDropdown);
        
        // Select using the visible text property
        selectCountry.selectByVisibleText(COUNTRY); 
        
        // 6. Click "Submit"
        submitButton.click();
        
        // --- Step 7: Validate Success ---
        
        // 7. Validate the success message
        // The success message is typically displayed in a div or p tag after submission.
        // The LambdaTest demo usually uses a p tag with class 'success-msg' or similar.
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement successMessageElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Thanks for contacting us, we will get back to you shortly.')]"))
            );
            
            String actualSuccessMessage = successMessageElement.getText().trim();

            assertEquals(SUCCESS_MESSAGE, actualSuccessMessage, 
                         "Final submission validation failed. Success message is incorrect.");
        System.out.println("Test Passed! Form submitted successfully and message validated: " + actualSuccessMessage);
        System.out.println("Final Test ID: " + sessionId);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}