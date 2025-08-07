package hexas.price;

import java.math.BigDecimal;
import java.text.DecimalFormatSymbols;

import org.jsoup.nodes.Document;

import hexas.TaskParser;
import hexas.creator.ParserDbUpdater;
import hexas.db.dbo.Task;

public class CdiscountPriceParser implements TaskParser {

	@Override
	public void parse(Document document, Task task) {
		BigDecimal price = price(document, ".c-buybox__price .u-visually-hidden");
//		String vendor = document
//				.select(".c-sellerBy a")
//				.stream()
//				.map(Element::text)
//				.filter(Objects::nonNull)
//				.distinct()
//				.findAny()
//				.orElse(null);

		new ParserDbUpdater().insertProductPrice(task, price);
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
