import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class AirArabiaLogin {

	private AirArabiaLogin(String browser) {
		WebDriver driver = null;
		if(browser.equalsIgnoreCase("firefox")) {
			// TODO Auto-generated method stub
			System.out.println("Inside Selenium tests");
			// Setting system properties of FirefoxDriver
			System.setProperty("webdriver.gecko.driver","C:\\Selenium\\geckodriver-v0.30.0-win64\\geckodriver.exe"); 
			System.out.println("Inside Selenium tests 2");
			driver = new FirefoxDriver();
		}else if(browser.equalsIgnoreCase("chrome")) {
			// TODO Auto-generated method stub
			// Setting system properties of FirefoxDriver
			System.setProperty("webdriver.chrome.driver","C:\\Selenium\\chromedriver_win32\\chromedriver.exe"); 
			System.out.println("Inside Selenium tests CHROME2");
			driver = new ChromeDriver();
		}

		if(driver != null)
			seleniumTest(driver);
	}

	public static void main(String[] args) {
		AirArabiaLogin airArabiaLogin = new AirArabiaLogin("chrome");
		AirArabiaLogin airArabiaLoginFirefox = new AirArabiaLogin("firefox");
	}

	private void seleniumTest(WebDriver driver) {

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		try {
			//Going to Air arabia
			driver.get("https://www.airarabia.com/en");


			//taking the login link and click
			WebElement loginLink = driver.findElement(By.linkText("Login"));
			loginLink.click();

			//Taking all accordions
			List<WebElement> allinks= driver.findElements(By.cssSelector(".ui-accordion"));
			/*for(WebElement accordion:allinks){
				//Selecting accordion only with Emrirates Text
				if(accordion.getText().contains("Emirates"))
					accordion.click();
			}*/

			for(int i=0; i < allinks.size(); i++) {
				WebElement element = allinks.get(i);
				//Selecting accordion only with Emrirates Text
				if(element.getText().contains("Emirates")) {
					element.click();
					break;
				}
			}

			//Click on the Emirates "Click Here" link
			WebElement clickHere =driver.findElement(By.linkText("Click here"));
			clickHere.click();

			//starts here : Since it is going to next tab taking control of the next tab
			String currentHandle= driver.getWindowHandle();
			System.out.println("currentHandle---->"+currentHandle);
			Set<String> handles=driver.getWindowHandles();
			for(String actual: handles) {
				if(!actual.equalsIgnoreCase(currentHandle)) {
					//Switch to the opened tab
					driver.switchTo().window(actual); 
				}
			}
			//Ends here : Since it is going to next tab taking control of the next tab

			//Check for the email , entering email
			WebElement emailBox = driver.findElement(By.name("email"));
			emailBox.sendKeys("XXXXXX@XXXX.COM");
			emailBox.sendKeys(Keys.TAB);

			//control is in password after TAB click, so entering password
			WebElement activeElement = driver.switchTo().activeElement();//taking active element means where the control is there. in this case it is password field
			activeElement.sendKeys("XXXXXXX");

			//Click on ENTER button
			activeElement.sendKeys(Keys.RETURN);

			//Wait until the navigation menu is visible
			WebDriverWait wait2 = new WebDriverWait(driver, 10);
			wait2.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".navigation-dropdown")));
			driver.findElement(By.cssSelector(".navigation-dropdown")).click();

			//Once element is clicked we need to click book flight , which is inside li tag and ng click is the property name.
			driver.findElement(By.xpath("//li[@ng-click='header.goToBookAFlight()']")).click();


			WebElement radioButtonOneWay = driver.findElement(By.xpath("//label[@for='mod-oneway']"));
			//			WebElement radioButtonReturn = driver.findElement(By.xpath("//label[@for='mod-returnway']"));
			//			wait2.until(ExpectedConditions.elementToBeClickable(radioButtonOneWay));
			radioButtonOneWay.click();

			//Entering mandatory fields
			//Enter the search from value
			driver.findElement(By.id("search-from")).sendKeys("AUH");
			driver.findElement(By.id("search-from")).sendKeys(Keys.DOWN);
			driver.findElement(By.id("search-from")).sendKeys(Keys.RETURN);
			//Enter the search to value
			driver.findElement(By.id("search-to")).sendKeys("COK");
			driver.findElement(By.id("search-to")).sendKeys(Keys.DOWN);
			driver.findElement(By.id("search-to")).sendKeys(Keys.RETURN);

			driver.findElement(By.id("departure-date-search-flight")).click();
			driver.findElement(By.id("departure-date-search-flight")).sendKeys(Keys.RETURN);

			//			WebDriverWait wait4 = new WebDriverWait(driver, 3);
			//			List<WebElement> cellsOfDepartureDate = wait.until(
			//		            ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("section:nth-of-type(1) > .lightpick__days > div")));

			//			DateUtil.clickGivenDay(cellsOfDepartureDate, DateUtil.getCurrentDay());


			//This is from date picker table
			//			WebElement dateWidgetFrom = wait4.until(
			//			    ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("ui-datepicker-calendar"))).get(0);

			//			WebElement departureDateInput = driver.findElement(By.xpath("//input[@ng-model='mod.departureDate']"));
			//			departureDateInput.sendKeys("28/03/2022");

			WebDriverWait wait3 = new WebDriverWait(driver, 3);
			wait3.until(ExpectedConditions.elementToBeClickable(By.id("btn-search-modify-search")));

			//		WebElement dateBox = driver.findElement(By.xpath("//form//input[@name='bdaytime']"));

			driver.findElement(By.id("btn-search-modify-search")).click();


			//quitting the driver
			//		driver.quit();
			System.out.println("Inside Selenium tests 3");
		}catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Inside catch block !!");
			try {
				driver.findElement(By.id("btn-close-modify-search")).click();
				driver.findElement(By.xpath("//h2[@ng-model='header.userProfileVisible']")).click();
				driver.findElement(By.id("btn-logout")).click();
				WebDriverWait wait2 = new WebDriverWait(driver, 10);
				wait2.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn-ok")));
				driver.findElement(By.xpath("//button[@ng-click='confirm()']")).click();

			}catch(Exception ex1) {
				ex1.printStackTrace();
			}
		}finally {
			System.out.println("Inside finally block !!");
			//			driver.findElement(By.id("btn-close-modify-search")).click();
			//			driver.findElement(By.xpath("//h2[@ng-model='header.userProfileVisible']")).click();
			//			driver.findElement(By.id("btn-logout")).click();
			//			WebDriverWait wait2 = new WebDriverWait(driver, 10);
			//			wait2.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn-ok")));
			//			driver.findElement(By.cssSelector("btn-ok")).click();
			driver.quit();
		}

	}

}
