package lambdatest.com.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Scenario_2 {

	private RemoteWebDriver driver;
    private final String BASE_URL = "https://www.lambdatest.com/selenium-playground";
    private final String TARGET_VALUE = "95"; 
    private final int MAX_WAIT_SECONDS = 10;
    
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


    @org.testng.annotations.Test
    public void setSliderValueTo95() throws InterruptedException {
    	
        driver.get(BASE_URL);
        String sessionId = ((RemoteWebDriver) driver).getSessionId().toString();
        WebElement dragDropLink = driver.findElement(By.linkText("Drag & Drop Sliders"));
        dragDropLink.click();
        
        WebElement sliderInput = driver.findElement(By.xpath("//input[@type='range' and @value='15']"));
        
        
        Actions move = new Actions(driver);
        
        move.clickAndHold(sliderInput)
        .moveByOffset(213, 0)
        .release()
        .perform();
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        
        String actualValue = driver.findElement(By.xpath("//output[@id='rangeSuccess' and text()='95']")).getText();
        
        assertEquals(TARGET_VALUE, actualValue, 
                     "Slider value validation failed. Expected: " + TARGET_VALUE + ", but found: " + actualValue);
        
        System.out.println("Test Passed! Slider value successfully set and validated to: " + actualValue);
        System.out.println("Final Test ID: " + sessionId);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}