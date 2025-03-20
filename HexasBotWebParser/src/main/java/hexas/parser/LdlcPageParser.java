package hexas.parser;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import hexas.db.dbo.Product;

public class LdlcPageParser implements PageParser {

	@Override
	public void parse(Document document, Product product) {
		throw new IllegalStateException("not supported");
	}

	@Override
	public void parseGroup(Document document, Product group) {
		document.select(".listing-product li").forEach(element -> {
			String stock = text(element, "div.stock");
			String price = text(element, "div.price");
			String url = "https://www.ldlc.com" + element
					.select("h3 a")
					.stream()
					.map(e -> e.attr("href"))
					.filter(Objects::nonNull)
					.distinct()
					.findAny()
					.orElse(null);
			String name = text(element, "h3 a");
			if ("rupture".equalsIgnoreCase(stock)) {
				price = null;
			}
			if (name.contains(group.getMetaModel())) {
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

	@Override
	public void sleep() throws InterruptedException {
		TimeUnit.SECONDS.sleep(1);
	}
}
