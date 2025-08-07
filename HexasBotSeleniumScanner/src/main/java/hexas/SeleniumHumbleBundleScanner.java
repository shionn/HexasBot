package hexas;

import static org.openqa.selenium.By.cssSelector;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import hexas.db.SessionFactory;
import hexas.db.dao.HumbleBundleDao;
import hexas.db.dbo.Bundle;
import hexas.db.dbo.BundleChoice;

public class SeleniumHumbleBundleScanner {

	private static final String ROOT_URL = "https://www.humblebundle.com";

	public static void main(String[] args) throws InterruptedException, ParseException {
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
				try (SqlSession session = new SessionFactory().open()) {
					Bundle bundle = parseBundle(driver, ROOT_URL + url);
					HumbleBundleDao dao = session.getMapper(HumbleBundleDao.class);
					if (!dao.exists(bundle)) {
						System.out.println("Nouveau bundle " + bundle.getName());
						dao.create(bundle);
						bundle.getChoices().forEach(choice -> {
							dao.createChoice(bundle, choice);
							choice.getGames().forEach(game -> {
								dao.createChoiceGame(choice, game);
							});
						});
						session.commit();
					}
				} catch (Exception e) {
					System.out.println("can not parse bundle : " + url);
					e.printStackTrace();
				}
			}

		} finally {
			driver.quit();
		}

	}

	private Bundle parseBundle(FirefoxDriver driver, String url) throws InterruptedException, ParseException {
		driver.get(url);
		TimeUnit.SECONDS.sleep(1);
		return Bundle
				.builder()
				.url(url)
				.name(driver.findElement(cssSelector(".bundle-title img")).getDomAttribute("alt"))
				.endDate(extractEndDate(driver))
				.choices(extractChoices(driver))
				.build();
	}

	private List<BundleChoice> extractChoices(FirefoxDriver driver) throws ParseException, InterruptedException {
		List<BundleChoice> choices = new ArrayList<>();
		List<WebElement> choiceElements = driver.findElements(cssSelector(".tier-filters a"));
		if (choiceElements.isEmpty()) {
			choices.add(BundleChoice.builder().games(retreiveGames(driver)).price(retreivePrice(driver)).build());
		} else {
			for (WebElement e : choiceElements) {
				e.click();
				TimeUnit.SECONDS.sleep(1);
				choices.add(BundleChoice.builder().games(retreiveGames(driver)).price(retreivePrice(driver)).build());
			}
		}
		return choices;
	}

	private BigDecimal retreivePrice(FirefoxDriver driver) throws ParseException {
		Matcher m = Pattern
				.compile("Payez au moins (\\d+,\\d+) ")
				.matcher(driver.findElement(cssSelector("h3.tier-header")).getText());
		m.find();
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(' ');
		symbols.setDecimalSeparator(',');
		symbols.setCurrencySymbol("€");
		return BigDecimal.valueOf(new DecimalFormat("", symbols).parse(m.group(1)).doubleValue());
	}

	private List<String> retreiveGames(FirefoxDriver driver) {
		return driver
				.findElements(cssSelector(".tier-item-view .item-title"))
				.stream()
				.map(WebElement::getText)
				.toList();
	}

	private Date extractEndDate(FirefoxDriver driver) {
		WebElement jsonLdScript = driver.findElement(By.xpath("//script[@type='application/ld+json']"));
		String jsonLdContent = jsonLdScript.getDomProperty("innerHTML").replace('+', ' ');
		JSONObject jsonObject = new JSONObject(jsonLdContent);
		JSONObject offers = jsonObject.getJSONObject("offers");
		String availabilityEnds = offers.getString("availabilityEnds");
		return Date.from(LocalDateTime.parse(availabilityEnds).toInstant(ZoneOffset.UTC));
	}
}
