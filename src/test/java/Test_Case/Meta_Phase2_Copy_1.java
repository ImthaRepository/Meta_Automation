package Test_Case;

import java.awt.AWTException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

public class Meta_Phase2_Copy_1 {
	
	
	public static String stamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
	
	WebDriver driver;

	@BeforeTest
	public void browseropen() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
	}

//@AfterTest
	public void closebroser() {
		driver.close();
	}

	//@Test(priority = 1)
	public void Fetchdata() throws AWTException, InterruptedException {

		driver.get(
				"https://www.facebook.com/ads/library/?active_status=all&ad_type=all&country=IN&is_targeted_country=false&media_type=all&q=download%20now&search_type=keyword_unordered");
		System.out.println("URL Entered");

		// ---------------------------------------Select Country---------------------------

		driver.findElement(By.id("js_0")).click();

		Scanner a = new Scanner(System.in);
		System.out.println("Enter the serial number of the Country:\n1.India\n2.All");
		int Country = a.nextInt();

		if (Country == 1) {
			driver.findElement(By.xpath("//div[text()='India' and @id='js_5np']")).click();
			System.out.println("India has been Choosen");

		} else {
			driver.findElement(By.xpath("//div[contains(@id,'js_') and text()='All']")).click();
			System.out.println("All Region has been Choosen");
		}

		// -------------------------------------Select Category--------------------------------

		driver.findElement(By.xpath("//div[contains(@id,'js_') and text()='Ad category']")).click();
		driver.findElement(By.xpath("//span[text()='All ads']")).click();

		// ------------------------------------Keyword Search----------------------------------
		Scanner b = new Scanner(System.in);
		System.out.println("Enter the keyword you want search");
		String keyWord = b.nextLine();
		System.out.println(keyWord);
		WebElement searchField = driver.findElement(
				By.xpath("//input[contains(@id,'js') and @placeholder='Search by keyword or advertiser']"));
		searchField.clear();
		searchField.sendKeys(keyWord, Keys.ENTER);

		// ------------------------------------Scroll Process-----------------------------------
		JavascriptExecutor js = (JavascriptExecutor) driver;
		long lastHeight = (long) js.executeScript("return document.body.scrollHeight");
		int scrollPauseTime = 3000;
		for (int i = 0; i < 20; i++) {
			js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

			// Wait for new content to load
			Thread.sleep(scrollPauseTime); // adjust based on site loading speed
			js.executeScript("window.scrollTo( document.body.scrollHeight, (document.body.scrollHeight)/2);");
			Thread.sleep(3000);
			long newHeight = (long) js.executeScript("return document.body.scrollHeight");
			lastHeight = newHeight;

		}

		// --------------------------------------Excel Written Process-------------------------------
		
		
		int linkCount = driver.findElements(By.xpath("//div[text()='Install now']")).size();
		System.out.println("Total link is " + linkCount);
		for (int i = 1; i <= linkCount; i++) {
			try {
				WebElement linkTag = driver
						.findElement(By.xpath("(//div[text()='Install now'])[" + i + "]/ancestor::a"));
				String href = linkTag.getAttribute("href");
				try {
					insertValueCell("Sheet1", i - 1, 0, href);
					System.out.println("Data written successfully.");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				System.out.println(i + " - Link is disabled");
			}

		}
	}

	// -------------------------------------------------Fetched link Validation and Filter----------------------------------------------
	static int iteration = 0;

	//@Test(priority = 2, dataProvider = "excelData", dataProviderClass = Utilities.Data_Provider.class, dependsOnMethods = "Fetchdata")
	public void analyseURLs(String URL) throws IOException {
		driver.get(URL);
		try {
			driver.findElement(By.xpath("//a[text()='Follow Link']")).click();
		} catch (Exception e) {
		//	System.out.println("It redirects directly to the app page");
		}
		try {
			WebElement downloadsNos = driver.findElement(By.xpath("//div[text()='Downloads']/preceding-sibling::div"));
			String downloadCounts = downloadsNos.getText();

			long count = convertToNumber(downloadCounts);
			System.out.println(count);
			if (count <= 100000000) {
				try {
					WebElement ratingElement = driver.findElement(By.xpath("//div[@itemprop='starRating']//div"));
					String rating = ratingElement.getText();
					double ratingCount=0;
					try {
					    String[] parts = rating.trim().split("\\s+"); // Split by any whitespace
					     ratingCount = Double.parseDouble(parts[0]);
					    System.out.println("Parsed rating: " + ratingCount);
					} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
					    System.err.println("Failed to parse rating: " + rating);
					    e.printStackTrace();
					}
					if (ratingCount <= 4.5) {
						try {
						 
							WebElement reviewElement = driver.findElement(
									By.xpath("//div[@itemprop='starRating']/parent::div/following-sibling::div"));
							String review = reviewElement.getText();
							
							int reviewCount =convertReviewToNumber(review);

							if (reviewCount <= 2820000) {
								insertValueCell("Sheet2", iteration, 0, URL);
								System.out.println("The Review is lesss than 900");
							} else {
								System.out.println("The Review Count is more than 150");
							}
						} catch (Exception e) {
							insertValueCell("Sheet2", iteration, 0, URL);
						}
								
					} else {
						System.out.println("The Rating Count is Greater than 4.5");
					}
				} catch (Exception e) {
					System.out.println("No Rating is Given");
					insertValueCell("Sheet2", iteration, 0, URL);
				}
			} else {
				System.out.println("The Downloads is more than 1M");
			}
		} catch (Exception e) {
			insertValueCell("Sheet2", iteration, 0, URL);
		}
		iteration++;
	}

