package hexas.drop;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AmazonDropSearch implements DropSearch {

	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:128.0) Gecko/20100101 Firefox/128.0";
	@Getter
	private final String model;
	@Getter
	private final String url;
	@Getter
	private String price;
	@Getter
	private String vendor;

	@Override
	public void search() throws IOException {
		Document doc = Jsoup.connect(url).header("User-Agent", USER_AGENT).get();
		doc
				.select("#corePrice_feature_div span.a-price .a-offscreen")
				.stream()
				.map(Element::text)
				.distinct()
				.findAny()
				.ifPresent(t -> price = t);
		doc
				.select("#merchantInfoFeature_feature_div .offer-display-feature-text-message")
				.stream()
				.map(e -> e.text())
				.distinct()
				.findAny()
				.ifPresent(t -> vendor = t);
	}


}
