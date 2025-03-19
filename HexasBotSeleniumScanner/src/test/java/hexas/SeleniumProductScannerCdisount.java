package hexas;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import hexas.db.dbo.Product;

public class SeleniumProductScannerCdisount {

	private static final String CDISCOUNT = "https://www.cdiscount.com/informatique/cartes-graphiques/sapphire-carte-graphique-pulse-amd-radeon-rx-9/f-10767-sap4895106295919.html";
	private static final String COMPUMSA = "https://www.compumsa.eu/article-17002-design-ASRock_Radeon_RX_9070_XT_16GB_Steel_Legend_RX9070XT_SL_16G_Carte_graphique_AMD.html";

	public static void main(String[] args) {
		FirefoxOptions options = new FirefoxOptions();
		FirefoxDriver driver = new FirefoxDriver(options);
		try {
			SeleniumProductScanner
					.doScan(driver, Product
							.builder()
//							.url(CDISCOUNT)
							.url(COMPUMSA)
							.build());
		} finally {
			driver.quit();
		}

	}
}
