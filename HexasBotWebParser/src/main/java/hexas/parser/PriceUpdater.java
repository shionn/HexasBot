package hexas.parser;

import java.util.Date;

import org.apache.ibatis.session.SqlSession;

import hexas.db.SessionFactory;
import hexas.db.dao.ProductScanDao;
import hexas.db.dbo.Product;

public class PriceUpdater {

	public void update(Product product, String price, String vendor) {
		System.out.println("Found price " + price + ":" + vendor);
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

	public void createOrUpdate(String name, String url, String price, String vendor, Product group) {
		try (SqlSession session = new SessionFactory().open()) {
			ProductScanDao dao = session.getMapper(ProductScanDao.class);
			Product product = dao.readByUrl(url);
			if (product == null) {
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
						.build();
				dao.create(product);
				session.commit();
			} else if (shouldUpdate(product, price)) {
				product.setLastPrice(price);
				product.setLastPriceDate(new Date());
				product.setVendor(vendor);
				dao.update(product);
				session.commit();
			}
		}

	}

}
