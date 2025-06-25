package Base_Setup;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;



public class Base_Class extends Base_Configuration{
	@BeforeClass
	public void setup() {

		 String browser="chrome";

		if (browser.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		} else {
			System.out.println("No Such Browser Found. Try Again");
		}
		maximizeWindow();
		DeleteCookies();
		
	}

	//@AfterClass
	public void tearDown() {
		closeAllWindows();
	}
}
