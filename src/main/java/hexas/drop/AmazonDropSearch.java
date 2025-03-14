package hexas.drop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AmazonDropSearch implements DropSearch {

	private static final String PREFIX_URL = "https://www.amazon.fr/";
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:128.0) Gecko/20100101 Firefox/128.0";
	private final String marque;
	private final String metaModel;
	private final String model;
	private final String url;

	@Override
	public List<DropResult> search() throws IOException {
		List<DropResult> results = new ArrayList<>();
		Document doc = Jsoup.connect(PREFIX_URL + url).header("User-Agent", USER_AGENT).get();
		DropResult r = DropResult
				.builder()
				.marque(marque)
				.metalModel(metaModel)
				.model(model)
				.url(PREFIX_URL + url)
				.price(doc
						.select("#corePrice_feature_div span.a-price .a-offscreen")
						.stream()
						.map(Element::text)
						.distinct()
						.findAny()
						.orElse(null))
				.vendor(doc
						.select("#merchantInfoFeature_feature_div .offer-display-feature-text-message")
						.stream()
						.map(e -> e.text())
						.distinct()
						.map(e -> e + " (Amazon)")
						.findAny()
						.orElse(null))
				.build();
		if (r.getPrice() != null) {
			results.add(r);
		}
		return results;
	}

}
