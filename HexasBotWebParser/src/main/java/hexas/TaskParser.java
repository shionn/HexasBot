package hexas;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import hexas.db.dbo.Task;

public interface TaskParser {

	void parse(Document document, Task task);

	default void sleep() throws InterruptedException {
		TimeUnit.SECONDS.sleep(1);
	};

	default BigDecimal price(Element element, String selector) {
		return Optional.ofNullable(text(element, selector)).map(String::trim).map(this::parsePrice).orElse(null);
	}

	default String text(Element element, String selector) {
		return element
				.select(selector)
				.stream()
				.map(Element::text)
				.filter(Objects::nonNull)
				.distinct()
				.findAny()
				.orElse(null);
	}

	default BigDecimal parsePrice(String text) {
		try {
			return BigDecimal.valueOf(new DecimalFormat("", getPriceSymbols()).parse(text).doubleValue());
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	default DecimalFormatSymbols getPriceSymbols() {
		throw new IllegalStateException("not implemented");
	}

}
