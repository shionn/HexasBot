package hexas.group;

import java.util.Objects;

import org.jsoup.nodes.Document;

import hexas.TaskParser;
import hexas.creator.ProductPriceScanTaskCreator;
import hexas.db.dbo.Task;

public class LdlcGroupParser implements TaskParser {

	@Override
	public void parse(Document document, Task task) {
		document.select(".listing-product li").forEach(element -> {
			String stock = text(element, "div.stock");
//			BigDecimal price = price(element, "div.price");
			String url = "https://www.ldlc.com" + element
					.select("h3 a")
					.stream()
					.map(e -> e.attr("href"))
					.filter(Objects::nonNull)
					.distinct()
					.findAny()
					.orElse(null);
//			String name = text(element, "h3 a");
			if (isInStock(stock)) {
				new ProductPriceScanTaskCreator().createIfAbsent(task, url);
			}
		});

	}

	private boolean isInStock(String stock) {
		return !"rupture".equalsIgnoreCase(stock);
	}

}
