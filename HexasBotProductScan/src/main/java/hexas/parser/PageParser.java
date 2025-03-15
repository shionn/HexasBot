package hexas.parser;

import hexas.db.dbo.Product;
import javafx.scene.web.WebView;

public interface PageParser {

	void parse(WebView webView, Product product);

}
