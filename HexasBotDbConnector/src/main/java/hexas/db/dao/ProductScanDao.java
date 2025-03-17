package hexas.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import hexas.db.dbo.Product;

public interface ProductScanDao {

	@Select("SELECT * FROM product WHERE scanner = #{scanner} ORDER BY last_price, last_price_date")
	List<Product> list(String scanner);

	@Update("UPDATE product SET last_price = #{lastPrice}, last_price_date = #{lastPriceDate}, vendor = #{vendor}, notify = true WHERE id = #{id}")
	void update(Product product);
}
