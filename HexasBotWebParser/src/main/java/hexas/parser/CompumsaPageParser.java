package hexas.parser;

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
		String price = document
				.select("#ContentPlaceHolderMain_LabelPrice")
				.stream()
				.map(Element::text)
				.filter(Objects::nonNull)
				.map(p -> p.replaceAll(" VAT Incl", ""))
				.distinct()
				.findAny()
				.orElse(null);
		if ("in stock".equalsIgnoreCase(stock)) {
			new PriceUpdater().update(product, price, "Compumsa");
		}
	}

}
