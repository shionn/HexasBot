package hexas.zold;

import java.math.BigDecimal;
import java.text.DecimalFormatSymbols;

import org.jsoup.nodes.Document;

import hexas.db.dbo.Product;

public class CyberTeckPageParser implements PageParser {

	@Override
	public void parse(Document doc, Product product) {
		String stockLabel = doc.select("#_ctl0_ContentPlaceHolder1_div_dispo_enligne").text();
		BigDecimal price = price(doc, "#_ctl0_ContentPlaceHolder1_l_prix");
		if (!"En Stock".equals(stockLabel)) {
			price = null;
		}
		new PriceUpdater().update(product, price, "CyberTeck");
	}

	@Override
	public void parseGroup(Document document, Product group) {
		// TODO https://www.cybertek.fr/carte-graphique-6.aspx?crits=8737
		PageParser.super.parseGroup(document, group);
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
