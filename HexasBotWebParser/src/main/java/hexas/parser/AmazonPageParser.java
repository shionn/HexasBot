package hexas.parser;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import hexas.db.dbo.Product;

public class AmazonPageParser implements PageParser {

	private static final List<String> VENDORS = Arrays.asList("NR INFO", "Amazon");

	@Override
	public void parse(Document doc, Product product) {
		String price = doc
				.select("#corePrice_feature_div span.a-price .a-offscreen")
				.stream()
				.map(Element::text)
				.filter(Objects::nonNull)
				.findAny()
				.orElse(null);
		String vendor = doc
				.select("#merchantInfoFeature_feature_div .offer-display-feature-text-message")
				.stream()
				.map(Element::text)
				.filter(Objects::nonNull)
				.map(String::trim)
				.findAny()
				.orElse("Amazon");
		if (VENDORS.contains(vendor)) {
			new PriceUpdater().update(product, price, vendor);
		}
	}

	@Override
	public void parseGroup(Document document, Product group) {
		document.select(".s-search-results .puis-card-container").forEach(element -> {
			String price = text(element, "span.a-price .a-offscreen");
			String url = "https://www.amazon.fr/dp/" + element.attr("data-dib-asin");
			String name = text(element, "h2");
			if (name.contains(group.getMetaModel())) {
				new PriceUpdater().createOrUpdate(name, url, price, group.getVendor(), group);
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

