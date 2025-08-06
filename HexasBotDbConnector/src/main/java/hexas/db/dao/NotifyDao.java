package hexas.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import hexas.db.dbo.Notification;

public interface NotifyDao {

	@Select("SELECT * FROM notification")
	List<Notification> list();

	@Update("UPDATE task_price SET notified = TRUE WHERE task = #{task} AND date = #{date}")
	void markNotifyied(Notification notification);
	
}
