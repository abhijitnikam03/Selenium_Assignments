package lambdatest.com.assignment;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod; // Use TestNG AfterMethod
import org.testng.annotations.BeforeMethod; // Use TestNG BeforeMethod
import org.testng.annotations.Parameters;
import org.testng.annotations.Test; // Use TestNG Test

import io.github.bonigarcia.wdm.WebDriverManager;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class Scenario_1 {

    private RemoteWebDriver driver;
    private final String BASE_URL = "https://www.lambdatest.com/selenium-playground";
    private final String TEST_MESSAGE = "Welcome to LambdaTest";

    // REPLACE THESE WITH YOUR ACTUAL LAMBDATEST CREDENTIALS
    private final String USERNAME = "abhijitnikam03"; 
    private final String ACCESS_KEY = "LT_hKl7qpVCxBvfH4Ubfcoglfydny0YCEimN8cYrptmS3ZcLxR"; 
    private final String GRID_URL = "@hub.lambdatest.com/wd/hub";

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

    @Test
    public void verifySimpleFormMessage() { // Removed throws InterruptedException
        // 1. Open LambdaTest's Selenium Playground
    	String sessionId = ((RemoteWebDriver) driver).getSessionId().toString();
        driver.get(BASE_URL);

        // 2. Click "Simple Form Demo"
        WebElement simpleFormLink = driver.findElement(By.linkText("Simple Form Demo"));
        simpleFormLink.click();
        
        // --- Step 3: Validation ---
        // 3. Validate that the URL contains "simple-form-demo"
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("simple-form-demo"), 
                   "URL validation failed. URL does not contain 'simple-form-demo'.");

        // --- Step 4 & 5: Input ---
        // 5. Use the variable to enter values in the "Enter Message" text box.
        WebElement messageInput = driver.findElement(By.id("user-message"));
        messageInput.sendKeys(TEST_MESSAGE);

        // 6. Click "Get Checked Value".
        WebElement showMessageButton = driver.findElement(By.id("showInput"));
        showMessageButton.click();

        // --- Step 7: Validation ---
        // 7. Validate whether the same text message is displayed in the right-hand panel.
        WebElement outputMessageElement = driver.findElement(By.id("message"));
        String displayedMessage = outputMessageElement.getText();

        assertEquals(TEST_MESSAGE, displayedMessage, 
                     "Validation failed. The displayed message does not match the input message.");
        
        System.out.println("Test Passed on " + ((RemoteWebDriver) driver).getCapabilities().getBrowserName() + 
                           "! Input Message: '" + TEST_MESSAGE + "' matches Displayed Message: '" + displayedMessage + "'.");
        System.out.println("Final Test ID: " + sessionId);
    }

    @AfterMethod // Use TestNG AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}