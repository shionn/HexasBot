package hexas.parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import hexas.db.dbo.Product;

public class CaseKingPageParser implements PageParser {

	@Override
	public void parse(Document document, Product product) {
		String stock = document.select(".add-to-cart").stream().map(Element::text).distinct().findAny().orElse(null);
		String price = document.select(".prices .value").stream().map(Element::text).distinct().findAny().orElse(null);
		if (stock != null) {
			new PriceUpdater().update(product, price, "CaseKing.de");
		}
	}

}
