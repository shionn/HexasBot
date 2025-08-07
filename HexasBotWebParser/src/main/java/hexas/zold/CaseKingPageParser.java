package hexas.zold;

import java.math.BigDecimal;
import java.text.DecimalFormatSymbols;
import java.util.Objects;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import hexas.db.dbo.Product;

public class CaseKingPageParser implements PageParser {

	@Override
	public void parse(Document document, Product product) {
		String stock = document.select(".add-to-cart").stream().map(Element::text).distinct().findAny().orElse(null);
		BigDecimal price = price(document, ".prices .value");
		System.out.println("Found stock " + stock + " price " + price);
		if (stock != null) {
			new PriceUpdater().update(product, price, "CaseKing.de");
		} else {
			new PriceUpdater().update(product, null, "CaseKing.de");
		}
	}

	@Override
	public void parseGroup(Document document, Product group) {
		document.select(".product-grid .product-tiles").forEach(element -> {
			String stock = text(element, ".product-availability");
			BigDecimal price = price(element, ".price .value");
			String url = "https://www.caseking.de" + element
					.select("a")
					.stream()
					.map(e -> e.attr("href"))
					.filter(Objects::nonNull)
					.distinct()
					.findAny()
					.orElse(null);
			String name = text(element, ".product-tile-product-name");
			if ("Out of stock".equalsIgnoreCase(stock)) {
				price = null;
			}
			new PriceUpdater().createOrUpdate(name, url, price, "CaseKing.de", group);
		});
	}

	@Override
	public DecimalFormatSymbols getPriceSymbols() {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator('.');
		symbols.setDecimalSeparator(',');
		symbols.setCurrencySymbol("€");
		return symbols;
	}

}
