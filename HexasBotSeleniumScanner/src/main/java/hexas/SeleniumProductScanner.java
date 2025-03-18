package hexas;

import java.time.Duration;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
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
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
		driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
		try(SqlSession session = new SessionFactory().open()) {
			List<Product> products = session.getMapper(ProductScanDao.class).list("selenium");
			for (Product product : products) {
				doScan(driver, product);
			}
		}
		driver.close();

	}

	private static void doScan(FirefoxDriver driver, Product product) {
		try {
			System.out.println("scan " + product);
			driver.get(product.getUrl());
			String source = driver.getPageSource();
//			if (source.contains("Cookies et choix publicitaires")) {
//				driver.findElement(Bys.id("sp-cc-rejectall-link")).click();
//			}
//			if (source.contains("Rejeter les cookies")) {
//				driver.findElement(Bys.text("Rejeter les cookies")).click();
//			}
//			if (source.contains("Do not accept")) {
//				driver.findElement(Bys.text("Do not accept")).click();
//			}
			PageParser parser = new PageParserRetreiver().resolve(product);
			parser.parse(Jsoup.parse(source), product);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
