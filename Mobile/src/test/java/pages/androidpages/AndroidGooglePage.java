package pages.androidpages;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import org.omg.CORBA.WStringSeqHelper;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.CommonsFunctions;
import utilities.Config;

import java.io.IOException;

public class AndroidGooglePage extends CommonsFunctions {

    @FindBy(name = "q")
    private WebElement searchBar;

    public AndroidGooglePage(MobileDriver driver) throws IOException {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void Goto(){
        goTo("https://www.google.com/");
    }

    public void searchSomething(String text){
        searchBar.sendKeys(text);
        searchBar.sendKeys((Keys.ENTER));
    }

}