//-----------------------------------------------------------Number Conversion Code------------------------------------------------------------//

	public static long convertToNumber(String value) {
		value = value.trim().toUpperCase();

		if (value.endsWith("K")) {
			return (long) (Double.parseDouble(value.replace("K", "")) * 1_000);
		} else if (value.endsWith("K+")) {
			return (long) (Double.parseDouble(value.replace("K+", "")) * 1_000);
		} else if (value.endsWith("M")) {
			return (long) (Double.parseDouble(value.replace("M", "")) * 1_000_000);
		} else if (value.endsWith("M+")) {
			return (long) (Double.parseDouble(value.replace("M+", "")) * 1_000_000);
		} else if (value.endsWith("+")) {
			return (long) (Double.parseDouble(value.replace("+", "")) * 1);
		} else if (value.endsWith("B")) {
			return (long) (Double.parseDouble(value.replace("B", "")) * 1_000_000_000);
		} else {
			return Long.parseLong(value); // if it's already a number
		}
	}
	
	public static int convertReviewToNumber(String value) {
		value = value.trim().toUpperCase();

        if (value.endsWith("K reviews") || value.endsWith("K+ reviews")) {
            return (int) (Double.parseDouble(value.replaceAll("[^\\d.]", "")) * 1_000);
        } else if (value.endsWith("M reviews") || value.endsWith("M+ reviews")) {
            return (int) (Double.parseDouble(value.replaceAll("[^\\d.]", "")) * 1_000_000);
        } else if (value.endsWith("B reviews") || value.endsWith("B+ reviews")) {
            return (int) (Double.parseDouble(value.replaceAll("[^\\d.]", "")) * 1_000_000_000);
        } else if (value.endsWith("+ reviews") || value.endsWith(" reviews")) {
            return (int) Double.parseDouble(value.replaceAll("[^\\d.]", ""));
        } else {
            return Integer.parseInt(value.replaceAll("[^\\d]", "")); // fallback
        }
	}


//------------------------------------------------------------ Insert Value in the Excel Sheet --------------------------------------------//

	public static void insertValueCell(String sheetName, int rownum, int cellnum, String data) throws IOException {
		File file = new File(System.getProperty("user.dir") + "//playLinks.xlsx");

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
		File file = new File(System.getProperty("user.dir") + "//playLinks.xlsx");

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

	//--------------------------------------------- Create New Excel File-----------------------------------------------------
	public void createExcelFile() {
		// Create a new workbook
		Workbook workbook = new XSSFWorkbook();

		// Create a new sheet
		Sheet sheet = workbook.createSheet("TestData");

		// Create a row
		Row row = sheet.createRow(0);

		// Create cells
		row.createCell(0).setCellValue("Username");
		row.createCell(1).setCellValue("Password");

		// Add data to next row
		Row dataRow = sheet.createRow(1);
		dataRow.createCell(0).setCellValue("admin");
		dataRow.createCell(1).setCellValue("admin123");

		// Write to file
		try (FileOutputStream fileOut = new FileOutputStream("TestData.xlsx")) {
			workbook.write(fileOut);
			workbook.close();
			System.out.println("Excel file created successfully!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
