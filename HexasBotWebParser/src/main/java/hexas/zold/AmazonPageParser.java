package hexas.zold;

import java.math.BigDecimal;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
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
			String name = text(element, "h2");
			String id = element.attr("data-dib-asin");
			if (isValidProduct(group, name) && isInStock(element) && !isIgnored(group, name)
					&& StringUtils.isNotBlank(id)) {
				String url = "https://www.amazon.fr/dp/" + id;
				new PriceUpdater().createOrUpdate(name, url, price, group.getVendor(), group);
			}
		});
	}

	private boolean isIgnored(Product group, String name) {
		boolean ignored = false;
		if (StringUtils.isNotBlank(group.getExcludePattern())) {
			ignored = Pattern.compile(group.getExcludePattern()).matcher(name).find();
		}
		return ignored || name.contains("Memory PC") || name.contains("VIBOX");
	}

	private boolean isValidProduct(Product group, String name) {
		if (StringUtils.isNotBlank(group.getIncludePattern())) {
			return Pattern.compile(group.getIncludePattern()).matcher(name).find();
		}
		return name.contains(group.getMetaModel());
	}

	private boolean isInStock(Element element) {
		return element.text().contains("Ajouter au panier");
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

