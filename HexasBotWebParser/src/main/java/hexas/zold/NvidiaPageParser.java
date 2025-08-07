package hexas.zold;

import java.math.BigDecimal;
import java.text.DecimalFormatSymbols;
import java.util.Objects;
import java.util.Optional;

import org.jsoup.nodes.Document;

import hexas.db.dbo.Product;

public class NvidiaPageParser implements PageParser {

	@Override
	public void parse(Document document, Product product) {
		throw new IllegalStateException("not supported");
	}

	@Override
	public void parseGroup(Document document, Product group) {
		document.select("#resultsDiv .droplink").forEach(element -> {
			String stock = text(element, "a span");
			BigDecimal price = Optional
					.ofNullable(text(element, "div.price"))
					.map(t -> t.replace("€", " "))
					.map(this::parsePrice)
					.orElse(null);
			String url = element
					.select("a")
					.stream()
					.map(e -> e.attr("href"))
					.filter(Objects::nonNull)
					.distinct()
					.findAny()
					.orElse(null);
			String name = text(element, "h2");
			if (!"acheter maintenant".equalsIgnoreCase(stock)) {
				price = null;
			}
			if (name.contains("RTX 50") && !"javascript:void(0);".equalsIgnoreCase(url)) {
				new PriceUpdater().createOrUpdate(name, url, price, "Nvidia", group);
			}
		});
	}

	@Override
	public DecimalFormatSymbols getPriceSymbols() {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		symbols.setCurrencySymbol("€");
		return symbols;
	}

}
