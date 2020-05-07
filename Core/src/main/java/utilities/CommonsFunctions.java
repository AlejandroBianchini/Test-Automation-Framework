package utilities;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.ElementOption;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CommonsFunctions {
    protected static WebDriver driver;
    private static WebDriverWait wait;

    protected CommonsFunctions(WebDriver _driver) throws IOException {
        driver = _driver;
    }

    public static void goTo(String url){
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

    public static void TakeScreenShot(String fileName, String path) throws IOException {
        TakesScreenshot scrShot =((TakesScreenshot)driver);
        File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
        File DestFile = new File(path + fileName);
        FileUtils.copyFile(SrcFile, DestFile);
    }

    //Web Actions
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

    //Mobile Actions
    public static void tap(WebElement element){
        TouchAction touchAction = new TouchAction((MobileDriver) driver);
        touchAction.tap(new ElementOption().withElement(element)).perform();
    }

    public static void press(WebElement element){
        TouchAction touchAction = new TouchAction((MobileDriver)driver);
        touchAction.press(new ElementOption().withElement(element)).perform();
    }

    public static void longPress(WebElement element){
        TouchAction touchAction = new TouchAction((MobileDriver) driver);
        touchAction.longPress(new ElementOption().withElement(element)).perform();
    }

    //Explicit Waits

    public static WebElement explicitElementWait(int time, ExpectedCondition<WebElement> expectedCondition){
        wait = new WebDriverWait(driver, time);
        return wait.until(expectedCondition);
    }

    public static Boolean explicitBooleanWait(int time, ExpectedCondition<Boolean> expectedCondition){
        wait = new WebDriverWait(driver, time);
        return wait.until(expectedCondition);
    }

    public static void explicitFrameWait(int time, ExpectedCondition<WebDriver> expectedCondition){
        wait = new WebDriverWait(driver, time);
        wait.until(expectedCondition);
    }

}
