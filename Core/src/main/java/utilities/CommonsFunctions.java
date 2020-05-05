package utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CommonsFunctions {
    protected static WebDriver driver;
    private static WebDriverWait wait;

    protected CommonsFunctions(WebDriver _driver) throws IOException {
        driver = _driver;
        String url = new GlobalVariablesReader().getProperty("url");
        goTo(url);
    }

    private static void goTo(String url){
        driver.get(url);
    }

    public static void quit() {
        driver.quit();
    }

    protected static WebElement findBy(By by) {
        return driver.findElement(by);
    }

    public static List<WebElement> findsBy(By by) {
        return driver.findElements(by);
    }

    protected static WebElement waitElementToBeClickable(WebElement element, int time) {
        wait = new WebDriverWait(driver, time);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected static WebElement waitElementToBeClickable(By by, int time) {
        wait = new WebDriverWait(driver, time);
        return wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    public static WebElement waitVisibilityOf(WebElement element, int time) {
        wait = new WebDriverWait(driver, time);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitVisibilityOfElementLocated(By by, int time) {
        wait = new WebDriverWait(driver, time);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public static void TakeScreenShot(String fileName, String path) throws IOException {
        TakesScreenshot scrShot =((TakesScreenshot)driver);
        File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
        File DestFile = new File(path + fileName);
        FileUtils.copyFile(SrcFile, DestFile);
    }

    public static void SwitchWindow(String window) {
        driver.switchTo().window(window);
    }

    public static void SwitchFrame(String frame) {
        driver.switchTo().frame(frame);
    }

    public static void SwitchFrame(int index) {
        driver.switchTo().frame(index);
    }

    public static void SwitchFrame(WebElement frame) {
        driver.switchTo().frame(frame);
    }

    public static void SwitchDefaultFrame() {
        driver.switchTo().defaultContent();
    }
}
