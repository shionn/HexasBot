package hexas;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import hexas.db.dbo.Product;

public class SeleniumProductScannerMain {


	public static void main(String[] args) {
		FirefoxOptions options = new FirefoxOptions();
		FirefoxDriver driver = new FirefoxDriver(options);
		try {
			SeleniumProductScanner
					.doScanGroup(driver, Product
							.builder()
							.metaModel("test")
							.url("https://www.ldlc.com/informatique/pieces-informatique/carte-graphique-interne/c4684/+fv121-126518.html")
							.build());
		} finally {
			driver.quit();
		}

	}

}
