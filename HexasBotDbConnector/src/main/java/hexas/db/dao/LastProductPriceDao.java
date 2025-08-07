package hexas.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import hexas.db.dbo.Product;

public interface LastProductPriceDao {

	@Select("SELECT * FROM last_product_price ORDER BY last_price_date, marque, name DESC LIMIT 100")
	@Results({ @Result(column = "product_id", property = "id") })
	List<Product> list();
}
