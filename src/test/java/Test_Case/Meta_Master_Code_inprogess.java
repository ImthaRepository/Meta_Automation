package Test_Case;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Meta_Master_Code_inprogess {
	
	
	public String getPropertyFileValue(String key) throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream(System.getProperty("user.dir") + "\\input.properties"));
		Object object = properties.get(key);
		String value = (String) object;
		return value;

	}

	public static String stamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

	WebDriver driver;

	@BeforeTest
	public void browseropen() {
//		System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
//        driver = new ChromeDriver();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
	}

	@AfterTest
	public void closebroser() {
		 if (driver != null) {
	            try {
	                driver.quit(); // Properly close session
	            } catch (org.openqa.selenium.WebDriverException e) {
	                System.err.println("WebDriverException caught during quit(): " + e.getMessage());
	                // You can log or handle Connection Reset specifically
	                if (e.getMessage().contains("Connection reset")) {
	                    // handle or log specifically
	                }
	            } catch (Exception e) {
	                System.err.println("Unexpected error during driver quit: " + e.getMessage());
	            }
	        }
	    }

	@Test(priority = 1)
	public void Fetchdata() throws InterruptedException, FileNotFoundException, IOException {
		// driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.get(
				"https://www.facebook.com/ads/library/?active_status=all&ad_type=all&country=IN&is_targeted_country=false&media_type=all&q=download%20now&search_type=keyword_unordered");
		System.out.println("URL Entered");

		// ---------------------------------------Select Country---------------------------
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("js_0")));
		
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@id,'js_') and text()='All']")));
//		driver.findElement(By.xpath("//div[contains(@id,'js_') and text()='United States']")).click();
//		System.out.println("All Region has been Choosen");
//		Scanner a = new Scanner(System.in);
//		System.out.println("Enter the serial number of the Country:\n1.India\n2.All");
//		int Country = a.nextInt();

		String Country = getPropertyFileValue("Country");
		System.out.println(Country);
		if (Country.equals(Country)) {
			System.out.println("India has been Choosen");

		} else {
			driver.findElement(By.id("js_0")).click();
			driver.findElement(By.xpath("//div[contains(@id,'js_') and text()='" + Country + "']")).click();
			System.out.println(Country + " has been Choosen");
			// -------------------------------------Select Category--------------------------------
			try {
				driver.findElement(By.xpath("//div[contains(@id,'js_') and text()='Ad category']")).click();
				driver.findElement(By.xpath("//span[text()='All ads']")).click();
				System.out.println("All ads is clicked");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("All ads is not clicked");
			}
		}

		// ------------------------------------Keyword   Search----------------------------------
//		Scanner b = new Scanner(System.in);
//		System.out.println("Enter the keyword you want search");
//		String keyWord = b.nextLine();
//		System.out.println(keyWord);
		WebElement searchField = driver.findElement(
				By.xpath("//input[contains(@id,'js') and @placeholder='Search by keyword or advertiser']"));
		searchField.clear();
		searchField.sendKeys(getPropertyFileValue("keyword"), Keys.ENTER);

		try {
			driver.findElement(By.xpath("//*[text()='No ads match your search criteria']"));
			System.out.println("No ads match your search criteria");
			Assert.fail();
		} catch (Exception e) {
			System.out.println("Data Found");
		}

		// ------------------------------------Scroll Process-----------------------------------
