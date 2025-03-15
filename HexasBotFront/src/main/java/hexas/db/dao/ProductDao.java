package hexas.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import hexas.db.dbo.Product;

public interface ProductDao {

	@Select("SELECT * FROM product ORDER BY meta_model, marque, model")
	List<Product> listAllDrops();

}
