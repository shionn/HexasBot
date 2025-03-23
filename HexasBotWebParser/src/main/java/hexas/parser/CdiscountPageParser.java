package hexas.parser;

import java.math.BigDecimal;
import java.text.DecimalFormatSymbols;
import java.util.Objects;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import hexas.db.dbo.Product;

public class CdiscountPageParser implements PageParser {

	@Override
	public void parse(Document document, Product product) {
		BigDecimal price = price(document, ".c-buybox__price .u-visually-hidden");
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

	@Override
	public DecimalFormatSymbols getPriceSymbols() {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(' ');
		symbols.setDecimalSeparator(',');
		symbols.setCurrencySymbol("€");
		return symbols;
	}
}
