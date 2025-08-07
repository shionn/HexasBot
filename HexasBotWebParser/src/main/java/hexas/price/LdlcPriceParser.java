package hexas.price;

import java.math.BigDecimal;
import java.text.DecimalFormatSymbols;

import org.jsoup.nodes.Document;

import hexas.TaskParser;
import hexas.creator.ParserDbUpdater;
import hexas.db.dbo.Task;

public class LdlcPriceParser implements TaskParser {

	@Override
	public void parse(Document document, Task task) {
		BigDecimal price = price(document, ".product-price .price .price");
		String addToCard = text(document, ".add-to-cart-bloc");
		if (addToCard != null && addToCard.contains("Ajouter au panier")) {
			new ParserDbUpdater().insertProductPrice(task, price);
		}
	}

	@Override
	public DecimalFormatSymbols getPriceSymbols() {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(' ');
		symbols.setDecimalSeparator('€');
		symbols.setCurrencySymbol("€");
		return symbols;
	}

}
