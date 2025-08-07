package hexas.zold;

import java.math.BigDecimal;
import java.text.DecimalFormatSymbols;
import java.util.concurrent.TimeUnit;

import org.jsoup.nodes.Document;

import hexas.db.dbo.Product;

public class PcComponentesPageParser implements PageParser {

	@Override
	public void parse(Document doc, Product product) {
		if (doc.text().contains("Vendu et expédié par PcComponentes")) {
			BigDecimal price = price(doc, "#pdp-price-current-integer");
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
			BigDecimal price = price(element, ".product-card__price-container [data-e2e='price-card']");
			String url = element.attr("href");
			String name = text(element, "H3");
			new PriceUpdater().createOrUpdate(name, url, price, "PcComponentes", group);
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
