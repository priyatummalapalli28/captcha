package captchaautomationpack;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;
import net.sourceforge.tess4j.Tesseract;

public class OcrSiteViaSelenium3
{
	public static void main(String[] args) throws Exception
	{
		//Open browser
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver=new ChromeDriver();
		//Maximize
		driver.manage().window().maximize();
		//launch site
		driver.get("https://www.incometaxindiaefiling.gov.in/home");
		WebDriverWait wait=new WebDriverWait(driver,20);
		try
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Continue to Homepage']"))).click();
		}
		catch(Exception ex)
		{
		}
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@value,'Login Here')]"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("userName"))).sendKeys("Rohan");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password"))).sendKeys("kumar");
		WebElement e=driver.findElement(By.id("captchaImg"));
		File src=driver.getScreenshotAs(OutputType.FILE);
		//Get coordinates and dimensions of element
		int x=e.getLocation().getX();
		int y=e.getLocation().getY();
		int w=e.getSize().getWidth();
		int h=e.getSize().getHeight();
		BufferedImage bfull=ImageIO.read(src);
		BufferedImage bele=bfull.getSubimage(x, y, w, h);
		ImageIO.write(bele,"png",src);
		File dest=new File("captchaelement.png");
		FileHandler.copy(src, dest);
		Tesseract t=new Tesseract();
		t.setDatapath("E:\\Automation\\AutomationNested\\captchaautomation\\tessdata");
		String text=t.doOCR(dest);
		System.out.println(text);
		driver.findElement(By.name("captchaCode")).sendKeys(text);
		Thread.sleep(5000);
		driver.close();
	}
}
