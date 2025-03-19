package hexas.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import hexas.db.dbo.Product;

public interface ProductDao {

	@Select("SELECT * FROM product ORDER BY meta_model, marque, model")
	List<Product> listAllDrops();

	@Select("SELECT * FROM product WHERE notify IS true LIMIT 1")
	List<Product> toNotify();

	@Update("UPDATE product SET notify = false WHERE id = #{id}")
	int markNotifyied(Product product);

	@Insert("INSERT INTO product(marque, meta_model, model, url, msrp, notify_channel, scanner) "
			+ "VALUES (#{marque}, #{metaModel}, #{model}, #{url}, #{msrp}, #{notifyChannel}, #{scanner})")
	int create(Product product);

	@Select("SELECT * FROM product WHERE id = #{id}")
	Product read(int id);

	@Update("UPDATE product SET marque = #{marque}, meta_model = #{metaModel}, model = #{model}, "
			+ "url = #{url}, msrp = #{msrp}, notify_channel = #{notifyChannel}, scanner = #{scanner}, last_price = #{lastPrice} "
			+ "WHERE id = #{id}")
	int update(Product product);

}
