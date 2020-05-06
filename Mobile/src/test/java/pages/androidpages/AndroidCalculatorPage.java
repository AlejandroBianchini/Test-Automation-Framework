package pages.androidpages;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import utilities.CommonsFunctions;
import java.io.IOException;

public class AndroidCalculatorPage extends CommonsFunctions {

    @FindBy(id = "com.android.calculator2:id/digit_1")
    private WebElement btn1;
    @FindBy(how = How.ID, using = "com.android.calculator2:id/digit_5")
    private WebElement btn5;
    @FindBy(id = "com.android.calculator2:id/op_add")
    private WebElement btnPlus;
    @FindBy(id = "com.android.calculator2:id/eq")
    private WebElement btnEquals;
    @FindBy(id = "com.android.calculator2:id/result")
    private WebElement panelResult;

    public AndroidCalculatorPage(MobileDriver driver) throws IOException {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public String Sumar()
    {
//        btn1.click();
        tap(btn1);
//        btnPlus.click();
        tap(btnPlus);
//        btn1.click();
        tap(btn1);
        btnEquals.click();

        return waitVisibilityOf(panelResult, 5).getText();
    }

}
