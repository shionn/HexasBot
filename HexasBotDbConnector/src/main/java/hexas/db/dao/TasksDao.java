package hexas.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import hexas.db.dbo.Task;

public interface TasksDao {

	@Insert("INSERT INTO task (type, url, product, include_pattern, exclude_pattern, parent) "
			+ "VALUES (#{type}, #{url}, #{product.id}, #{includePattern}, #{excludePattern}, #{parent.id}) ")
	int create(Task task);

	@Select("SELECT t.id, t.type, t.url, t.include_pattern, t.exclude_pattern, " //
			+ "p.id AS product_id, p.marque, p.name " //
			+ "FROM task AS t " //
			+ "LEFT JOIN product AS p ON t.product = p.id " //
			+ "WHERE t.type <> 'Disable' " //
			+ "ORDER BY type, marque, name")
	@Results({ //
			@Result(column = "product_id", property = "product.id"),
			@Result(column = "marque", property = "product.marque"),
			@Result(column = "name", property = "product.name") //
	})
	List<Task> list();

	@Select("SELECT t.id, t.type, t.url, t.include_pattern, t.exclude_pattern, " //
			+ "p.id AS product_id, p.marque, p.name " //
			+ "FROM task AS t " //
			+ "LEFT JOIN product AS p ON t.product = p.id " //
			+ "WHERE t.id = #{id}")
	@Results({ //
			@Result(column = "product_id", property = "product.id"),
			@Result(column = "marque", property = "product.marque"),
			@Result(column = "name", property = "product.name") //
	})
	Task read(int id);

	@Insert("UPDATE task SET " //
			+ "type = #{type}, url = #{url}, product = #{product.id}, " //
			+ "include_pattern = #{includePattern}, exclude_pattern = #{excludePattern} "
			+ "WHERE id = #{id}")
	int update(Task task);

	@Select("SELECT EXISTS(SELECT * FROM task WHERE type = #{type} AND url = #{url})")
	boolean contains(Task task);

	@Select("SELECT t.id, t.type, t.url, t.include_pattern, t.exclude_pattern, " //
			+ "p.id AS product_id, p.marque, p.name, "
			+ "ltp.price AS last_price, ltp.date AS last_price_date " //
			+ "FROM task AS t " //
			+ "LEFT JOIN product AS p ON t.product = p.id " //
			+ "LEFT JOIN last_task_price AS ltp ON ltp.task = t.id " //
			+ "WHERE t.type <> 'Disable' " //
			+ "ORDER BY type")
	@Results({ //
			@Result(column = "product_id", property = "product.id"), //
			@Result(column = "marque", property = "product.marque"), //
			@Result(column = "name", property = "product.name") //
	})
	List<Task> listActiveTask();

}
