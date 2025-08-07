package hexas.creator;

import java.math.BigDecimal;

import org.apache.ibatis.session.SqlSession;

import hexas.db.SessionFactory;
import hexas.db.dao.TaskPriceDao;
import hexas.db.dbo.Task;

public class ProductPriceCreator {

	public void createIfAbsent(Task task, BigDecimal price) {
		if (task.getLastPrice() == null || task.getLastPrice().compareTo(price) != 0) {
			try (SqlSession session = new SessionFactory().open()) {
				session.getMapper(TaskPriceDao.class).create(task.getId(), price);
				session.commit();
			}
		} else {
			System.out.println("price (" + price + ") already up to date " + task.getUrl());
		}
	}

}
