package hexas.parser;

import java.util.Objects;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import hexas.db.dbo.Product;

public class CdiscountPageParser implements PageParser {

	@Override
	public void parse(Document document, Product product) {
		String price = document
				.select(".c-buybox__price .u-visually-hidden")
				.stream()
				.map(Element::text)
				.filter(Objects::nonNull)
				.distinct()
				.findAny()
				.orElse(null);
		String vendor = document
				.select(".c-sellerBy a")
				.stream()
				.map(Element::text)
				.filter(Objects::nonNull)
				.distinct()
				.findAny()
				.orElse(null);

		new PriceUpdater().update(product, price, vendor);
	}
}
