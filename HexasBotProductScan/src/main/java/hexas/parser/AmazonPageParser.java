package hexas.parser;

import java.util.Date;

import org.apache.ibatis.session.SqlSession;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import hexas.db.SessionFactory;
import hexas.db.dao.ProductScanDao;
import hexas.db.dbo.Product;
import javafx.scene.web.WebView;

public class AmazonPageParser implements PageParser {

	@Override
	public void parse(WebView webView, Product product) {
		Document doc = new WebViewToJsoup().build(webView);
		String price = doc
				.select("#corePrice_feature_div span.a-price .a-offscreen")
				.stream()
				.map(Element::text)
				.findAny()
				.orElse(null);
		String vendor = doc
				.select("#merchantInfoFeature_feature_div .offer-display-feature-text-message")
				.stream()
				.map(Element::text)
				.findAny()
				.orElse(null);
		if (shouldUpdate(product, price)) {
			product.setLastPrice(price);
			product.setLastPriceDate(new Date());
			product.setVendor(vendor);
			try (SqlSession session = new SessionFactory().open()) {
				ProductScanDao dao = session.getMapper(ProductScanDao.class);
				dao.update(product);
				session.commit();
			}
		}

	}

	private boolean shouldUpdate(Product product, String price) {
		if (product.getLastPrice() == null) {
			return price != null;
		}
		return price != null && !product.getLastPrice().equals(price);
	}

}

