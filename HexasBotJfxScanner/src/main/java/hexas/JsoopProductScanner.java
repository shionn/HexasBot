package hexas;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import hexas.db.SessionFactory;
import hexas.db.dao.ProductScanDao;
import hexas.db.dbo.Product;
import hexas.parser.PageParserRetreiver;

public class JsoopProductScanner {
	private static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64; rv:136.0) Gecko/20100101 Firefox/136.0";

	public static void main(String[] args) {
		new JsoopProductScanner().scan();
	}

	private void scan() {
		try (SqlSession session = new SessionFactory().open()) {
			List<Product> products = session.getMapper(ProductScanDao.class).list("jsoop");
			for (Product product : products) {
				try {
//					System.out.println("Scan " + product);
					this.scan(product);
				} catch (IOException e) {
					e.printStackTrace();

				}
			}
		}

	}

	private void scan(Product product) throws IOException {
		Document document = Jsoup
				.connect(product.getUrl())
				.header("User-Agent", USER_AGENT)
				.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
				.header("DNT", "1")
				.header("Priority", "u=0, i")
				.header("Sec-Fetch-Dest", "document")
				.header("Sec-Fetch-Mode", "navigate")
				.header("Sec-Fetch-Site", "none")
				.header("Sec-Fetch-User", "?1")
				.header("TE", "trailers")
				.header("Upgrade-Insecure-Requests", "1")
				.get();
		new PageParserRetreiver().resolve(product).parse(document, product);
	}
}
