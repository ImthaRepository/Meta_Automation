package Test_Case;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Trash {
	WebDriver driver;
	@BeforeTest
	public void browseroper() {
		driver=new ChromeDriver();
		driver.manage().window().maximize();
	}
	
	@Test(priority = 1, dataProvider = "excelData")
	public void analyseURLs(String URL1) throws IOException {
		driver.get(URL1);
		   try {  
	            String currentUrl = driver.getCurrentUrl();  // Final redirected URL
	            String pageSource = driver.getPageSource();

	            // You can filter based on known redirected URL or page content
	            if (currentUrl.contains("/blocked") || pageSource.contains("Access Denied") || pageSource.contains("blocked")) {
	                System.out.println("Blocked or invalid URL: " + URL1);
	            } else {
	                System.out.println("Valid URL: " + currentUrl);
	            }
	        } catch (Exception e) {
	            System.out.println("Error accessing: " + URL1);
	        }
		
//		try {
//			 	String currentUrl = driver.getCurrentUrl();
//	            String pageSource = driver.getPageSource();
//
//	            // Refine block detection - use stricter matching
//	            boolean isBlocked = currentUrl.toLowerCase().contains("/blocked")
//	                    || pageSource.toLowerCase().contains("this site is blocked")
//	                    || pageSource.toLowerCase().contains("403 forbidden")
//	                    || pageSource.toLowerCase().contains("access denied")
//	            		|| pageSource.contains("Access Denied");
//
//	            if (isBlocked) {
//	                System.out.println("Blocked or suspicious URL: " + currentUrl);
//	            } else {
//	                System.out.println("✅ Valid URL: " + currentUrl);
//	            }
//
//	        } catch (Exception e) {
//	            System.out.println("Error accessing: " + URL1 + " — " + e.getMessage());
//		}
	}
	@DataProvider(name = "excelData")
	public Object[][] getDataFromExcel() {
		String filePath = "C:\\Users\\Ram prathees\\eclipse-workspace\\DCI.Meta_Automation\\excel web files filtered\\2025.07.16.16.06.14-India- New sheet.xlsx";
		String sheetName = "Sheet1";
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
