package hexas.parser;

import org.jsoup.nodes.Document;

import hexas.db.dbo.Product;

public class CyberTeckPageParser implements PageParser {

	@Override
	public void parse(Document doc, Product product) {
		String stockLabel = doc.select("#_ctl0_ContentPlaceHolder1_div_dispo_enligne").text();
		if ("En Stock".equals(stockLabel)) {
			String price = doc.select("#_ctl0_ContentPlaceHolder1_l_prix").text();
			new PriceUpdater().update(product, price, "CyberTeck");
		}

	}

}
