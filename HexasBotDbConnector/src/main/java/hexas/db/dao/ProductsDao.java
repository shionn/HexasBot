package hexas.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import hexas.db.dbo.Product;

public interface ProductsDao {

	@Select("SELECT * FROM product ORDER BY marque, meta_model, name")
	List<Product> listProducts();

	@Select("SELECT * FROM product WHERE id = #{id}")
	Product read(int id);

	@Update("UPDATE product SET marque = #{marque}, meta_model = #{metaModel}, name = #{name}, msrp = #{msrp}, "
			+ "notify_price = #{notifyPrice}, notify_channel = #{notifyChannel} "
			+ "WHERE id = #{id}")
	int update(Product product);

	@Update("INSERT product (marque, meta_model, name, msrp, notify_price, notify_channel) "
			+ "VALUES (#{marque}, #{name}, #{meta_model}, #{msrp}, #{notifyPrice}, #{notifyChannel})")
	int create(Product product);

	@Delete("DELETE FROM product WHERE id = #{id}")
	int remove(int id);
}
