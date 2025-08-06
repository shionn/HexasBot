package hexas.price;

import java.math.BigDecimal;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import hexas.TaskParser;
import hexas.creator.ProductPriceCreator;
import hexas.db.dbo.Task;

public class AmazonPriceParser implements TaskParser {
	private static final List<String> VENDORS = Arrays.asList("NR INFO", "Amazon");

	@Override
	public void parse(Document document, Task task) {
		BigDecimal price = price(document, "#corePrice_feature_div span.a-price .a-offscreen");
		String vendor = document
				.select("#merchantInfoFeature_feature_div .offer-display-feature-text-message")
				.stream()
				.map(Element::text)
				.filter(Objects::nonNull)
				.map(String::trim)
				.findAny()
				.orElse("Amazon");
		if (VENDORS.contains(vendor)) {
			new ProductPriceCreator().createIfAbsent(task, price);
		}
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
