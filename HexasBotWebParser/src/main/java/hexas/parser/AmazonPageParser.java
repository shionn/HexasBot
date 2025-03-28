package hexas.parser;

import java.math.BigDecimal;
import java.text.DecimalFormatSymbols;
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
		BigDecimal price = price(doc, "#corePrice_feature_div span.a-price .a-offscreen");
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
			BigDecimal price = price(element, "span.a-price .a-offscreen");
			String url = "https://www.amazon.fr/dp/" + element.attr("data-dib-asin");
			String name = text(element, "h2");
			if (name.contains(group.getMetaModel()) && element.text().contains("Ajouter au panier") && !name.contains("Memory PC")) {
				new PriceUpdater().createOrUpdate(name, url, price, group.getVendor(), group);
			}
		});
	}

	@Override
	public DecimalFormatSymbols getPriceSymbols() {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(' ');
		symbols.setDecimalSeparator(',');
		symbols.setCurrencySymbol("€");
		return symbols;
	}

}

