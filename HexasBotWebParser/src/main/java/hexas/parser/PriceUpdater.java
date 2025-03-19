package hexas.parser;

import java.util.Date;

import org.apache.ibatis.session.SqlSession;

import hexas.db.SessionFactory;
import hexas.db.dao.ProductScanDao;
import hexas.db.dbo.Product;

public class PriceUpdater {

	public void update(Product product, String price, String vendor) {
		System.out.println("Found price " + price + ":" + vendor);
		product.setLastPrice(price);
		product.setLastPriceDate(new Date());
		product.setVendor(vendor);
		product.setNotify(shouldNotify(product, price));
		try (SqlSession session = new SessionFactory().open()) {
			ProductScanDao dao = session.getMapper(ProductScanDao.class);
			dao.update(product);
			session.commit();
		}
	}

	private boolean shouldNotify(Product product, String price) {
		if (price == null) {
			return false;
		}
		if (product.getLastPrice() == null) {
			return true;
		}
		return !product.getLastPrice().equals(price);
	}

	public void createOrUpdate(String name, String url, String price, String vendor, Product group) {
		try (SqlSession session = new SessionFactory().open()) {
			ProductScanDao dao = session.getMapper(ProductScanDao.class);
			Product product = dao.readByUrl(url);
			if (product == null && price != null) {
				product = Product
						.builder()
						.marque("todo")
						.model(name)
						.lastPrice(price)
						.lastPriceDate(new Date())
						.metaModel(group.getMetaModel())
						.msrp(group.getMsrp())
						.notifyChannel(group.getNotifyChannel())
						.url(url)
						.vendor(vendor)
						.scanner("group-" + group.getId())
						.notify(price != null)
						.build();
				dao.create(product);
				session.commit();
			} else if (product != null) {
				product.setNotify(shouldNotify(product, price));
				product.setLastPrice(price);
				product.setLastPriceDate(new Date());
				product.setVendor(vendor);
				dao.update(product);
				session.commit();
			}
		}

	}

}
