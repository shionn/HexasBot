package hexas.db.dao;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface TaskPriceDao {

	@Insert("INSERT INTO task_price (task, date, price, notified) " //
			+ "VALUES( #{task}, NOW(), #{price}, FALSE) ")
	int create(@Param("task") int id, @Param("price") BigDecimal price);

}
