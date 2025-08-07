package hexas;

import static org.openqa.selenium.By.cssSelector;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumHumbleBundleScanner {

	private static final String ROOT_URL = "https://www.humblebundle.com";

	public static void main(String[] args) throws InterruptedException {
		new SeleniumHumbleBundleScanner().doParsing();
	}

	private void doParsing() throws InterruptedException {
		FirefoxDriver driver = new SeleniumInitier().initDriver();
		try {
			driver.get(ROOT_URL + "/games");
			TimeUnit.SECONDS.sleep(1);
			List<String> urls = driver
					.findElements(cssSelector("a.full-tile-view"))
					.stream()
					.map(e -> e.getDomAttribute("href"))
					.toList();
			for (String url : urls) {
				parseBundle(driver, ROOT_URL + url);
			}

		} finally {
			driver.quit();
		}

	}

	private void parseBundle(FirefoxDriver driver, String url) throws InterruptedException {
		System.out.println(url);
		driver.get(url);
		TimeUnit.SECONDS.sleep(1);
		System.out.println(driver.findElement(cssSelector(".bundle-title img")).getDomAttribute("alt"));
		for (WebElement e : driver.findElements(cssSelector(".tier-filters a"))) {
			e.click();
			System.out.println("  " + driver.findElement(cssSelector("h3.tier-header")).getText());
			driver
					.findElements(cssSelector(".tier-item-view .item-title"))
					.forEach(el -> System.out.println("    " + el.getText()));
		}
	}
}