//		JavascriptExecutor js = (JavascriptExecutor) driver;
//		long lastHeight = (long) js.executeScript("return document.body.scrollHeight");
//		int scrollPauseTime = 3000;
//		for (int i = 0; i < 100; i++) {
//			js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
//			System.out.println(i);
//			// Wait for new content to load
//			Thread.sleep(scrollPauseTime); // adjust based on site loading speed
//			js.executeScript("window.scrollTo( document.body.scrollHeight, (document.body.scrollHeight)/2);");
//			Thread.sleep(3000);
//			long newHeight = (long) js.executeScript("return document.body.scrollHeight");
//			lastHeight = newHeight;
//
//		}
//		
		/*
		 * JavascriptExecutor js = (JavascriptExecutor) driver;
		 * driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(60));
		 * 
		 * AtomicLong lastHeight = new AtomicLong((long)
		 * js.executeScript("return document.body.scrollHeight"));
		 * 
		 * for (int i = 0; i <=50 ; i++) { System.out.println("Scroll: " + i);
		 * js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
		 * 
		 * try { boolean heightChanged = wait.until(driver -> { long newHeight = (long)
		 * js.executeScript("return document.body.scrollHeight"); return newHeight >
		 * lastHeight.get(); });
		 * 
		 * if (!heightChanged) { System.out.println("No more content."); break; }
		 * 
		 * // Optional lazy-load trigger js.
		 * executeScript("window.scrollTo(document.body.scrollHeight, document.body.scrollHeight/2);"
		 * ); Thread.sleep(5000); lastHeight.set((long)
		 * js.executeScript("return document.body.scrollHeight"));
		 * 
		 * } catch (TimeoutException e) {
		 * System.out.println("Wait timed out. Possibly no more content."); break; } }
		 */
		JavascriptExecutor js = (JavascriptExecutor) driver;
		long lastHeight = (long) js.executeScript("return document.body.scrollHeight");
		int scrollPauseTime = 3000;
		String loopCount = getPropertyFileValue("ScrollCount");
		int ScrollCount = Integer.parseInt(loopCount);
		for (int i = 0; i <= ScrollCount; i++) {
			System.out.println("Scroll: " + i);
			js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

			// Wait for new content to load
			Thread.sleep(scrollPauseTime); // adjust based on site loading speed
			js.executeScript("window.scrollTo( document.body.scrollHeight, (document.body.scrollHeight)/2);");
			Thread.sleep(3000);
			long newHeight = (long) js.executeScript("return document.body.scrollHeight");
			lastHeight = newHeight;

		}

		// --------------------------------------Excel Written Process-------------------------------
		createExcelFile(stamp);

		int linkCount = driver.findElements(By.xpath("//div[text()='Install now']")).size();
		System.out.println("Total link is " + linkCount);
		for (int i = 1; i <= linkCount; i++) {
			try {
				WebElement linkTag = driver
						.findElement(By.xpath("(//div[text()='Install now'])[" + i + "]/ancestor::a"));
				String href = linkTag.getAttribute("href");
				try {
					insertValueCell("All_Links", i - 1, 0, href);
					System.out.println("Data written successfully.");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				System.out.println(i + " - Link is disabled");
			}

		}
		createExcelFile(stamp + "-" + Country + "- New sheet");
	}

	// -------------------------------------------------Fetched link Validation and Filter----------------------------------------------
	static int iteration = 0;
	static int iterate = 0;

	@Test(priority = 2, dataProvider = "excelData", dependsOnMethods = "Fetchdata")
	public void analyseURLs(String URL1) throws IOException {
		driver.get(URL1);
		String Country = getPropertyFileValue("Country");
		String filepath = System.getProperty("user.dir") + "\\" + stamp + "-" + Country + "- New sheet.xlsx";
		try {
			driver.findElement(By.xpath("//a[text()='Follow Link']")).click();
		} catch (Exception e) {
			// System.out.println("It redirects directly to the app page");
		}
		String URL = driver.getCurrentUrl();
		try {
			WebElement downloadsNos = driver.findElement(By.xpath("//div[text()='Downloads']/preceding-sibling::div"));
			String downloadCounts = downloadsNos.getText();

			long count = convertToNumber(downloadCounts);
			System.out.println(count);
			if (count < 1000000) {
				try {
					WebElement ratingElement = driver.findElement(By.xpath("//div[@itemprop='starRating']//div"));
					String rating = ratingElement.getText();
					double ratingCount = 0;
					try {
						String[] parts = rating.trim().split("\\s+"); // Split by any whitespace
						ratingCount = Double.parseDouble(parts[0]);
						System.out.println("Parsed rating: " + ratingCount);
					} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
						System.err.println("Failed to parse rating: " + rating);
						e.printStackTrace();
					}
					if (ratingCount <= 4.5) {
//						try {
//						 
//							WebElement reviewElement = driver.findElement(
//									By.xpath("//div[@itemprop='starRating']/parent::div/following-sibling::div"));
//							String review = reviewElement.getText();
//							
//							int reviewCount =convertReviewToNumber(review);
//
//							if (reviewCount <= 150) {
//								insertValueCellWithoutDuplicate("Sheet2", iteration, 0, URL);
//								writeInMasterSheet("Links", 0, URL);
//								System.out.println("The Review is less than 150");
//								System.out.println(iterate+" - link in written");
//								iteration++;
//							} else {
//								System.out.println(iterate+" - The Review Count is more than 150");
//							}
//						} catch (Exception e) {
//							insertValueCellWithoutDuplicate("Sheet2", iteration, 0, URL);
//							writeInMasterSheet("Links", 0, URL);
//							System.out.println(iterate+" - link in written");
//							iteration++;
//						}
						insertValueCellWithoutDuplicate("Sheet2", iteration, 0, URL);
						writeInMasterSheet("Links", 0, URL, filepath, "Sheet1");
						System.out.println("The rating is less than 4.5");
						System.out.println(iterate + " - link in written");
						iteration++;
					} else {
						System.out.println(iterate + " - The Rating Count is Greater than 4.5");
					}
				} catch (Exception e) {
					System.out.println("No Rating is Given");
					insertValueCellWithoutDuplicate("Sheet2", iteration, 0, URL);
					writeInMasterSheet("Links", 0, URL, filepath, "Sheet1");
					System.out.println(iterate + " - link in written");
					iteration++;
				}
			} else {
				System.out.println(iterate + " - The Downloads is more than 1M");
			}
		} catch (Exception e) {
//			insertValueCellWithoutDuplicate("Sheet2", iteration, 0, URL);
//			writeInMasterSheet("Links", 0, URL);
			System.out.println(iterate + " - The link is App store or other site");
		}
