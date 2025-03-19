package hexas;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import hexas.db.dbo.Product;

public class SeleniumProductScannerCdisount {


	public static void main(String[] args) {
		FirefoxOptions options = new FirefoxOptions();
		FirefoxDriver driver = new FirefoxDriver(options);
		try {
			SeleniumProductScanner
					.doScanGroup(driver, Product
							.builder()
							.metaModel("test")
							.url("https://www.pccomponentes.fr/cartes-graphiques/amd-radeon-rx-9070-xt?seller=pccomponentes")
							.build());
		} finally {
			driver.quit();
		}

	}

}
