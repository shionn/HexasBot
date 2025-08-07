package hexas.group;

import org.jsoup.nodes.Document;

import hexas.TaskParser;
import hexas.creator.ParserDbUpdater;
import hexas.db.dbo.Task;

public class PcComponentesGroupParser implements TaskParser {

	@Override
	public void parse(Document document, Task task) {
		document.select("#category-list-product-grid > a").forEach(element -> {
//			BigDecimal price = price(element, ".product-card__price-container [data-e2e='price-card']");
			String url = element.attr("href");
//			String name = text(element, "H3");
			new ParserDbUpdater().createProductScanTask(task, url);
		});
	}

}
