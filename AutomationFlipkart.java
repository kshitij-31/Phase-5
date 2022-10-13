package ecommerce;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class AutomationFlipkart{
	private static String url = "https://www.flipkart.com/";
	@Test(groups = "Chrome")
	public void LaunchChrome() {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Selenium\\chromedriver_win32 (3)\\chromedriver.exe");
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test(groups = "Chrome", dependsOnMethods = "LaunchChrome")
	public void f() {
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
//		driver.get("https://www.flipkart.com/");
		driver.get(url);
		pageLoadtime(driver, url);
		// close button
		screenshot(driver,"loginformDisplaycc");
		driver.findElement(By.xpath("/html/body/div[2]/div/div/button")).click();
		// mobile category
		screenshot(driver,"urldisplaycc");
		driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[2]/div/div/div[3]/a/div[2]")).click();

		checkImageLoaded(driver);
		
		ScrollHeight(driver);
	}
	@Test(groups = "Firefox")
	public void LaunchFirefox() {
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\Hp\\eclipse-workspace\\geckodriver-v0.31.0-win64\\geckodriver.exe");
		try {
			Thread.sleep(4000);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	@Test(groups="Firefox", dependsOnMethods="LaunchFirefox")
	public void firefox() {
		WebDriver driver = new FirefoxDriver();
		driver.manage().window().maximize();
//		driver.get("https://www.flipkart.com/");
		driver.get(url);
		pageLoadtime(driver, url);
		// close button
		screenshot(driver,"loginformDisplayff");
		driver.findElement(By.xpath("/html/body/div[2]/div/div/button")).click();
		// mobile category
		screenshot(driver,"urldisplayff");
		driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[2]/div/div/div[3]/a/div[2]")).click();
		screenshot(driver,"mblCategoryff");
		checkImageLoaded(driver);
		
		ScrollHeight(driver);
		
	}

	private static void ScrollHeight(WebDriver driver) {
		// TODO Auto-generated method stub
		try {
		    long lastHeight = (long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight");

		    while (true) {
		        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
		        Thread.sleep(2000);

		        long newHeight = (long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight");
		        if (newHeight == lastHeight) {
		            break;
		        }
		        lastHeight = newHeight;
		    }
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		
	}

	private static void checkImageLoaded(WebDriver driver) {

		// iphone13 click
		WebDriverWait wait = new WebDriverWait(driver, Duration .ofSeconds(6));
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//*[@id=\"container\"]/div/div[3]/div[1]/div/div/p[3]/a[7]")));
		driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[3]/div[1]/div/div/p[3]/a[7]")).click();

		//driver.get(url);
		// identify image
		WebElement i = driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[3]/div[1]/div[1]/div[1]/div/div[1]/div[2]/div[1]/div[2]/img"));
		// Javascript executor to check image
		Boolean p = (Boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].complete "
				+ "&& typeof arguments[0].naturalWidth != \"undefined\" " + "&& arguments[0].naturalWidth > 0", i);

		// verify if status is true
		if (p) {
			System.out.println("Image Loaded");
			screenshot(driver,"imgLoaded");
		} else {
			System.out.println("Image Not Loaded");
		}
	}

	private static void pageLoadtime(WebDriver driver, String url) {
		long s = System.currentTimeMillis();
		// URL launch
		driver.get(url);
		// verify page is loaded
		WebDriverWait wait = new WebDriverWait(driver, Duration .ofSeconds(6));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[2]/div/div/button")));// close button
		// capture time after page load
		long e = System.currentTimeMillis();
		// compute time
		long r = e - s;
		System.out.println("Page load time in milliseconds: " + r);
		screenshot(driver,"pageLoad");

	}
	public static void screenshot(WebDriver driver,String screenshotName){
		  TakesScreenshot ts = (TakesScreenshot)driver;
		   File scr = ts.getScreenshotAs(OutputType.FILE);
		   try {
				FileUtils.copyFile(scr, new File(screenshotName+".png"));
				System.out.println("Screenshot taken");
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	

	
	
}