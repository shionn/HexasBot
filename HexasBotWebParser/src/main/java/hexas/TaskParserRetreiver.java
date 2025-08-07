package hexas;

import hexas.db.dbo.Task;
import hexas.group.AmazonGroupParser;
import hexas.group.CaseKingGroupParser;
import hexas.group.LdlcGroupParser;
import hexas.price.AmazonPriceParser;
import hexas.price.CaseKingPriceParser;
import hexas.price.LdlcPriceParser;

public class TaskParserRetreiver {

	public TaskParser resolve(Task task) {
		return switch (task.getType()) {
		case ProductGroupScan -> resolvePageParserRetreiver(task);
		case ProductPriceScan -> resolvePriceParserRetreiver(task);
		default -> throw new IllegalArgumentException("unknow type " + task.getType());
		};
		
	}

	private TaskParser resolvePageParserRetreiver(Task task) {
		if (task.getUrl().startsWith("https://www.amazon.fr/")) {
			return new AmazonGroupParser();
		}
		if (task.getUrl().startsWith("https://www.caseking.de")) {
			return new CaseKingGroupParser();
		}
		if (task.getUrl().startsWith("https://www.ldlc.com")) {
			return new LdlcGroupParser();
		}
		throw new IllegalStateException("no parser : " + task.getUrl());
	}

	private TaskParser resolvePriceParserRetreiver(Task task) {
		if (task.getUrl().startsWith("https://www.amazon.fr/")) {
			return new AmazonPriceParser();
		}
		if (task.getUrl().startsWith("https://www.caseking.de")) {
			return new CaseKingPriceParser();
		}
		if (task.getUrl().startsWith("https://www.ldlc.com")) {
			return new LdlcPriceParser();
		}
		throw new IllegalStateException("no parser : " + task.getUrl());
	}

//	public PageParser resolve(String url) {
//		if (url.startsWith("https://www.cybertek.fr")) {
//			return new CyberTeckPageParser();
//		}
//		if (url.startsWith("https://www.pccomponentes.fr")) {
//			return new PcComponentesPageParser();
//		}
//		if (url.startsWith("https://www.caseking.de")) {
//			return new CaseKingPageParser();
//		}
//		if (url.startsWith("https://www.cdiscount.com")) {
//			return new CdiscountPageParser();
//		}
//		if (url.startsWith("https://www.compumsa.eu")) {
//			return new CompumsaPageParser();
//		}
//		if (url.startsWith("https://www.ldlc.com")) {
//			return new LdlcPageParser();
//		}
//		if (url.startsWith("https://marketplace.nvidia.com")) {
//			return new NvidiaPageParser();
//		}
//
//		throw new IllegalStateException("no parser : " + url);
//	}

}
