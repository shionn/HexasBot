package hexas.parser;

import org.jsoup.nodes.Document;

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

}
