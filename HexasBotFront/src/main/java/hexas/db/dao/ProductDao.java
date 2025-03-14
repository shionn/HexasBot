package hexas.db.dao;

import java.util.List;

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

}
