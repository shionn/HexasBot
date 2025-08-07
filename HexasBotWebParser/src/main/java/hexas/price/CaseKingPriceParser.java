package hexas.price;

import java.math.BigDecimal;
import java.text.DecimalFormatSymbols;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import hexas.TaskParser;
import hexas.creator.ProductPriceCreator;
import hexas.db.dbo.Task;

public class CaseKingPriceParser implements TaskParser {

	@Override
	public void parse(Document document, Task task) {
		String stock = document.select(".add-to-cart").stream().map(Element::text).distinct().findAny().orElse(null);
		BigDecimal price = price(document, ".prices .value");
		System.out.println("Found stock " + stock + " price " + price);
		if (stock != null) {
			new ProductPriceCreator().createIfAbsent(task, price);
		} else {
			// TODO
		}
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
