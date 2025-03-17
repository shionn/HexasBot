package hexas.product.finder;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class CaseKingProductFinder {
	private static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64; rv:136.0) Gecko/20100101 Firefox/136.0";

	public static void main(String[] args) throws IOException {
		Document doc = Jsoup
				.connect(
						"https://www.caseking.de/en/asrock-radeon-rx-9070-xt-steel-legend-grafikkarte-16384-mb-gddr6/GCAR-031.html")
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
				.cookies(new HashMap<String, String>())
				.get();
		List<String> stock = doc.select(".add-to-cart").stream().map(Element::text).distinct().toList();
		List<String> price = doc.select(".prices .value").stream().map(Element::text).distinct().toList();
		System.out.println(stock + " " + price);
	}

//	public static void main(String[] args) throws IOException {
//		Document document = Jsoup
//				.connect("https://www.caseking.de/en/pc-components/graphics-cards/amd/rx-9000/radeon-rx-9070-xt")
//				.header("User-Agent", USER_AGENT)
//				.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
//				.header("DNT", "1")
//				.header("Priority", "u=0, i")
//				.header("Sec-Fetch-Dest", "document")
//				.header("Sec-Fetch-Mode", "navigate")
//				.header("Sec-Fetch-Site", "none")
//				.header("Sec-Fetch-User", "?1")
//				.header("TE", "trailers")
//				.header("Upgrade-Insecure-Requests", "1")
//				.get();
//		System.out.println(document);
//		document.select(".product-grid .product-tiles").forEach(p -> {
//			p
//					.select("a")
//					.stream()
//					.map(a -> a.attr("href"))
//					.distinct()
//					.map(u -> "https://www.caseking.de" + u)
//					.forEach(System.out::println);
//		});
//	}
}
