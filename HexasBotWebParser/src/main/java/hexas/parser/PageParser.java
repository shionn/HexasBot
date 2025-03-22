package hexas.parser;

import java.util.concurrent.TimeUnit;

import org.jsoup.nodes.Document;

import hexas.db.dbo.Product;

public interface PageParser {

	void parse(Document document, Product product);

	default void parseGroup(Document document, Product group) {
		throw new IllegalStateException("group not supported");
	}

	default void sleep() throws InterruptedException {
		TimeUnit.SECONDS.sleep(1);
	};

}
