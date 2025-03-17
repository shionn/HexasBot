package hexas.drop;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.session.SqlSession;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import hexas.db.SpringSessionFactory;
import hexas.db.dao.ProductScanDao;
import hexas.db.dbo.Product;
import hexas.parser.PageParser;
import hexas.parser.PageParserRetreiver;

@Component
public class DropScanner implements Serializable {
	private static final long serialVersionUID = -1905973814573778921L;
	private static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64; rv:136.0) Gecko/20100101 Firefox/136.0";

	private Iterator<Product> products;
	private Map<String, Map<String, String>> cookiePerSites = new HashMap<>();

	@Scheduled(fixedDelay = 8, timeUnit = TimeUnit.SECONDS)
	public void scanWithJsoop() {
		if (products == null || !products.hasNext()) {
			System.out.println("list all product to scan ");
			try (SqlSession session = new SpringSessionFactory().open()) {
				products = session.getMapper(ProductScanDao.class).list("jsoop").iterator();
			}
		} else {
			try {
				scan(products.next());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void scan(Product product) throws IOException {
		System.out.println("scan de " + product);
		PageParser parser = new PageParserRetreiver().resolve(product);
		Map<String, String> cookies = getCookies(parser.getClass().getSimpleName());
		Connection connection = Jsoup
				.connect(product.getUrl())
				.header("User-Agent", USER_AGENT)
				.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
				.header("DNT", "1")
				.header("Priority", "u=0, i")
				.header("Sec-Fetch-Dest", "document")
				.header("Sec-Fetch-Mode", "navigate")
				.header("Sec-Fetch-Site", "none")
				.header("Sec-Fetch-User", "?1")
				.header("TE", "trailers")
				.header("Upgrade-Insecure-Requests", "1")
				.ignoreHttpErrors(true)
				.followRedirects(true)
				.cookies(cookies);
		Response response = connection.execute();
		cookies.putAll(response.cookies());
		parser.parse(response.parse(), product);
	}

	private Map<String, String> getCookies(String site) {
		Map<String, String> cookies = cookiePerSites.get(site);
		if (cookies == null) {
			cookies = new HashMap<String, String>();
			cookiePerSites.put(site, cookies);
		}
		return cookies;
	}

}
