package Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.DataProvider;



public class Data_Provider extends XLUtils {
	@DataProvider (name="Link_Data_Auto")
	public String [][] fetchData() throws IOException{
//		String path=System.getProperty("user.dir")+"\\playLinks.xlsx";
//		int sheetNum=0;
//		int totalrows=getRowCount(path, "Sheet1");
//		int totalcols=getCellCount(path, "Sheet1", 0);
//		
//		String loginData[][]=new String[totalrows][totalcols];
//		for (int i = 0; i <=totalrows; i++) {
//			for (int j = 1; j < totalcols; j++) {
//			loginData[i-1][j-1]=getCellData(path, sheetNum, i, j);
//			}
//		}
//		return loginData;
	    String path = System.getProperty("user.dir") + "\\playLinks.xlsx";
	    String sheetName = "Sheet1";

	    int totalRows = getRowCount(path, sheetName);      // Assuming it returns total usable rows (e.g. excluding header)
	    int totalCols = getCellCount(path, sheetName, 0);  // Columns in the first row

	    String[][] loginData = new String[totalRows][totalCols];

	    for (int i = 0; i < totalRows; i++) {
	        for (int j = 0; j < totalCols; j++) {
	            loginData[i][j] = getCellData(path, sheetName, i, j); // No offset needed
	        }
	    }

	    return loginData;
	}
	   @DataProvider(name = "excelData")
	    public Object[][] getDataFromExcel() {
	        String filePath = System.getProperty("user.dir") + "\\playLinks.xlsx";
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
	
	/*
	
	@DataProvider (name="Login_Data_Auto")
	public String [][] getData() throws IOException{
		String path=System.getProperty("user.dir")+"\\SignPostAdminLogin.xlsx";
		int sheetNum=0;
		int totalrows=getRowCount(path, "Sheet1");
		int totalcols=getCellCount(path, "Sheet1", 1);
		
		String loginData[][]=new String[totalrows][totalcols];
		for (int i = 1; i <=totalrows; i++) {
			for (int j = 0; j < totalcols; j++) {
			loginData[i-1][j]=getCellData(path, sheetNum, i, j);
			}
		}
		return loginData;
	}
	
	 @DataProvider (name="Login_Data_Manual")
     public String [][] getDataManual() throws IOException{
  		String path =System.getProperty("user.dir")+"\\SignPostAdminLogin.xlsx";
  		int sheetNum=0;
  		int startRows=1;
  		int Endrows=4;
  		int startCols=0;
  		int Endcols=9;
  		
  	
  		String loginData[][]=new String[Endrows][Endcols];
  		for (int i = startRows; i <=Endrows; i++) {	
  			for (int j = startCols; j < Endcols; j++) {		
  			loginData[i-1][j]=getCellData(path, sheetNum, i, j);
  			}
  		}
  		return loginData;
  		}*/
}
