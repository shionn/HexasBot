package hexas.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import hexas.db.dbo.Product;

public interface ProductScanDao {

	@Select("SELECT * FROM product")
	List<Product> list();
}
