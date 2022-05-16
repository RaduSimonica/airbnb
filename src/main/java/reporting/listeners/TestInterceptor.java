package reporting.listeners;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestInterceptor implements ITestListener {

    private static Logger LOGGER = LogManager.getLogger();

    public void onTestStart(ITestResult result) {
        LOGGER.log(Level.INFO, String.format("Started test: %s", result.getName()));
    }

    public void onTestSuccess(ITestResult result) {
        LOGGER.log(Level.INFO, String.format("SUCCESS on test: %s", result.getName()));
    }

    public void onTestFailure(ITestResult result) {
        LOGGER.log(Level.INFO, String.format("FAILURE on test: %s", result.getName()));
    }

    public void onTestSkipped(ITestResult result) {
        LOGGER.log(
                Level.INFO,
                String.format(
                        "SKIPPED test: %s due to: %s",
                        result.getName(),
                        result.getSkipCausedBy()
                )
        );
    }
}
