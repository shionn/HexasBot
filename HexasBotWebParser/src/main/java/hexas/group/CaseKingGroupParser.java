package hexas.group;

import java.util.Objects;

import org.jsoup.nodes.Document;

import hexas.TaskParser;
import hexas.creator.ParserDbUpdater;
import hexas.db.dbo.Task;

public class CaseKingGroupParser implements TaskParser {

	@Override
	public void parse(Document document, Task task) {
		document.select(".product-grid .product-tiles").forEach(element -> {
			String stock = text(element, ".product-availability");
//			BigDecimal price = price(element, ".price .value");
			String url = "https://www.caseking.de" + element
					.select("a")
					.stream()
					.map(e -> e.attr("href"))
					.filter(Objects::nonNull)
					.distinct()
					.findAny()
					.orElse(null);
//			String name = text(element, ".product-tile-product-name");
			if (!"Out of stock".equalsIgnoreCase(stock)) {
				new ParserDbUpdater().createProductScanTask(task, url);
			}
		});

	}

}
