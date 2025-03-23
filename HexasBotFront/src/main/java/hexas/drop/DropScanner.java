package hexas.drop;

import java.io.File;
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

	@Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
	public void scanWithSelenium() {
		try {
			System.out.println("Scan with selenium");
			ProcessBuilder builder = new ProcessBuilder("./selenium.sh");
			builder.redirectError(ProcessBuilder.Redirect.INHERIT);
//			builder.directory(new File("/home/shionn/projects/HexasBot"));
			builder.directory(new File("/var/lib/tomcat"));
			builder.start().waitFor();
			System.out.println("fin du scan");
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Scheduled(fixedDelay = 4, timeUnit = TimeUnit.MINUTES)
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

	void scan(Product product) throws IOException {
		System.out.println("scan de " + product);
		PageParser parser = new PageParserRetreiver().resolve(product);
		Map<String, String> cookies = getCookies(parser.getClass().getSimpleName());
		Connection connection = Jsoup
				.connect(product.getUrl())
				.header("User-Agent", USER_AGENT)
				.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
//				.header("Accept-Encoding", "gzip, deflate, br, zstd")
				.header("Accept-Language", "fr,fr-FR;q=0.8,en-US;q=0.5,en;q=0.3")
				.header("Connection", "keep-alive")
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

	private Map<String, String> getCookies(String site) throws IOException {
		Map<String, String> cookies = cookiePerSites.get(site);
		if (cookies == null) {
			cookies = new HashMap<String, String>();
//			Properties props = new Properties();
//			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("cookies.properties"));
//			String value = props.getProperty(site);
//			if (value != null) {
//				for (String prop : value.split(";")) {
//					String[] split = prop.split("=", 2);
//					cookies.put(split[0].trim(), split[1].trim());
//				}
//			}
			cookiePerSites.put(site, cookies);
		}
		return cookies;
	}

}
