package pages.webPages;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utilities.CommonsFunctions;
import utilities.GlobalVariablesReader;

import java.io.File;
import java.io.IOException;

public class HomePage extends CommonsFunctions {
    @FindBy(how = How.NAME, using = "q")
    private static WebElement searchBar;

    public HomePage(WebDriver driver) throws IOException {
        super(driver);
        PageFactory.initElements(driver, this);
        Goto();
    }

    public void Goto() throws IOException {
        String url = new GlobalVariablesReader().getProperty("url");
        goTo(url);
    }

    public void Buscar() {
        explicitElementWait(5, ExpectedConditions.elementToBeClickable(searchBar));
        searchBar.sendKeys("Selenium");
        searchBar.submit();
    }

    public String ObtieneResultado()
    {
        explicitElementWait(5, ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='ZpxfC']/div/h3/span")));
        return findBy(By.xpath("//*[@id='ZpxfC']/div/h3/span")).getText();
    }

    private String ObtieneDatosExcel() throws IOException, InvalidFormatException {
        File file = new File("src/test/resources/data/Data.xlsx");
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(0);

        return row.getCell(0).getStringCellValue();
    }
}
