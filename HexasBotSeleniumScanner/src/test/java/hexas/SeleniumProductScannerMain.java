package hexas;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import hexas.db.dbo.Product;

public class SeleniumProductScannerMain {


	public static void main(String[] args) {
		FirefoxOptions options = new FirefoxOptions();
		FirefoxDriver driver = new FirefoxDriver(options);
		try {
//			SeleniumProductScanner
//			.doScanGroup(driver, Product
//					.builder()
//					.metaModel("test")
//					.url("https://www.amazon.fr/s?i=merchant-items&me=A206ULAR6UT9T8&s=price-desc-rank&ds=v1%3AL8FKdDrQvaT7IqoGw0eDRxn03hGY2IVdOe8r%2FadKSiM&marketplaceID=A13V1IB3VIYZZH&qid=1742419456&ref=sr_st_price-desc-rank")
//					.build());
			SeleniumProductScanner
					.doScan(driver, Product
					.builder()
					.metaModel("test")
					.url("https://www.amazon.fr/s?i=merchant-items&me=A206ULAR6UT9T8&s=price-desc-rank&ds=v1%3AL8FKdDrQvaT7IqoGw0eDRxn03hGY2IVdOe8r%2FadKSiM&marketplaceID=A13V1IB3VIYZZH&qid=1742419456&ref=sr_st_price-desc-rank")
					.build());
		} finally {
			driver.quit();
		}

	}

}
