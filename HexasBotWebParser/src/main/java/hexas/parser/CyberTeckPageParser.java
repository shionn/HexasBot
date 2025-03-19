package hexas.parser;

import org.jsoup.nodes.Document;

import hexas.db.dbo.Product;

public class CyberTeckPageParser implements PageParser {

	@Override
	public void parse(Document doc, Product product) {
		String stockLabel = doc.select("#_ctl0_ContentPlaceHolder1_div_dispo_enligne").text();
		String price = doc.select("#_ctl0_ContentPlaceHolder1_l_prix").text();
		if (!"En Stock".equals(stockLabel)) {
			price = null;
		}
		new PriceUpdater().update(product, price, "CyberTeck");

	}

}
