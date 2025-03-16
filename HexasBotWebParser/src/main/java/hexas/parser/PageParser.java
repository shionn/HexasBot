package hexas.parser;

import org.jsoup.nodes.Document;

import hexas.db.dbo.Product;

public interface PageParser {

	void parse(Document document, Product product);

}
