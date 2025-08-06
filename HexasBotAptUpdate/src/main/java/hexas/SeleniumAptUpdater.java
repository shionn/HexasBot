package hexas;

import java.time.Duration;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class SeleniumAptUpdater {

	public static void main(String[] args) {
		FirefoxOptions options = new FirefoxOptions();
		FirefoxDriver driver = new FirefoxDriver(options);
		try {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
			driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
			// http://aptcache:3142/acng-report.html?abortOnErrors=aOe&calcSize=cs&doDownload=dd&asNeeded=an&doMirror=Start+Mirroring#bottom

			// End of log output. Please reload to run again.
		} finally {
			driver.quit();
		}

	}
}
