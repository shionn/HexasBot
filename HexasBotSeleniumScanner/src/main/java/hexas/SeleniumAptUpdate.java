package hexas;

import static org.openqa.selenium.By.cssSelector;

import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumAptUpdate {
	public static void main(String[] args) throws InterruptedException {
		new SeleniumAptUpdate().doUpdate();
	}

	private void doUpdate() throws InterruptedException {
		FirefoxDriver driver = new SeleniumInitier().initDriver();
		try {
			driver.get("http://aptcache:3142/acng-report.html");
			driver.findElement(cssSelector("[name=doDownload]")).click();
			driver.findElement(cssSelector("[name=doMirror]")).click();
		} finally {
			driver.quit();
		}
	}

}
