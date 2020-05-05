package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.testng.ITestResult;
import org.testng.xml.XmlTest;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class Report {
    private static String PATH_REPORT = System.getProperty("user.dir") + "\\test-output\\ExtentReports\\";
    private static String PATH_IMG = PATH_REPORT + "img\\";
    private static ExtentHtmlReporter htmlReport = new ExtentHtmlReporter(PATH_REPORT + "Reporte - " + dateNow() + ".html");
    private static ExtentReports _instance = new ExtentReports();
    private static ArrayList<ExtentTest> tests = new ArrayList<>();
    private static ExtentTest test;
    private static String testName;

    public Report() {
        cleanDirectorys(PATH_REPORT);
        cleanDirectorys(PATH_IMG);
        _instance.attachReporter(htmlReport);
    }

    public void addSystemInfo(XmlTest xml){
        Object systemInfo = xml.getAllParameters();
        ((Map) systemInfo).forEach((k, v) -> _instance.setSystemInfo((String) k, (String) v));
    }

    public void startTest(ITestResult result){
        testName = result.getMethod().getMethodName();
        String category = result.getMethod().getTestClass().getName().replace("testclasses.", "");
//        String description = result.getTestContext().getName();
        test = _instance.createTest(testName).assignCategory(category);
    }

    public void addTestReport(ITestResult result) {
        int status = result.getStatus();

        Status logstatus = null;

        switch (status) {
            case 1:
                logstatus = Status.PASS;
                break;
            case 2:
                logstatus = Status.FAIL;
                break;
            default:
                break;
        }

        test.log(logstatus, "The test has ended with status " + logstatus);

        if (logstatus == Status.FAIL) {
            try {
                String _fileName = testName + " " + dateNow() + ".png";
                CommonsFunctions.TakeScreenShot(_fileName, PATH_IMG);
                test.log(logstatus, "Last step screenshot", MediaEntityBuilder.createScreenCaptureFromPath("../ExtentReports/img/" + _fileName).build());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        tests.add(test);
    }

    public void generateReport()
    {
        for (ExtentTest t: tests) {
            _instance.flush();
        }
    }

    private static void cleanDirectorys(String path) {
        File directorio = new File(path);
        File[] files = directorio.listFiles();
        File f;

        if (directorio.exists()) {
            for (int i = 0; i < files.length; i++) {
                f = new File(files[i].toString());
                f.delete();
            }

        }
    }

    private static String dateNow() {
        SimpleDateFormat objSDF = new SimpleDateFormat("dd-MMM-aaaa kkmmss a");
        return objSDF.format(new Date());
    }

   /* private static void addScreenshotToReport(WebDriver driver, String fileName) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File finalScreenshotLocation = new File(PATH_IMG + fileName);
            FileUtils.copyFile(screenshot, finalScreenshotLocation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
