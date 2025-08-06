package hexas.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import hexas.db.dbo.Product;

@Deprecated
public interface ProductScanDao {

	@Select("SELECT * FROM product WHERE scanner = #{scanner} ORDER BY last_price, last_price_date")
	List<Product> list(String scanner);

	@Update("UPDATE product SET last_price = #{lastPrice}, last_price_date = #{lastPriceDate}, vendor = #{vendor}, notify = #{notify} WHERE id = #{id}")
	void update(Product product);

	@Select("SELECT * FROM product WHERE url = #{url}")
	Product readByUrl(String url);

	@Insert("INSERT INTO product(marque, meta_model, model, url, msrp, notify_channel, scanner, last_price, last_price_date, vendor, notify, notify_price) "
			+ "VALUES (#{marque}, #{metaModel}, #{model}, #{url}, #{msrp}, #{notifyChannel}, #{scanner}, #{lastPrice}, #{lastPriceDate}, #{vendor}, #{notify}, #{notifyPrice})")
	int create(Product product);
}
