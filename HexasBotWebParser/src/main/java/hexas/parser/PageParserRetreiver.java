package hexas.parser;

import hexas.db.dbo.Product;

public class PageParserRetreiver {

	public PageParser resolve(Product product) {
		return resolve(product.getUrl());
	}

	public PageParser resolve(String url) {
		if (url.startsWith("https://www.amazon.fr/")) {
			return new AmazonPageParser();
		}
		if (url.startsWith("https://www.cybertek.fr")) {
			return new CyberTeckPageParser();
		}
		if (url.startsWith("https://www.pccomponentes.fr")) {
			return new PcComponentesPageParser();
		}
		if (url.startsWith("https://www.caseking.de")) {
			return new CaseKingPageParser();
		}
		if (url.startsWith("https://www.cdiscount.com")) {
			return new CdiscountPageParser();
		}
		if (url.startsWith("https://www.compumsa.eu")) {
			return new CompumsaPageParser();
		}
		if (url.startsWith("https://www.ldlc.com")) {
			return new LdlcPageParser();
		}
		if (url.startsWith("https://marketplace.nvidia.com")) {
			return new NvidiaPageParser();
		}

		throw new IllegalStateException("no parser : " + url);
	}

}
