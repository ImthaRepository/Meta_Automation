package Test_Case;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.Properties;

import org.testng.TestNG;

public class Test_Runner {
	

	public static void main(String[] args) throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream(System.getProperty("user.dir") + "\\input.properties"));
		Object object = properties.get("runs");
		String value = (String) object;
		
		int numberOfRuns = Integer.parseInt(value); // Set the number of times to run the suite
		   Meta_Master_Code logs = new Meta_Master_Code();
           logs.logger.info("The number of Runs input is - "+numberOfRuns);
        for (int i = 1; i <= numberOfRuns; i++) {
            System.out.println("----- Running TestNG Suite: Run #" + i + " -----");
            logs.logger.info("----- Running TestNG Suite: Run #" + i + " -----");
            TestNG testng = new TestNG();
            testng.setTestSuites(Collections.singletonList("Meta_MasterCode.xml")); // Path to your suite
            testng.run();
            logs.logger.info("----- Completed Run #" + i + " -----\n");
            System.out.println("----- Completed Run #" + i + " -----\n");
        }
    }
}
