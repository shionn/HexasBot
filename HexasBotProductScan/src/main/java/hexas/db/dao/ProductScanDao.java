package hexas.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import hexas.db.dbo.Product;

public interface ProductScanDao {

	@Select("SELECT * FROM product")
	List<Product> list();

	@Update("UPDATE product SET last_price = #{lastPrice}, last_price_date = #{lastPriceDate}, vendor = #{vendor} WHERE id = #{id}")
	void update(Product product);
}
