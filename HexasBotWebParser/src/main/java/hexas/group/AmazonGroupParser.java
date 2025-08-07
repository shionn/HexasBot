package hexas.group;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import hexas.TaskParser;
import hexas.creator.ParserDbUpdater;
import hexas.db.dbo.Task;

public class AmazonGroupParser implements TaskParser {

	@Override
	public void parse(Document document, Task task) {
		document.select("[data-component-type='s-search-result']").forEach(element -> {
//			BigDecimal price = price(element, "span.a-price .a-offscreen");
			String name = text(element, "h2");
			String id = element.attr("data-asin");
			if (isValidProduct(task, name) && isInStock(element) && !isIgnored(task, name)
					&& StringUtils.isNotBlank(id)) {
				String url = "https://www.amazon.fr/dp/" + id;
				new ParserDbUpdater().createProductScanTask(task, url);
			}
		});
	}

	private boolean isValidProduct(Task task, String name) {
		if (StringUtils.isNotBlank(task.getIncludePattern())) {
			return Pattern.compile(task.getIncludePattern()).matcher(name).find();
		}
		return true;
	}

	private boolean isIgnored(Task task, String name) {
		boolean ignored = false;
		if (StringUtils.isNotBlank(task.getExcludePattern())) {
			ignored = Pattern.compile(task.getExcludePattern()).matcher(name).find();
		}
		return ignored;
	}

	private boolean isInStock(Element element) {
		return element.text().contains("Ajouter au panier");
	}


}
