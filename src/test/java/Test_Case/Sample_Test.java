package Test_Case;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;
import java.io.FileInputStream;
import java.security.GeneralSecurityException;
import java.util.*;

public class Sample_Test extends Meta_Phase2_Copy_1{
	


	    private static final String APPLICATION_NAME = "Google Sheets API Java";
	    private static final String SPREADSHEET_ID = "14D5ntocrV4nVN5RocvRPApsm7J5TLaUpuL64_39QdX8"; // Change this
	    private static final String RANGE = "Sheet1!A:A"; // Column A

	    @SuppressWarnings("deprecation")
		private static Sheets getSheetsService() throws IOException, GeneralSecurityException {
	        FileInputStream serviceAccountStream = new FileInputStream("path-to-your-service-account.json");

	        GoogleCredential credential = GoogleCredential.fromStream(serviceAccountStream)
	                .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));

	        return new Sheets.Builder(credential.getTransport(), credential.getJsonFactory(), credential)
	                .setApplicationName(APPLICATION_NAME)
	                .build();
	    }

	    @Test
	    public void writeDataSkippingExistingCells() throws IOException, GeneralSecurityException {
	        Sheets service = getSheetsService();

	        List<String> dataToInsert = Arrays.asList("Apple", "Banana", "Cherry", "Mango");

	        // 1. Read current values in column A
	        ValueRange readResult = service.spreadsheets().values()
	                .get(SPREADSHEET_ID, RANGE)
	                .execute();

	        List<List<Object>> existingRows = readResult.getValues();
	        int startRow = existingRows != null ? existingRows.size() + 1 : 1;

	        // 2. Prepare data to write starting from next empty cell
	        List<List<Object>> values = new ArrayList<>();
	        for (String item : dataToInsert) {
	            values.add(Collections.singletonList(item));
	        }

	        ValueRange body = new ValueRange()
	                .setValues(values);

	        String rangeToWrite = "Sheet1!A" + startRow;
	        service.spreadsheets().values()
	                .update(SPREADSHEET_ID, rangeToWrite, body)
	                .setValueInputOption("RAW")
	                .execute();

	        System.out.println("Data written starting at row: " + startRow);
	    }
	

	
	
	
//@Test
public  void test() throws IOException {
	
	String URL="https://play.google.com/store/apps/details?id=com.gameloft.android.ANMP.GloftA9HM&pcampaignid=merch_published_cluster_promotion_battlestar_top_picks";
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
}
}
