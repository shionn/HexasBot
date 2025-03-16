package hexas.parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import hexas.db.dbo.Product;

public class AmazonPageParser implements PageParser {

	@Override
	public void parse(Document doc, Product product) {
		String price = doc
				.select("#corePrice_feature_div span.a-price .a-offscreen")
				.stream()
				.map(Element::text)
				.findAny()
				.orElse(null);
		String vendor = doc
				.select("#merchantInfoFeature_feature_div .offer-display-feature-text-message")
				.stream()
				.map(Element::text)
				.findAny()
				.orElse(null);
		new PriceUpdater().update(product, price, vendor);
	}

}

