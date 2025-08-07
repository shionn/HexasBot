package hexas.price;

import java.math.BigDecimal;
import java.text.DecimalFormatSymbols;

import org.jsoup.nodes.Document;

import hexas.TaskParser;
import hexas.creator.ParserDbUpdater;
import hexas.db.dbo.Task;

public class PcComponentesPriceParser implements TaskParser {

	@Override
	public void parse(Document document, Task task) {
		if (document.text().contains("Vendu et expédié par PcComponentes")) {
			BigDecimal price = price(document, "#pdp-price-current-integer");
//			String vendor = document.select("#pdp-section-offered-by span span").text();
			new ParserDbUpdater().insertProductPrice(task, price);
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
