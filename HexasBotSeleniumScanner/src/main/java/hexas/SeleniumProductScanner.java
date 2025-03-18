package hexas;

import org.jsoup.Jsoup;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import hexas.db.SessionFactory;
import hexas.db.dao.ProductScanDao;
import hexas.db.dbo.Product;
import hexas.parser.PageParser;
import hexas.parser.PageParserRetreiver;

public class SeleniumProductScanner {

	public static void main(String[] args) {
		doParsing();
	}

	private static void doParsing() {
		FirefoxOptions options = new FirefoxOptions();
		FirefoxDriver driver = new FirefoxDriver(options);
		new SessionFactory().open().getMapper(ProductScanDao.class).list("javafx");
		try {
			Product product = Product
					.builder()
					.url("https://www.pccomponentes.fr/carte-graphique-carte-graphique-asus-tuf-gaming-amd-radeon-rx-9070-xt-oc-16-go-gddr6-fsr-4")
					.build();
			driver.get(product.getUrl());
			PageParser parser = new PageParserRetreiver().resolve(product);
			parser.parse(Jsoup.parse(driver.getPageSource()), product);
		} catch (Exception e) {
			System.out.println(e);
		}
		driver.close();

	}
}
