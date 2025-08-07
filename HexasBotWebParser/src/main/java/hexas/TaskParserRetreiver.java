package hexas;

import hexas.db.dbo.Task;
import hexas.group.AmazonGroupParser;
import hexas.group.CaseKingGroupParser;
import hexas.group.LdlcGroupParser;
import hexas.group.PcComponentesGroupParser;
import hexas.price.AmazonPriceParser;
import hexas.price.CaseKingPriceParser;
import hexas.price.CdiscountPriceParser;
import hexas.price.LdlcPriceParser;
import hexas.price.PcComponentesPriceParser;

public class TaskParserRetreiver {

	public TaskParser resolve(Task task) {
		return switch (task.getType()) {
		case ProductGroupScan -> resolveGroupParserRetreiver(task);
		case ProductPriceScan -> resolvePriceParserRetreiver(task);
		default -> throw new IllegalArgumentException("unknow type " + task.getType());
		};
		
	}

	private TaskParser resolveGroupParserRetreiver(Task task) {
		if (task.getUrl().startsWith("https://www.amazon.fr/")) {
			return new AmazonGroupParser();
		}
		if (task.getUrl().startsWith("https://www.caseking.de")) {
			return new CaseKingGroupParser();
		}
		if (task.getUrl().startsWith("https://www.ldlc.com")) {
			return new LdlcGroupParser();
		}
		if (task.getUrl().startsWith("https://www.pccomponentes.fr")) {
			return new PcComponentesGroupParser();
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
		if (task.getUrl().startsWith("https://www.cdiscount.com")) {
			return new CdiscountPriceParser();
		}
		if (task.getUrl().startsWith("https://www.ldlc.com")) {
			return new LdlcPriceParser();
		}
		if (task.getUrl().startsWith("https://www.pccomponentes.com")) {
			return new PcComponentesPriceParser();
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
//		if (url.startsWith("https://www.compumsa.eu")) {
//			return new CompumsaPageParser();
//		}
//		if (url.startsWith("https://marketplace.nvidia.com")) {
//			return new NvidiaPageParser();
//		}
//
//		throw new IllegalStateException("no parser : " + url);
//	}

}
