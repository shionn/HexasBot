package hexas.parser;

import hexas.db.dbo.Product;

public class PageParserRetreiver {

	public PageParser resolve(Product product) {
		if (product.getUrl().startsWith("https://www.amazon.fr/")) {
			return new AmazonPageParser();
		}
		if (product.getUrl().startsWith("https://www.cybertek.fr")) {
			return new CyberTeckPageParser();
		}
		if (product.getUrl().startsWith("https://www.pccomponentes.fr")) {
			return new PcComponentesPageParser();
		}
		if (product.getUrl().startsWith("https://www.caseking.de")) {
			return new CaseKingPageParser();
		}
		throw new IllegalStateException("no parser : " + product.getUrl());
	}

}
