package Test_Case;

import java.awt.AWTException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Meta_Copy_001 {
	WebDriver driver;

	@BeforeTest
	public void browseropen() {
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
	}

	//@AfterTest
	public void closebroser() {
		driver.close();
	}
	@Test (priority=1)
	public void Fetchdata() throws AWTException, InterruptedException {
		
		driver.get("https://www.facebook.com/ads/library/?active_status=all&ad_type=all&country=IN&is_targeted_country=false&media_type=all&q=download%20now&search_type=keyword_unordered");
		System.out.println("URL Entered");
		
			Scanner a =new Scanner(System.in);
			System.out.println("Enter the keyword you want search");
			String keyWord= a.nextLine();
			System.out.println(keyWord);
			WebElement searchField = driver.findElement(By.id("js_2"));
			searchField.clear();
			searchField.sendKeys(keyWord,Keys.ENTER);
			
			

	        JavascriptExecutor js = (JavascriptExecutor) driver;
	        long lastHeight = (long) js.executeScript("return document.body.scrollHeight");
	        int scrollPauseTime = 3000;
	        for (int i = 0; i < 15; i++) {
	        	  js.executeScript("window.scrollTo(0, document.body.scrollHeight);"); 

	              // Wait for new content to load
	              Thread.sleep(scrollPauseTime); // adjust based on site loading speed
	              js.executeScript("window.scrollTo( document.body.scrollHeight, (document.body.scrollHeight)/2);"); 
	              Thread.sleep(3000);
	              long newHeight = (long) js.executeScript("return document.body.scrollHeight");
	             // js.executeScript("window.scrollTo( document.body.scrollHeight, (document.body.scrollHeight)/2);"); 
//	              if (newHeight == lastHeight) {
//	                  break; // No more content loaded
//	              }
	             // lastHeight = newHeight;
			} 
	            // Scroll to bottom
	            js.executeScript("window.scrollTo(0, document.body.scrollHeight);"); 

	            // Wait for new content to load
	            Thread.sleep(scrollPauseTime); // adjust based on site loading speed
	            js.executeScript("window.scrollTo( document.body.scrollHeight, (document.body.scrollHeight)/2);"); 
	            Thread.sleep(3000);
	            long newHeight = (long) js.executeScript("return document.body.scrollHeight");
	          //  js.executeScript("window.scrollTo( document.body.scrollHeight, (document.body.scrollHeight)/2);"); 
//	            if (newHeight == lastHeight) {
//	                break; // No more content loaded
//	            }
	            lastHeight = newHeight;
	        

			int linkCount = driver.findElements(By.xpath("//div[text()='Install now']")).size();
			System.out.println("Total link is "+linkCount);
			for (int i = 1; i <= linkCount; i++) {
				try {
					WebElement linkTag = driver.findElement(By.xpath("(//div[text()='Install now'])["+i+"]/ancestor::a"));
					String href = linkTag.getAttribute("href");
					try {
			      insertValueCell("Sheet1", i-1, 0, href);
		      System.out.println("Data written successfully.");
			  } catch (IOException e) {
				      e.printStackTrace();
				   }
				} catch (Exception e) {
					System.out.println(i+" - Link is disabled");
				}
				
			}
	}
	//
	static int iterate=0;
	static int iteration = 0;
	@Test (priority=2 , dataProvider = "excelData" , dataProviderClass = Utilities.Data_Provider.class,dependsOnMethods = "Fetchdata" )
	public void analyseURLs(String URL) throws IOException {
		driver.get(URL);
		try {
			driver.findElement(By.xpath("//a[text()='Follow Link']")).click();
		} catch (Exception e) {
		//	System.out.println("It redirects directly to the app page");
		}
		WebElement downloadsNos = driver.findElement(By.xpath("//div[text()='Downloads']/preceding-sibling::div"));
		String downloadCounts = downloadsNos.getText();
		
		 long count = convertToNumber(downloadCounts);
		 System.out.println(count);
		 if (count < 1000000) {
			insertValueCell("Sheet2", iteration, 0, URL);
			iteration++;
			System.out.println(iterate+" is written");
		} else {
			System.out.println(iterate+"The Downloads is more than 10M");
			
		}
		 iterate++;
	}

	public static long convertToNumber(String value) {
	    value = value.trim().toUpperCase();

	    if (value.endsWith("K")) {
	        return (long) (Double.parseDouble(value.replace("K", "")) * 1_000);
	    }  else if (value.endsWith("K+")) {
	        return (long) (Double.parseDouble(value.replace("K+", "")) * 1_000);
	        }
	    else if (value.endsWith("M")) {
	        return (long) (Double.parseDouble(value.replace("M", "")) * 1_000_000);
	    }else if (value.endsWith("M+")) {
	        return (long) (Double.parseDouble(value.replace("M+", "")) * 1_000_000);
	        } 
	    else if (value.endsWith("B")) {
	        return (long) (Double.parseDouble(value.replace("B", "")) * 1_000_000_000);
	    } else {
	        return Long.parseLong(value); // if it's already a number
	    }
	}
	//click(locatorId("js_0"));


		//	     try {
//	      insertValueCell("Sheet1", i, 1, keyWord);
//	      System.out.println("Data written successfully.");
	//  } catch (IOException e) {
//	      e.printStackTrace();
	//   }
	//}
		
//		Scanner a =new Scanner(System.in);
//		System.out.println("Select the country");
//		String Country= a.next();
//		print(Country);
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
	public static void insertValueCellFinal(String sheetName, int rownum, int cellnum, String data) throws IOException {
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
}
