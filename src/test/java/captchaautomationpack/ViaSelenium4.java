package captchaautomationpack;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;
import net.sourceforge.tess4j.Tesseract;

public class ViaSelenium4
{
	public static void main(String[] args) throws Exception
	{
		ChromeOptions co=new ChromeOptions();
		co.addArguments("--disable-notifications");
		//Open browser
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver=new ChromeDriver(co);
		//Maximize
		driver.manage().window().maximize();
		//launch site
		driver.get("https://www.samsung.com/in/");
		WebDriverWait wait=new WebDriverWait(driver,20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Samsung']/parent::*")));
		while(2>1)
		{
			if(driver.findElement(By.xpath("(//*[contains(text(),'Pre-registration')])[1]")).isDisplayed())
			{
				driver.findElement(By.xpath("(//*[contains(text(),'Pre-registration')])[1]")).click();
				break;
			}
			else
			{
				driver.findElement(By.xpath("//*[@aria-label='Previous slide']")).click();
			}
		}
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath("//iframe[@class='frame-a1']")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("tbName"))).sendKeys("Anusha");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("tbMobile"))).sendKeys("8499845069");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("tbEmail"))).sendKeys("anushauks@gmail.com");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("tbPinCode"))).sendKeys("500035");
		WebElement e=driver.findElement(By.name("tbPinCode"));
		driver.executeScript("arguments[0].scrollIntoView();",e);
		File src=driver.findElement(By.id("imgCaptcha")).getScreenshotAs(OutputType.FILE);
		File dest=new File("samsungcaptcha.png");
		FileHandler.copy(src, dest);
		Tesseract t=new Tesseract();
		t.setDatapath("E:\\Automation\\AutomationNested\\captchaautomation\\tessdata");
		String text=t.doOCR(dest);
		Thread.sleep(3000);
		System.out.println(text);
		driver.findElement(By.name("txtCaptcha")).sendKeys(text);
		driver.switchTo().defaultContent();
		Thread.sleep(5000);
		driver.close();	
	}
}
