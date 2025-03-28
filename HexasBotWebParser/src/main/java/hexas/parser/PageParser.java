package hexas.parser;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import hexas.db.dbo.Product;

public interface PageParser {

	void parse(Document document, Product product);

	default void parseGroup(Document document, Product group) {
		throw new IllegalStateException("group not supported");
	}

	default void sleep() throws InterruptedException {
		TimeUnit.SECONDS.sleep(1);
	};

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

	default BigDecimal price(Element element, String selector) {
		return Optional.ofNullable(text(element, selector)).map(String::trim).map(this::parsePrice).orElse(null);
	}

	default BigDecimal parsePrice(String text) {
		try {
			return BigDecimal.valueOf(new DecimalFormat("", getPriceSymbols()).parse(text).doubleValue());
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	DecimalFormatSymbols getPriceSymbols();

}
