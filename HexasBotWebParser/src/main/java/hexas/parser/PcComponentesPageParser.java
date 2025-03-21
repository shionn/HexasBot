package hexas.parser;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import hexas.db.dbo.Product;

public class PcComponentesPageParser implements PageParser {

	@Override
	public void parse(Document doc, Product product) {
		if (doc.text().contains("Vendu et expédié par PcComponentes")) {
			String price = doc
					.select("#pdp-price-current-integer")
					.stream()
					.map(e -> e.text())
					.distinct()
					.findAny()
					.orElse(null);
			String vendor = doc.select("#pdp-section-offered-by span span").text();
			new PriceUpdater().update(product, price, vendor);
		}
	}

	@Override
	public void sleep() throws InterruptedException {
		TimeUnit.SECONDS.sleep(1);
	}

	@Override
	public void parseGroup(Document document, Product group) {
		document.select("#category-list-product-grid > a").forEach(element -> {
			String price = text(element, ".product-card__price-container [data-e2e='price-card']");
			String url = element.attr("href");
			String name = text(element, "H3");
			new PriceUpdater().createOrUpdate(name, url, price, "PcComponentes", group);
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
