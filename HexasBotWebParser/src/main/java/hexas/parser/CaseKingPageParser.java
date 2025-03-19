package hexas.parser;

import java.util.Objects;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import hexas.db.dbo.Product;

public class CaseKingPageParser implements PageParser {

	@Override
	public void parse(Document document, Product product) {
		String stock = document.select(".add-to-cart").stream().map(Element::text).distinct().findAny().orElse(null);
		String price = document.select(".prices .value").stream().map(Element::text).distinct().findAny().orElse(null);
		System.out.println("Found stock " + stock + " price " + price);
		if (stock != null) {
			new PriceUpdater().update(product, price, "CaseKing.de");
//		} else {
//			System.out.println(document);
		}
	}

	@Override
	public void parseGroup(Document document, Product group) {
		document.select(".product-grid .product-tiles").forEach(element -> {
			String stock = text(element, ".product-availability");
			String price = text(element, ".price .value");
			String url = "https://www.caseking.de" + element
					.select("a")
					.stream()
					.map(e -> e.attr("href"))
					.filter(Objects::nonNull)
					.distinct()
					.findAny()
					.orElse(null);
			String name = text(element, ".product-tile-product-name");
			if (!"Out of stock".equalsIgnoreCase(stock)) {
				new PriceUpdater().createOrUpdate(name, url, price, "CaseKing.de", group);
			}
		});
	}

	private String text(Element element, String selector) {
		return element
				.select(selector)
				.stream()
				.map(Element::text)
				.filter(Objects::nonNull)
				.distinct()
				.findAny()
				.orElse(null);
	}

}
