package hexas.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import hexas.db.dbo.ProductPriceNotification;

public interface ProductPriceNotifyDao {

	@Select("SELECT * FROM notification")
	List<ProductPriceNotification> list();

	@Update("UPDATE task_price SET notified = TRUE WHERE task = #{task} AND date = #{date}")
	void markNotifyied(ProductPriceNotification notification);
	
}