//		driver.get(URL);
//		try {
//			WebElement downloadsNos = driver.findElement(By.xpath("//div[text()='Downloads']/preceding-sibling::div"));
//			String downloadCounts = downloadsNos.getText();
//
//			long count = convertToNumber(downloadCounts);
//			System.out.println(count);
//			if (count < 1000000) {
//				insertValueCellWithoutDuplicate("Filtered Links", iteration, 0, URL);	
//				writeInMasterSheet("Links", 0, URL);	
//			} else {
//				System.out.println("The Downloads is more than 10M");
//			}
//		} catch (Exception e) {
//			insertValueCellWithoutDuplicate("Filtered Links", iteration, 0, URL);
//			writeInMasterSheet("Links", 0, URL);
//		}

		iterate++;
	}
	
	

	// ----------------------------------------------Data Provider Code--------------------------------------------------------------------
	@DataProvider(name = "excelData")
	public Object[][] getDataFromExcel() {
		String filePath = System.getProperty("user.dir") + "\\" + stamp + ".xlsx";
		String sheetName = "All_Links";
		return readExcelData(filePath, sheetName);
	}

	private Object[][] readExcelData(String filePath, String sheetName) {
		List<Object[]> dataList = new ArrayList();

		try (FileInputStream fis = new FileInputStream(new File(filePath));
				Workbook workbook = WorkbookFactory.create(fis)) {

			Sheet sheet = workbook.getSheet(sheetName);
			Iterator<Row> rows = sheet.iterator();

			while (rows.hasNext()) {
				Row row = rows.next();
				List<String> cellList = new ArrayList<>();

				boolean skipRow = false;

				for (Cell cell : row) {
					cell.setCellType(CellType.STRING);
					String value = cell.getStringCellValue().trim();

					if (value.isEmpty()) {
						skipRow = true; // Skip if any cell in the row is empty
						break;
					}

					cellList.add(value);
				}

				if (!skipRow && !cellList.isEmpty()) {
					dataList.add(cellList.toArray(new Object[0]));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dataList.toArray(new Object[0][]);
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
		// File file = new File(System.getProperty("user.dir") + "//playLinks.xlsx");
		File file = new File(System.getProperty("user.dir") + "//" + stamp + ".xlsx");
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

	public static void insertValueCellWithoutDuplicate(String sheetName, int rownum, int cellnum, String data)
			throws IOException {
		File file = new File(System.getProperty("user.dir") + "//" + stamp + ".xlsx");

		Workbook workbook;
		Sheet sheet;

		// If file exists, read it; else create new workbook
		if (file.exists()) {
			FileInputStream fileInputStream = new FileInputStream(file);
			workbook = new XSSFWorkbook(fileInputStream);
			fileInputStream.close();
		} else {
			workbook = new XSSFWorkbook();
		}

		// Get or create sheet
		sheet = workbook.getSheet(sheetName);
		if (sheet == null) {
			sheet = workbook.createSheet(sheetName);
		}

		// Check for duplicates in the entire column (cellnum)
		boolean isDuplicate = false;
		for (Row existingRow : sheet) {
			Cell existingCell = existingRow.getCell(cellnum);
			if (existingCell != null && existingCell.getCellType() == CellType.STRING) {
				if (existingCell.getStringCellValue().equalsIgnoreCase(data)) {
					isDuplicate = true;
					break;
				}
			}
		}

		if (!isDuplicate) {
			// Get or create the row
			Row row = sheet.getRow(rownum);
			if (row == null) {
				row = sheet.createRow(rownum);
			}

			// Create the cell and set value
			Cell cell = row.createCell(cellnum);
			cell.setCellValue(data);

			// Write to file
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			workbook.write(fileOutputStream);
			fileOutputStream.close();
			System.out.println("Data written successfully: " + data);
		} else {
			System.out.println("Duplicate entry skipped: " + data);
		}

		workbook.close();
	}

//	public static void writeInMasterSheet(String sheetName, int columnNumber, String data) throws IOException {
//		File file = new File(System.getProperty("user.dir") + "//MasterSheet.xlsx");
//		Workbook workbook;
//		Sheet sheet;
//
//		// Load existing workbook or create new one
//		if (file.exists()) {
//			FileInputStream fileInputStream = new FileInputStream(file);
//			workbook = new XSSFWorkbook(fileInputStream);
//			fileInputStream.close();
//		} else {
//			workbook = new XSSFWorkbook();
//		}
//
//		// Get or create sheet
//		sheet = workbook.getSheet(sheetName);
//		if (sheet == null) {
//			sheet = workbook.createSheet(sheetName);
//		}
//
//		// Check for duplicate in the target column
//		boolean isDuplicate = false;
//		int lastRowNum = sheet.getLastRowNum();
//		for (int i = 0; i <= lastRowNum; i++) {
//			Row row = sheet.getRow(i);
//			if (row != null) {
//				Cell cell = row.getCell(columnNumber);
//				if (cell != null && cell.getCellType() == CellType.STRING) {
//					if (cell.getStringCellValue().equalsIgnoreCase(data)) {
//						isDuplicate = true;
//						break;
//					}
//				}
//			}
//		}
//	}

	public static void writeInMasterSheet(String sheetName, int columnNumber, String data, String fileName,
			String sheetname) throws IOException {
		File file = new File(System.getProperty("user.dir") + "//Master Sheet.xlsx");
		Workbook workbook;
		Sheet sheet;

		// Load existing workbook or create new one
		if (file.exists()) {
			FileInputStream fileInputStream = new FileInputStream(file);
			workbook = new XSSFWorkbook(fileInputStream);
			fileInputStream.close();
		} else {
			workbook = new XSSFWorkbook();
		}

		// Get or create sheet
		sheet = workbook.getSheet(sheetName);
		if (sheet == null) {
			sheet = workbook.createSheet(sheetName);
		}

		// Check for duplicate in the target column
		boolean isDuplicate = false;
		int lastRowNum = sheet.getLastRowNum();
		for (int i = 0; i <= lastRowNum; i++) {
			Row row = sheet.getRow(i);
			if (row != null) {
				Cell cell = row.getCell(columnNumber);
				if (cell != null && cell.getCellType() == CellType.STRING) {
					if (cell.getStringCellValue().equalsIgnoreCase(data)) {
						isDuplicate = true;
						System.out.println("Duplicate entry in the Mastersheet");
						break;
					}
				}
			}
		}

		// Write the data if not duplicate
		if (!isDuplicate) {
			Row newRow = sheet.createRow(lastRowNum + 1);
			Cell newCell = newRow.createCell(columnNumber);
			newCell.setCellValue(data);
			System.out.println("Data Written in Mastersheet - " + data);
			writeData(fileName, sheetname, data);

		}

		// Write to file
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		workbook.write(fileOutputStream);
		fileOutputStream.close();
		workbook.close();
	}

	public static void writeData(String fileName, String sheetName, String data) {
		String filePath = fileName;
		String dataToWrite = data;

		try (FileInputStream fis = new FileInputStream(filePath); Workbook workbook = new XSSFWorkbook(fis)) {

			Sheet sheet = workbook.getSheet(sheetName);
			if (sheet == null) {
				sheet = workbook.createSheet(sheetName);
			}

			int lastRowNum = sheet.getLastRowNum();
			int writeRowIndex = 0;

			// Find the first empty row
			for (int i = 0; i <= lastRowNum; i++) {
				Row existingRow = sheet.getRow(i);
				Cell existingCell = (existingRow != null) ? existingRow.getCell(0) : null;

				if (existingRow == null || existingCell == null || existingCell.getCellType() == CellType.BLANK
						|| (existingCell.getCellType() == CellType.STRING
								&& existingCell.getStringCellValue().isEmpty())) {
					writeRowIndex = i;
					break;
				} else {
					writeRowIndex = lastRowNum + 1; // All filled, write on a new row
				}
			}

			Row writeRow = sheet.getRow(writeRowIndex);
			if (writeRow == null) {
				writeRow = sheet.createRow(writeRowIndex);
			}

			Cell writeCell = writeRow.createCell(0); // Always write in first column
			writeCell.setCellValue(dataToWrite);

			// Save changes
			try (FileOutputStream fos = new FileOutputStream(filePath)) {
				workbook.write(fos);
				System.out.println("Data written at row index: " + writeRowIndex);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------- Create New Excel File-----------------------------------------------------
	public static void createExcelFile(String fileName) {
		// Create a new workbook
		Workbook workbook = new XSSFWorkbook();
		// Sheet sheet;
		// Create a new sheet
		// sheet = workbook.createSheet(sheetName1);
		// sheet = workbook.createSheet(sheetName2);

		// Create a row
		// Row row = sheet.createRow(0);

		// Create cells
		// row.createCell(0).setCellValue("Links");
		// row.createCell(1).setCellValue("Password");

		// Add data to next row
		// Row dataRow = sheet.createRow(1);
		// dataRow.createCell(0).setCellValue(links);
		// dataRow.createCell(1).setCellValue("admin123");

		// Write to file
		try (FileOutputStream fileOut = new FileOutputStream(fileName + ".xlsx")) {
			workbook.write(fileOut);
			workbook.close();
			System.out.println("Excel file created successfully!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeDatainExcel(String sheetName, int rownum, int cellnum, String data) throws IOException {
		File file = new File(System.getProperty("user.dir") + "//" + stamp + "- Unique Links.xlsx");
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
	
	
	public void compareData(String sheetName, int columnNumber, String data) throws IOException {

		String masterFilePath = System.getProperty("user.dir") + "//Master Sheet.xlsx";
		String newFilePath = System.getProperty("user.dir") + "//" + stamp + "- Unique Links.xlsx";

		boolean isDuplicate = false;

		// Load Master Sheet
		FileInputStream masterInputStream = new FileInputStream(masterFilePath);
		Workbook masterWorkbook = new XSSFWorkbook(masterInputStream);
		Sheet masterSheet = masterWorkbook.getSheet(sheetName);

		if (masterSheet != null) {
			int lastRowNum = masterSheet.getLastRowNum();
			for (int i = 0; i <= lastRowNum; i++) {
				Row row = masterSheet.getRow(i);
				if (row != null) {
					Cell cell = row.getCell(columnNumber);
					if (cell != null && cell.getCellType() == CellType.STRING) {
						if (cell.getStringCellValue().equalsIgnoreCase(data)) {
							isDuplicate = true;
							break;
						}
					}
				}
			}
		}

		masterWorkbook.close();
		masterInputStream.close();

		// If not duplicate, write to new file
		if (!isDuplicate) {
			File newFile = new File(newFilePath);
			Workbook newWorkbook;
			Sheet newSheet;

			if (newFile.exists()) {
				FileInputStream newFileInput = new FileInputStream(newFile);
				newWorkbook = new XSSFWorkbook(newFileInput);
				newFileInput.close();
			} else {
				newWorkbook = new XSSFWorkbook();
			}

			newSheet = newWorkbook.getSheet(sheetName);
			if (newSheet == null) {
				newSheet = newWorkbook.createSheet(sheetName);
			}

			int newLastRow = newSheet.getLastRowNum();
			Row newRow = newSheet.createRow(newLastRow + 1);
			Cell newCell = newRow.createCell(columnNumber);
			newCell.setCellValue(data);

			FileOutputStream outputStream = new FileOutputStream(newFile);
			newWorkbook.write(outputStream);
			outputStream.close();
			newWorkbook.close();
		} else {
			System.out.println("Data already exists in Master Sheet.");
		}

	}
	@Test(priority = 3, dependsOnMethods = "analyseURLs")
	public void deleteExcelIfNoSheet() throws FileNotFoundException, IOException {
		String Country = getPropertyFileValue("Country");
		String filePath = System.getProperty("user.dir") + "\\" + stamp + "-" + Country + "- New sheet.xlsx"; // Replace with actual file path
		File file = new File(filePath);

		if (!file.exists()) {
			System.out.println("File does not exist.");
			return;
		}

		try (FileInputStream fis = new FileInputStream(file); Workbook workbook = new XSSFWorkbook(fis)) {

			int numberOfSheets = workbook.getNumberOfSheets();
			if (numberOfSheets == 0) {
				// Close workbook before deleting file
				workbook.close();

				if (file.delete()) {
					System.out.println("Excel file deleted as it contains no sheets.");
				} else {
					System.out.println("Failed to delete the file.");
				}
			} else {
				System.out.println("Excel file has sheets, not deleted.");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
