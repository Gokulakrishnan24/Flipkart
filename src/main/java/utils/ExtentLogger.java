package utils;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import listeners.TestListener;
import com.aventstack.extentreports.ExtentTest;

public class ExtentLogger {

    private static ExtentTest getExtentTest() {
        ExtentTest testInstance = TestListener.getTestInstance();
        if (testInstance == null) {
            throw new IllegalStateException("ExtentTest instance is null. Did the test run without initializing ExtentReports?");
        }
        return testInstance;
    }

    public static void info(String message) {
        getExtentTest().log(Status.INFO, message);
    }

    public static void pass(String message) {
        getExtentTest().log(Status.PASS, message);
    }

    public static void fail(String message) {
        getExtentTest().log(Status.FAIL, message);
    }

    public static void attachScreenshot(String path, String message) {
        try {
            getExtentTest().fail(message,
                    MediaEntityBuilder.createScreenCaptureFromPath(path).build());
        } catch (Exception e) {
            getExtentTest().fail("📸 Failed to attach screenshot: " + e.getMessage());
        }
    }
}
