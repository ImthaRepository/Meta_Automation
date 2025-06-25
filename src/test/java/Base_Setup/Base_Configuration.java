package Base_Setup;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.RollingFileAppender;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import io.github.bonigarcia.wdm.WebDriverManager;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class Base_Configuration {
	  public static WebDriver driver;

		public String projectPath() {
			//for get project path we can also use ./ notation
			String path = System.getProperty("user.dir");
			return path;
		}
		/*public String timeStamp() {
			Date currentdate=new Date();
			//String stamp=currentdate.toString().replace(" ", "-").replaceAll(":", "-");
			String stamp=currentdate.toString().replace(" ", "-");
			return stamp;
		}*/
		public String timeStamp() {
			String stamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
			return stamp;
		}
		
		//Creation of new folder
		public File createFolder(String foldername) {				
		    String folderName=System.getProperty("user.dir")+foldername;
		    File Folder=new File(folderName);
		   // boolean created = Folder.mkdirs();
		    return Folder;
		}
		

		public String getPropertyFileValue(String key) throws FileNotFoundException, IOException {

			Properties properties = new Properties();
			properties.load(new FileInputStream(projectPath()+"\\input.properties"));
			Object object = properties.get(key);
			String value = (String) object;
			return value;

		}

		
		
	//<------------------------------------------------------- Browser Events-------------------------------------------------------->	
		
	    // Browser Choose
		   public void browser(String browser) {
			   switch (browser) {
			      case "chrome":
				    driver = new ChromeDriver();
				    WebDriverManager.chromedriver().setup();
				    break;

			      case "firefox":
				    driver = new FirefoxDriver();
				    WebDriverManager.firefoxdriver().setup();
				    
				    break;

			      case "edge":
				    driver = new EdgeDriver();
				    WebDriverManager.edgedriver().setup();
				    break;

			      default:
				    break;
			    }
		   }
		   
		// Launch Chrome Browser
		    public  WebDriver launchchromeBrowser() {
			     WebDriver driver = new ChromeDriver();
			     return driver;
		    }
		    
		 // ignore SSL certificate ( exception )
		    public void ignoreInsecureCeritificat() {
		    	ChromeOptions opt =new ChromeOptions();
		    	opt.setAcceptInsecureCerts(true);
		    	driver=new ChromeDriver(opt);
		    }
		    
		// Enter url
		   public static void getUrl(String url) {
					driver.get(url);
		    }
		   
		// Navigate to url
		   public static void navigateToUrl(String url) {
			   driver.navigate().to(url);
		   }
		   
		//  Refresh page
			public void refresh() {
				driver.navigate().refresh();
			}
			
		//	Navigate Backward
			public void navigateBack() {
				driver.navigate().back();
			}
			
		// Navigate Forward	
		   public void navigateForward() {
				driver.navigate().forward();
			}
			
	    // Minimize Window
			public static void minmizeWindow() {
				driver.manage().window().minimize();
			}
			
		// Set the Size of the Window
		   public static void windowSize(int width, int height) {
				Dimension d= new Dimension(width , height);
				driver.manage().window().setSize(d);
				return;
			}

		 // Maximize Window
			public static void maximizeWindow() {
				driver.manage().window().maximize();
			}
			
		//  Delete All the Cookies
			public static void DeleteCookies() {
				driver.manage().deleteAllCookies();
			}
			
		//  Close all windows
			public static void closeAllWindows() {
				driver.quit();
			}

		//  Close all current window
			public void closeCurrentwindow() {
				driver.close();
			}
			
			
	//<------------------------------------------------     Locators --------------------------------------------------------------->

		//  Find locator by id :
			public static WebElement locatorId(String id) {
				WebElement findElement = driver.findElement(By.id(id));
				return findElement;
			}

		//  Find locator by name :
			public static WebElement locatorName(String name) {
				WebElement findElement = driver.findElement(By.name(name));
				return findElement;
			}

		//  Find locator by ClassName :
			public static WebElement locatorClassName(String value) {
				WebElement findElement = driver.findElement(By.className(value));
				return findElement;
			}

	    //  Find locator by xpath :
			public static WebElement locatorXpath(String xpathExpression) {
				WebElement findElement = driver.findElement(By.xpath(xpathExpression));
				return findElement;
			}
			
		//  Find number of elements using xpath for loop Action
		    public static List<WebElement> multipleelements(String xpathExpression) {
				List<WebElement> listElement = driver.findElements(By.xpath(xpathExpression));
				return listElement;
		    }
		    
		 // Find locator by CSS selector
		    public static WebElement locatoreCSS(String CSSexpresion) {
		    	WebElement findElement = driver.findElement(By.cssSelector(CSSexpresion));
		    	return findElement;
		    }
				
			
			
	//<------------------------------------------Waits And Sleep Events------------------------------------------------------------->
			
			
		 //  Sleep
			 public void sleep(int time ) throws InterruptedException {
				Thread .sleep(time);
				return;
			 }
			
		 //  Implicit Wait
			 public static void implicitwait() {
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
			 }

	     //  Explicit wait Until Visible using Web element
			 public WebDriverWait WaitUntilVisibleElement(WebElement element, int time) {
				WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(time));
				wait.until(ExpectedConditions.visibilityOf(element));
				return wait;
			 }
			 
		 //  Explicit wait Until Visible using locator
			 public WebDriverWait WaitUntilVisibleLocator(String locator, int time) {
					WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(time));
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
					return wait;
			 }
			 
		 //  Explicit wait Until Clickable
			 public WebDriverWait WaitUntilClcikable(WebElement element) {
				WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
				wait.until(ExpectedConditions.elementToBeClickable(element));
				return wait;
			 } 
			 
		//   Explicit wait Until Title Visible using locator
			 public WebDriverWait WaitUntilVisibleTitle(String Title) {
				 WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
				 wait.until(ExpectedConditions.titleIs(Title));
				 return wait;
			  }
							
		   
		/* //  fluent wait
			 private  FluentWait fluentwait() {
				 FluentWait wait = new FluentWait(driver).withTimeout(Duration.ofSeconds(30)).pollingEvery(Duration.ofSeconds(5)).ignoring(NoSuchElementException.class);	 
				 
				 wait.until(new FunctionCoverage(getProjectPath(), null, null))
				 reutrn wait;
			 }*/

	//<---------------------------------------------------------Basic Operation in Web pages------------------------------------------->
		
			 
			 		 
		//  Print the Value	 
			public void print(String text) {
			    System.out.println(text);
		    }
			
		//  print the Boolean
			public void printBoolean(Boolean b) {
				System.out.println(b);
			}
		
		//  Get the text from the webelement
		    public String getText(WebElement element) {
			   String text = element.getText();
			   return text;
		    }
		    
		//  Get the text from the webelement
		    public String getInnerText(WebElement element) {
			   String text = element.getAttribute("innerText");
			   return text;
		    }
		
		//  Insert Value In textBox
		    public static void sendKeys(WebElement element, String Data) {
			   element.sendKeys(Data);
		    }
		    
		//  Clear Textbox
			public void clear(WebElement element) {
				element.clear();
			}

		//  Click Button
		    public static void click(WebElement element) {
			   element.click(); 
		    }

		//  Click OK In Alert
		    public void acceptAlert(Alert alert) {
			   alert.accept();
		    }

		//  Click Cancel On Alert
		    public void declineAlert(Alert alert) {
			   alert.dismiss();
		    }

		//  Get the Inserted value in textbox
		    public String getInsertedValue(WebElement element, String Data) {
			   String attribute = element.getAttribute(Data);
			   return attribute;
		    }
		    
		 // Insert values and enter
			public void insertValueAndEnter(WebElement element, String value) {
				element.sendKeys(value, Keys.ENTER);
			}	

		//  Get the title
		    public String getTitle() {
			   String title = driver.getTitle();
			   return title;
		    }

		//  Get the Current URL
		    public String fetchUrl() {
			  String currentUrl = driver.getCurrentUrl();
			  return currentUrl;
		    }
		    
		//  Assert for Actual and Expected
			public void Assert(String Actual,String Expect) {
			   org.testng.Assert.assertEquals(Actual, Expect);
			   return;
			}
			   
		//  Assert for Test Pass
		    public void Assertpass() {
			   org.testng.Assert.assertTrue(true);
			}
			   
		//  Assert for Test Fail
			public void AssertFail() {
			   org.testng.Assert.assertTrue(false);
			}
			       
			   
		//  For Loop Print Expression
			public void forLoopPrint(List<WebElement> elements,String element ,int startCount,int loopInterval) throws InterruptedException {
				 for (int k = startCount; k <= elements.size(); k++) {
					 WebElement interest = driver.findElement(By.xpath(("("+element+")["+k+"]")));
					 print(interest.getAttribute("href")+" : "+interest.getAttribute("innerText") );//+" :: "+interest.getAttribute("textContent")
					 sleep(loopInterval);
					 }
			 }
		   
		 // For Loop click Expression
			public void forLoopclick(List<WebElement> elements,String element ,int startCount,int loopInterval) throws InterruptedException {
				for (int k = startCount; k <= elements.size(); k++) {
					 WebElement interest = driver.findElement(By.xpath(("("+element+")["+k+"]")));
					 interest.click();
					 sleep(loopInterval);
					 }
			}

	//<------------------------------------------Basic Java Conversion Code ------------------------------------------------------->		
			
		 // conversion of string into integer
			public int string_to_int(String str) {
				int n=Integer.parseInt(str);
				return n;
			}
			
	    // Conversion of int into String
			public String int_into_String (int num) {
				String s=Integer.toString(num);
				return s;
			}
			
	    // Reverse the String
			public String reverse_String (String data) {
				StringBuilder rev = new StringBuilder(data).reverse();
				return rev.toString();
			}
			
		// Reverse the Number
			public int reverse_Number(int num) {
				int rev = 0, r, a;
				
				a=num;
				while(num>0)
				{
					r= num%10;
					rev=rev*10+r;
					num=num/10;		
				}
				return rev;
			}
			
		// highest number among the array
			public int highest_Number(int [] array) {
				Arrays.sort(array);
				return array[array.length-1];
			}
			
		// Lowest number among the array
			public int lowest_Number(int [] array) {
				
				Arrays.sort(array);
				return array[0];
			}
			
		// Find Odd or Even Number
			public void odd_or_Even_Boolean(int num) {
				if (num % 2 == 0) {
					print(num + " : The Number is Even");
				} else {
					print (num + " : The Number is Odd");
				}
				return;
			}
			
		// Find Prime Number
			public void isPrimeNumber (int num) {
				if (primeBoolean(num)) {
					print(num +" : Number is Prime Number");
				} else {
					print(num +" : Number is Not a Prime Number");
				}
			}
			
			public static boolean primeBoolean(int num) {
				 for (int i = 2; i <= num/2; i++) {
					if (num % i==0) {
						return false;
					}
				}
				 return true;
			 }
	//<----------------------------------------JavaScript Functions like Scroll the web page-------------------------------------->
		
		 //  Get Title using JS
			public String GetTitleJS() {
				 JavascriptExecutor executor = (JavascriptExecutor) driver;
			     String Title = executor.executeScript("return document.Title;").toString();
			     return Title;     
			}

		 //  Insert value using JS
		     public void SendKeysJS(WebElement element, String data) {
			     JavascriptExecutor executor = (JavascriptExecutor) driver;
			     executor.executeScript("[arguments[0].setAttribute('value',' " + data + " ')", element);
	         }
		     
		 //  Insert Value to the script
		     public void InsertValueJS(WebElement element, String data) {
			     JavascriptExecutor executor = (JavascriptExecutor) driver;
			     executor.executeScript("arguments[0].value = arguments[1];", element, data);
			   //  ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", element, data);		
	         }
		     
		 //  Fetch Attribute value using JS
		     public void getAttributeJS(WebElement element) {
			     JavascriptExecutor executor = (JavascriptExecutor) driver;
			     executor.executeScript("[arguments[0].getAttribute('value')", element);
	         }
			    
		 //  Click button using JS
		     public void clickJS(WebElement element) {
		        JavascriptExecutor executor=(JavascriptExecutor) driver;
		        executor.executeScript("arguments[0].click()",element );
		     }
		 
		 //  Scroll the Web Page JS
		     public void ScrollJS(int horizontal,int vertical) {
			    JavascriptExecutor js=(JavascriptExecutor)driver;
			    js.executeScript("window.scrollBy("+horizontal+","+vertical+")");
		     }
		     
		
		 // Scroll into view JS
		    public void MoveToElementJS(WebElement element) {
		    	JavascriptExecutor executor=(JavascriptExecutor) driver;
		        executor.executeScript("arguments[0].scrollIntoView(true);",element);
		    }
		    
		 // Color the textbox background using JS
			public void setbackgroundColorJS(WebElement element ,String color) {
			    JavascriptExecutor executor=(JavascriptExecutor) driver;
			    executor.executeScript("arguments[0].style.background ='"+ color+"'",element);
			}
			
		 // Color the textbox background using JS
			public void setBorderColorJS(WebElement element ,String color) {
				JavascriptExecutor executor=(JavascriptExecutor) driver;
			    executor.executeScript("arguments[0].style.border ='"+ color+"'",element);
			}
			
		 // Generate Alert using JS
			public void SetAlert(String message) {
				JavascriptExecutor executor=(JavascriptExecutor) driver;
			    executor.executeScript("alert('"+ message+"')");
			}
			
		 // Refresh page using JS
			public void RefreshJS() {
				JavascriptExecutor executor=(JavascriptExecutor)driver;
				executor.executeScript("history.go(0)");
			}
			
		 // Scroll to Bottom page using JS
			public void ScrollToBottomJS() {
				JavascriptExecutor executor=(JavascriptExecutor)driver;
				executor.executeScript("window.scrollTo(0,document.body.scrollHeight)");
			}
		    
		 // Color the textbox background border using JS
		/*    public void setTextboxColorJS(WebElement element ,String background, String border) {
		    	JavascriptExecutor executor=(JavascriptExecutor) driver;
		        executor.executeScript("arguments[0].setAttribute('style','background:"+background+";border:"+border+"';",element);
		    }*/
		    
		 
		 // Make the element to be visible (for handling the not visible elements but shown in the DOM)
			public void makeElementToBeVisibleJS(WebElement element) {
				JavascriptExecutor executor=(JavascriptExecutor)driver;
				executor.executeScript("arguments[0].style.display = 'block';", element);
			}
	//<--------------------------------------------------Boolean Operations--------------------------------------------------------->	
		  
		
		// Verify drop down is multi selected?
		   public boolean multiSelected(WebElement element) {
			  Select select = new Select(element);
			  boolean multiple = select.isMultiple();
			  return multiple;
		   }

		
		// Verify element is displayed
		   public boolean isDisplayed(WebElement element) {
		   boolean displayed = element.isDisplayed();
		   return displayed;
		   }

		// Verify element Is enabled
		   public boolean isEnabled(WebElement element) {
		      boolean enabled = element.isEnabled();
			  return enabled;
		   }
		   
		   
		   
	//<-------------------------------------------------------Select Class For DropDown Menus---------------------------------------->
		// verify is selected
		   public boolean isSelected(WebElement element) {
		 	   boolean selected = element.isSelected();
			   return selected;
		   }

		 // Desselect by index
		    public void deselectByIndex(WebElement element, int index) {
			    Select select = new Select(element);
			    select.deselectByIndex(index);
		    }

		 // Desselect by attributevalue
		    public void deselectByValue(WebElement element, String value) {
			    Select select = new Select(element);
			    select.deselectByValue(value);
		    }

		 // Desselect by text
		    public void deselectBytext(WebElement element, String text) {
			   Select select = new Select(element);
			   select.deselectByVisibleText(text);
		    }
		    
		 // Deselect all
		    public void deSelectAll(WebElement element) {
			      Select select = new Select(element);
			      select.deselectAll();
		    }

		 // Select by index
		    public void selectByIndex(WebElement element, int index) {
			   Select select = new Select(element);
			   select.selectByIndex(index);
		     }

		 // Select by attribute value
		    public void selectByValue(WebElement element, String value) {
			    Select select = new Select(element);
			    select.selectByValue(value);
		     }

		 // Select by text
		    public void selectBytext(WebElement element, String text) {
			     Select select = new Select(element);
			     select.selectByVisibleText(text);
		     }
		    
		 // Get all options Drop down as text
			public List<WebElement> getAlloptionsText(WebElement element) {
				Select select = new Select(element);
				List<WebElement> options = select.getOptions();
				return options;
			}

	/*   // 27 Get all options Drop down as Attribute value
			public List<WebElement> getAllOptionsValue(WebElement element) {
				Select select = new Select(element);
				List<WebElement> options = select.getOptions();
				return options;
			}*/

		 // Get First Selected option in Drop down
			public WebElement firstSelectedoption(WebElement element) {
				Select select = new Select(element);
				WebElement firstSelectedOption = select.getFirstSelectedOption();
				return firstSelectedOption;
			}



		    
	//<----------------------------------------------Window And Frame Handle----------------------------------------------------->
		 
		//  Get the parent window
		    public String getTheParentWindow() {
			   String windowHandle = driver.getWindowHandle();
			   return windowHandle;
		    }

		//  Get all the windows
			public Set<String> getAllWindows() {
				Set<String> allwindows = driver.getWindowHandles();
				return allwindows;
			}
		
		//  Switch to Switch window
			public void switchWindow() {
			   String parentid = driver.getWindowHandle();
			   Set<String> allid = driver.getWindowHandles();
				for (String eachid : allid) {
					if (!parentid.equals(eachid)) {
						driver.switchTo().window(eachid);
					}
				}
			}
			
			

		//  Switch to frame by index
			public void switchToFrameIndex(int index) {
			   driver.switchTo().frame(index);
			}

		//  Switch to frame by frame id
			public void switchToframeId(String Id) {
				driver.switchTo().frame(Id);
			}
			
		//  Switch to frame by frame Element locator
			public void switchToframeElement(WebElement element) {
				driver.switchTo().frame(element);
			}
			
		//  Switch to frame to default frame
			public void switchToDefaultFrame() {
			   driver.switchTo().defaultContent();
			}

		

		

	//<--------------------------------------------------------Action Function------------------------------------------------------->
		
		// Click Using Action
		   public void clickAction(WebElement element) {
			   Actions actions = new Actions(driver);
			   actions.click(element).perform();
		   }

		// Mouse over action for single option
		   public void mouseOver(WebElement target) {
			  Actions actions = new Actions(driver);
			  actions.moveToElement(target).perform();;
		   }

		// Drag and drop
		   public void drogAndDrop(WebElement source, WebElement target) {
			  Actions actions = new Actions(driver);
			  actions.dragAndDrop(source, target).perform();
	       }

		// Right click
		   public void rightClick(WebElement element) {
			  Actions actions = new Actions(driver);
			  actions.contextClick(element).perform();
		   }

		// Double click
		   public void doubleClick(WebElement element) {
			  Actions actions = new Actions(driver);
			  actions.doubleClick(element).perform();
		   }
		   
		

	//<-------------------------------------------------------Log Insert------------------------------------------------------------->
		
		// Logger configuration in class without using log property file
		   public static  Logger logger= Logger.getLogger(Base_Class.class);
		   static {
				    String stamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm").format(new Date());
			        String logFileName = "logs/" + stamp + "-Log.log";

			        try {
			            RollingFileAppender rollingFileAppender = new RollingFileAppender();
			            rollingFileAppender.setName("FileLogger");
			            rollingFileAppender.setFile(logFileName);
			            rollingFileAppender.setMaxFileSize("5MB");
			            rollingFileAppender.setMaxBackupIndex(10);
			            rollingFileAppender.setLayout(new PatternLayout("%d{ISO8601} %-5p %c{1} - %m%n"));
			            rollingFileAppender.activateOptions();
			            logger.addAppender(rollingFileAppender);
			        } catch (Exception e) {
			            e.printStackTrace();
			        }
		   }        
	       public Logger info(String message) {
			  logger.info(message);
			  return logger;
		   }
		   
	       public Logger debug(String message) {
	 		  logger.debug(message);
	 		  return logger;
	 	   }
	       
	       public Logger warn(String message) {
	 		  logger.warn(message);
	 		  return logger;
	 	   }
	       
	       public Logger error(String message) {
	 		  logger.error(message);
	 		  return logger;
	 	   }
		
	    // Log using log property file
		   public Logger infofile(String message) {
		      Logger logg=Logger.getLogger("Login Validation");
			  PropertyConfigurator.configure(projectPath()+"\\log4j.properties");
			  logg.info(message);
			  return logger;
		   }

		 //  Log for extent report implementation in both node and general   
		   public Logger infof(String message) {
		          Logger log= Logger.getLogger(this.getClass());
				  log.info(message);
				  print(message);
				  return log;
		        }
	    
	//<-------------------------------------------------ScreenShots Calls-------------------------------------------------------------->  
	    
	    /*public void ScreenShot(WebElement element, String value, String name) throws IOException {
		TakesScreenshot screenshot = (TakesScreenshot) driver;
		WebElement findElement = driver.findElement(By.id(value));
		File s = element.getScreenshotAs(OutputType.FILE);
		File d = new File("C:\\Users\\Ram prathees\\eclipse-workspace\\Base_Class_Full_Inputs\\Screenshots\\" + name + ".png");
		FileUtils.copyFile(s, d);
	}*/
		// Take screenshot for report with folder
		     public String screenshotReport( String Foldername,String name) throws IOException {
		       TakesScreenshot screenshot =(TakesScreenshot)driver;
		       String date = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
		       String stamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		       createFolder(date+" - "+Foldername);
		       String path=projectPath()+"\\Screenshots\\"+date+" - "+Foldername;
		       File s = screenshot.getScreenshotAs(OutputType.FILE);
		       String fileName=path+"\\"+stamp+" - "+ name +".png";
			   File d = new File(fileName);
			   FileUtils.copyFile(s, d);
			   return fileName;		    	 
		     }
	        
		//  Take screenshot for visible part of page
		    public void screenShortWebPage(String name) throws IOException {
			   TakesScreenshot screenshot = (TakesScreenshot) driver;
			   File s = screenshot.getScreenshotAs(OutputType.FILE);
			   File d = new File(projectPath()+"\\Screenshots\\" + name + ".png");
			   FileUtils.copyFile(s, d);
		     }
	    //  Take screenshot for element
	        public void ScreenShotElement(WebElement element, String name) throws IOException {
			  // TakesScreenshot screenshot = (TakesScreenshot) driver;
			   File s = element.getScreenshotAs(OutputType.FILE);
			   File d = new File(projectPath()+"\\Screenshots\\" + name + ".png");
			   FileUtils.copyFile(s, d);
	         }


	    //  Ashot  fullScreenshot
	        public void FullPageScreenShot(String name) throws IOException {
	    	   Screenshot scrshot =new AShot().shootingStrategy(ShootingStrategies.viewportPasting(0)).takeScreenshot(driver);
	    	   ImageIO.write(scrshot.getImage(), "png", new File(projectPath()+"\\Screenshots\\" + name + ".png"));
	        }
	    
	    //  Ashot capture Image
	        public void captureImage(WebElement element ,String Imagename) throws IOException {
	    	   Screenshot imageshot = new AShot().takeScreenshot(driver, element);
	    	   ImageIO.write(imageshot.getImage(), "png",new File(projectPath()+"\\Screenshots\\" + Imagename + ".png"));
	    	   File f=new File("./screenshot/"+Imagename+".png");    	 
	    	   if (f.exists()) {
	    		  System.out.println("image file captured");
	    	   } else {
	    		  System.out.println("image file not exixts");
	    	   }
	        }
	    
	    //  Ashot Compare Image and print difference
	        public void compareImage(String expectedImageName, WebElement element) throws IOException {
	    	   BufferedImage expectedimage = ImageIO.read(new File(projectPath()+"\\screenshot\\"+expectedImageName+".png"));
	    	   Screenshot imageshot = new AShot().takeScreenshot(driver, element);
	    	   BufferedImage actualimage = imageshot.getImage();
	    	   ImageDiffer imgdiff=new ImageDiffer();
	    	   ImageDiff diff=imgdiff.makeDiff(expectedimage, actualimage);
	    	   if (diff.hasDiff()==true) {
	    		   System.out.println("Image are not Same");
	    		   BufferedImage showdiff=diff.getMarkedImage();
	    		   ImageIO.write(showdiff, "png", new File(projectPath()+"\\Screenshots\\markedimage1.png"));
	    	   } else {
	    		    System.out.println("Images are same");
	    	   }
	        }
	    
	  //<------------------------------------------------ Robot Class Functions---------------------------------------------------->
		
		
		
		
	    // press Enter
	 	   public void pressEnter() throws AWTException {
	 		  Robot robot = new Robot();
	 		  robot.keyPress(KeyEvent.VK_ENTER); 
	 	   }
	 	
	 	// Release Enter
	 	   public void releaseEnter() throws AWTException {
	 		  Robot robot = new Robot();
	 		  robot.keyRelease(KeyEvent.VK_ENTER);
	 	   }
	 	   
	 	// Upload the File by Robot class
	 	   public void uploadFile( WebElement element,String path) throws AWTException {
	 		  JavascriptExecutor executor=(JavascriptExecutor) driver;
		      executor.executeScript("arguments[0].click()",element );
	 		  Robot robot = new Robot();
	 		  robot.delay(2000);
	 		  StringSelection ss=new StringSelection(path);
	 		  Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
	 		  robot.keyPress(KeyEvent.VK_CONTROL);
	 		  robot.keyPress(KeyEvent.VK_V);
	 		  robot.keyRelease(KeyEvent.VK_CONTROL);
	 		  robot.keyRelease(KeyEvent.VK_V);
	 		  robot.keyPress(KeyEvent.VK_ENTER);
	 		  robot.keyRelease(KeyEvent.VK_ENTER);
	 	   }
	 	
	 	
	    
	//<----------------------------------------------Color of the webelement Calls ---------------------------------------------------->
	    
	    
	    //  Get background Color
	        public String backgroundColor_Hex_Or_RGBA_Or_RGB(WebElement element ,String type) {
	    	   String backcolor=element.getCssValue("background-color");
	    	   if (type.equalsIgnoreCase("Hex")) {
	    		   String HEXcolor=Color.fromString(backcolor).asHex();
	    	       return HEXcolor;
			    } else if (type.equalsIgnoreCase("RGB")) {
				    String RGBcolor=Color.fromString(backcolor).asRgb();
				    return RGBcolor;
			    }else {
				     return backcolor;
			    }	    	
	         }
	   
	   //  Get Text Color
	       public String textColor_Hex_Or_RGBA_Or_RGB(WebElement element, String type) {
	    	  String TxtColor=element.getCssValue("color");
	    	  if (type.equalsIgnoreCase("Hex")) {
	    		  String HEXcolor=Color.fromString(TxtColor).asHex();
	    	      return HEXcolor;
			  } else if (type.equalsIgnoreCase("RGB")) {
				  String RGBcolor=Color.fromString(TxtColor).asRgb();
				  return RGBcolor;
			  }else {
			      return TxtColor;
			  }	
	       }
	    
	   //  Get border Color
	       public String borderColor_Hex_Or_RGBA_Or_RGB(WebElement element, String type) {
	           String TxtColor=element.getCssValue("border-color");
	           if (type.equalsIgnoreCase("Hex")) {
	        	   String HEXcolor=Color.fromString(TxtColor).asHex();
	        	   return HEXcolor;
	    		} else if (type.equalsIgnoreCase("RGB")) {
	    			String RGBcolor=Color.fromString(TxtColor).asRgb();
	    			return RGBcolor;
	    		}else {
	    			return TxtColor;
	            }
	        }


	//<------------------------------------------------------------Excel Data--------------------------------------------------------->
	       
	     // Read Cell Data
	        public static String readCellDatum(String path,int sheetnum, int rownum, int cellnum) throws IOException {
	    	    String res="";
				File file = new File(path);
				FileInputStream fileinputstream = new FileInputStream(file);
				XSSFWorkbook workbook = new XSSFWorkbook(fileinputstream);
			    XSSFSheet sheet = workbook.getSheetAt(sheetnum);
				XSSFRow row = sheet.getRow(rownum);
				XSSFCell cell = row.getCell(cellnum);
				CellType type = cell.getCellType();
				switch (type) {
				case STRING:
					res = cell.getStringCellValue();
					break;
				case NUMERIC:
					if (DateUtil.isCellDateFormatted(cell)) {
						Date dateCellValue = cell.getDateCellValue();
						SimpleDateFormat dateformat = new SimpleDateFormat("dd-mmm-yyyy");
						res = dateformat.format(dateCellValue);
					 } else {
						double numericCellValue = cell.getNumericCellValue();
						long check = Math.round(numericCellValue);
						if (check == numericCellValue) {
							res = String.valueOf(check);
						} else {
							res = String.valueOf(numericCellValue);
						    }
						break;
					    }
				   default:
				   break;
				 }
				 return res;
			}

	        
	   //  Read Cell Data using Data Provider by providing input of rows and columns with sample data provider 
	       public static  String getCellData(String path, int sheetnum,int rownum, int colnum ) throws IOException {
	    		FileInputStream fi=new FileInputStream(path);
	    		XSSFWorkbook workbook=new XSSFWorkbook(fi);
	    		XSSFSheet sheet=workbook.getSheetAt(sheetnum);
	    		XSSFRow row=sheet.getRow(rownum);
	    		XSSFCell cell=row.getCell(colnum);
	    		
	    		DataFormatter formatter=new DataFormatter();
	    		String data;
	    		try {
	    			data = formatter.formatCellValue(cell);//Return the formatted value of a cell as a string regardless of the data type.		
	    		} catch (Exception e) {
	    			data="";
	    		}
	    		workbook.close();
	    		fi.close();
	    		return data;
	    	}
	        @DataProvider (name="Login_Data_Excel")
	        public String [][] getData() throws IOException{
	     		String path ="C:\\Users\\Ram prathees\\eclipse-workspace\\Base_Class_Full_Inputs\\Excel File\\fortest.xlsx";
	     		int startRows=0;
	     		int totalrows=3;
	     		int startCols=0;
	     		int totalcols=3;
	     		
	     		
	     		String loginData[][]=new String[totalrows][totalcols];
	     		for (int i = startRows; i <=totalrows; i++) {
	     			for (int j = startCols; j < totalcols; j++) {
	     			loginData[i][j]=getCellData(path, 0, i, j);
	     			}
	     		}
	     		return loginData;
	     		}
	       
	       /*// Pre-Store Background color for compare 
	      
	      public static String setColor_Hex_Or_RGBA_Or_RGB(String type,String Value,String colorName, ) {
	    	  HashMap<String, String> hm= new HashMap<String, String>();
	    	  if (type.equalsIgnoreCase("Hex")) {
	      		hm.put;
	      	    return HEXcolor;
	  		} else if (type.equalsIgnoreCase("RGB")) {
	  			String RGBcolor=Color.fromString(TxtColor).asRgb();
	  			return RGBcolor;
	  		}else {
	  			return TxtColor;
	          }
	    	  
	      }*/
	/*	// 51 Read Cell Data
		public static String readCellData(String sheetnum, int rownum, int cellnum) throws IOException {

			String res = "";

			File file = new File("C:\\Users\\welcome\\Desktop.xlsx");
			FileInputStream fileinputstream = new FileInputStream(file);
			Workbook workbook = new XSSFWorkbook(fileinputstream);
		    Sheet = workbook.getSheet(sheetnum);
			Row row = sheet.getRow(rownum);
			Cell cell = row.getCell(cellnum);
			CellType type = cell.getCellType();

			switch (type) {
			case STRING:
				res = cell.getStringCellValue();

				break;
			case NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					Date dateCellValue = cell.getDateCellValue();
					SimpleDateFormat dateformat = new SimpleDateFormat("dd-mmm-yyyy");
					res = dateformat.format(dateCellValue);
				} else {
					double numericCellValue = cell.getNumericCellValue();
					long check = Math.round(numericCellValue);
					if (check == numericCellValue) {
						res = String.valueOf(check);
					} else {
						res = String.valueOf(numericCellValue);
					}

					break;
				}
			default:
				break;
			}
			return res;
		}


		// 52 Replace cell Data
		public static void replaceCellData(String sheetnum, int rownum, int cellnum, String olddata, String newdata)
				throws IOException {

			File file = new File("C:\\Users\\welcome\\eclipse-workspace\\FrameWork\\Excel 2\\Excel 6 june.xlsx");
			FileInputStream fileinputstream = new FileInputStream(file);
			Workbook workbook = new XSSFWorkbook(fileinputstream);
			Sheet sheet = Workbook..getsh
			Row row = sheet.getRow(rownum);
			Cell cell = row.createCell(cellnum);
			String value = cell.getStringCellValue();

			if (value.equals(olddata)) {
				cell.setCellValue(newdata);
				FileOutputStream fileoutputstream = new FileOutputStream(file);
				workbook.write(fileoutputstream);

			}
		}

		// 53 insert value in cell
		public static void insertValueCell(String sheetnum, int rownum, int cellnum, String data) throws IOException {

			File file = new File("C:\\Users\\welcome\\eclipse-workspace\\FrameWork\\Excel 2");
			FileInputStream fileinputstream = new FileInputStream(file);
			Workbook workbook = new XSSFWorkbook(fileinputstream);
			Sheet sheet = Workbook.getSheet(sheetnum);
			Row row = sheet.getRow(rownum);
			Cell cell = row.createCell(cellnum);
			cell.setCellValue(data);
			FileOutputStream fileoutputstream = new FileOutputStream(file);
			workbook.write(fileoutputstream);

		}*/
	        public static void insertValueCell(String sheetName, int rownum, int cellnum, String data) throws IOException {
	            File file = new File(System.getProperty("user.dir")+"//playLinks.xlsx");
	            
	            // Open the file input stream
	            FileInputStream fileInputStream = new FileInputStream(file);
	            Workbook workbook = new XSSFWorkbook(fileInputStream);
	            
	            // Get the sheet
	            Sheet sheet = workbook.getSheet(sheetName);
	            if (sheet == null) {
	                sheet = workbook.createSheet(sheetName);
	            }

	            // Get or create the row
	            Row row = sheet.getRow(rownum);
	            if (row == null) {
	                row = sheet.createRow(rownum);
	            }

	            // Create the cell and set value
	            Cell cell = row.createCell(cellnum);
	            cell.setCellValue(data);

	            // Close the input stream before writing
	            fileInputStream.close();

	            // Write to the file
	            FileOutputStream fileOutputStream = new FileOutputStream(file);
	            workbook.write(fileOutputStream);

	            // Close resources
	            fileOutputStream.close();
	            workbook.close();
	        }
	//<---------------------------------------------------Encoder and Decoder------------------------------------------------------------->
	    
	      //Encode the String Data  
	      public String Encoding(String data ) {		
	    	  byte[] Encode = Base64.encodeBase64(data.getBytes());
	    	  String EncodedData=new String(Encode);
	    	  return EncodedData;		
	      }
	      
	      //Decode the String Data   may get failure if happens string format of encoded data is problem change and try
	      public String Decoding(String data ) {		
	    	  byte[] Decode = Base64.decodeBase64(data.getBytes());
	    	  String DecodedData=new String(Decode);
	    	  return DecodedData;		
	      }

}
