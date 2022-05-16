package reporting.listeners;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import utils.TimeUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SuiteInterceptor implements ISuiteListener {

    private static final Logger LOGGER = LogManager.getLogger();

    private LocalDateTime startDateTime;

    public void onStart(ISuite suite) {
        this.startDateTime = TimeUtils.getNow();
        LOGGER.log(
                Level.INFO,
                String.format(
                        "Started running suite: %s at: %s",
                        suite.getName(),
                        this.startDateTime.format(DateTimeFormatter.ISO_DATE_TIME)
                )
        );
    }

    public void onFinish(ISuite suite) {
        Duration duration = Duration.between(this.startDateTime, TimeUtils.getNow());
        LOGGER.log(Level.INFO, String.format("Finished running suite: %s.", suite.getName()));
        LOGGER.log(Level.INFO, String.format("Execution took: %s", duration));
    }
}
