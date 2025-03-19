package hexas.parser;

import java.util.Objects;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import hexas.db.dbo.Product;

public class NvidiaPageParser implements PageParser {

	@Override
	public void parse(Document document, Product product) {
		throw new IllegalStateException("not supported");
	}

	@Override
	public void parseGroup(Document document, Product group) {
		document.select("#resultsDiv .droplink").forEach(element -> {
			String stock = text(element, "a span");
			String price = text(element, "div.price");
			String url = element
					.select("a")
					.stream()
					.map(e -> e.attr("href"))
					.filter(Objects::nonNull)
					.distinct()
					.findAny()
					.orElse(null);
			String name = text(element, "h2");
			if ("acheter maintenant".equalsIgnoreCase(stock) && name.contains("RTX 50")) {
				new PriceUpdater().createOrUpdate(name, url, price, "LDLC", group);
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
