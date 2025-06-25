package Test_Case;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

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
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Run_the_ExcelLinks {
	
	
	public static String stamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
	
	WebDriver driver;

	@BeforeTest
	public void browseropen() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
	}

	@AfterTest
	public void closebroser() {
		driver.close();
	}

	
	// -------------------------------------------------Fetched link Validation and Filter----------------------------------------------
	static int iterate=0;
	@Test(dataProvider = "runURL")
	public void analyseURLs(String URL) throws IOException, InterruptedException {
		driver.get(URL);
		try {
			driver.findElement(By.xpath("//a[text()='Follow Link']")).click();
		} catch (Exception e) {
		//	System.out.println("It redirects directly to the app page");
		}
		System.out.println(iterate+" - "+ URL);
		iterate++;
		Thread.sleep(2000);
	}
	

	//----------------------------------------------Data Provider Code--------------------------------------------------------------------
	@DataProvider(name = "runURL")
	public Object[][] getDataFromExcel() {
		String filePath = System.getProperty("user.dir") + "\\2025.06.20.16.24.24.xlsx";
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

}
