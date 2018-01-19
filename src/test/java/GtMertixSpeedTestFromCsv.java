import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class GtMertixSpeedTestFromCsv {
    static WebDriver driver;
    static WebDriverWait wait;

    private String country;
    private String url1;
    private String value;
    private String connectionValue;
    private String connectionName;

    public GtMertixSpeedTestFromCsv(String country, String url1, String value, String connectionValue, String connectionName){
        this.country = country;
        this.url1 = url1;
        this.value = value;
        this.connectionValue = connectionValue;
        this.connectionName = connectionName;
    }

    @Parameterized.Parameters
    public static Collection data() throws IOException {
        return csvAsCollectionOfStringArrays(System.getProperty("user.dir")+"\\csvdata\\testdata.csv");
    }

    public static Collection<String[]> csvAsCollectionOfStringArrays(String csvFileName)throws IOException {
        List<String[]> records = new ArrayList<String[]>();
        String record;
        BufferedReader file = new BufferedReader(new FileReader(System.getProperty("user.dir")+"\\csvdata\\testdata.csv"));
        while ((record=file.readLine())!=null) {
            String fields[] = record.split(",");
            records.add(fields);
        }
        file.close();
        return records;
    }

    @BeforeClass
    public static void setUp() {
        driver = BrowserFactory.getBrowser("Chrome");
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10);
        GtMertixPageObject gtMertixPage = new GtMertixPageObject();
        PageFactory.initElements(driver, GtMertixPageObject.class);

        //Go to GTmetrix and login -- start
        driver.get("https://gtmetrix.com/");
        wait.until(ExpectedConditions.visibilityOf(gtMertixPage.logInButton));
        gtMertixPage.logInButton.click();
        wait.until(ExpectedConditions.elementToBeClickable(gtMertixPage.emailField));
        gtMertixPage.emailField.sendKeys("yoyordanovtestmail@gmail.com");
        gtMertixPage.passwordField.sendKeys("Test1234!");
        gtMertixPage.passwordField.submit();
        wait.until(ExpectedConditions.elementToBeClickable(gtMertixPage.analysisOptionsButton));
        //Go to GTmetrix and login -- end
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }

    @Test
    public void testCase1Test() throws InterruptedException, IOException {
        GtMertixPageObject gtMertixPage = new GtMertixPageObject();
        PageFactory.initElements(driver, GtMertixPageObject.class);

        //Passing URL, selecting country and submitting --start
        driver.get("https://gtmetrix.com/");
        Assert.assertTrue(driver.getTitle().equals("Dashboard | GTmetrix"));
        Assert.assertEquals("Expected the header contains: Welcome Yordan! but actual it contains: "+gtMertixPage.headerUserName,
                "Welcome Yordan!", gtMertixPage.headerUserName.getText());
        wait.until(ExpectedConditions.elementToBeClickable(gtMertixPage.analysisOptionsButton)).click();
        Select selectCountry = new Select(driver.findElement(By.cssSelector("#af-region")));
        selectCountry.selectByValue(this.value);
        Select selectConnection = new Select(driver.findElement(By.cssSelector("#af-connection")));
        selectConnection.selectByValue(this.connectionValue);
        gtMertixPage.pageUrl.sendKeys(this.url1);
        Thread.sleep(500);
        gtMertixPage.pageUrl.submit();
        //Passing URL and selecting country and submitting --end

        //Extracting information -- start
        new WebDriverWait(driver, 900).until(ExpectedConditions.visibilityOf(gtMertixPage.pageDetailsLabel));
        Assert.assertTrue("Expected test result must be displayed but actual test results are not displayed",gtMertixPage.testResults.size()>0);
        System.out.println();
        System.out.println(this.country);
        System.out.println(this.connectionName);
        System.out.println(this.url1);
        System.out.println("Onload Time - " + gtMertixPage.testResults.get(0).getText());
        System.out.println("Total Page Size - " + gtMertixPage.testResults.get(1).getText());
        System.out.println("Requests - " + gtMertixPage.testResults.get(2).getText());
        //Extracting information -- end
    }

}
