package hexas.parser;

import java.math.BigDecimal;
import java.text.DecimalFormatSymbols;
import java.util.Objects;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import hexas.db.dbo.Product;

public class CompumsaPageParser implements PageParser {

	@Override
	public void parse(Document document, Product product) {
		String stock = document
				.select("#ContentPlaceHolderMain_LabelStock")
				.stream()
				.map(Element::text)
				.filter(Objects::nonNull)
				.distinct()
				.findAny()
				.orElse(null);
		BigDecimal price = document
				.select("#ContentPlaceHolderMain_LabelPrice")
				.stream()
				.map(Element::text)
				.filter(Objects::nonNull)
				.map(p -> p.replaceAll(" VAT Incl", ""))
				.map(this::parsePrice)
				.distinct()
				.findAny()
				.orElse(null);
		if (!"in stock".equalsIgnoreCase(stock)) {
			price = null;
		}
		new PriceUpdater().update(product, price, "Compumsa");
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
