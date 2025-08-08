package hexas.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import hexas.db.dbo.Product;

public interface LastProductPriceDao {

	@Select("SELECT * FROM last_product_price ORDER BY date_sort DESC, marque, name LIMIT 100")
	@Results({ @Result(column = "product_id", property = "id") })
	List<Product> list();

	@Select("SELECT * FROM product GROUP BY id ORDER BY marque, meta_model, name ")
	@Results({ //
			@Result(column = "id", property = "id"), //
			@Result(column = "id", property = "prices", many = @Many(select = "listPrices")), //
	})
	List<Product> listByProduct();

	@Select("SELECT * FROM last_product_price WHERE product_id = #{id} ORDER BY last_price ASC LIMIT 5")
	List<Product> listPrices(int id);

}
