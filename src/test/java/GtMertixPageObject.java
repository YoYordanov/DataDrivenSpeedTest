import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class GtMertixPageObject {

    @FindBy(css = ".js-auth-widget-link")
    public static WebElement logInButton;

    @FindBy(id = "li-email")
    public static WebElement emailField;

    @FindBy(id = "li-password")
    public static WebElement passwordField;

    @FindBy(css = ".analyze-form-options-trigger")
    public static WebElement analysisOptionsButton;

    @FindBy(css = ".js-analyze-form-url")
    public static WebElement pageUrl;

    @FindBy(css = ".report-page-details")
    public static WebElement pageDetailsLabel;

    @FindBy(css = ".report-page-detail-value")
    public static List<WebElement> testResults;

    @FindBy(xpath = "/html/body/div[1]/header/div/ul/li[1]/span")
    public static WebElement headerUserName;

}
