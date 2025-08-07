package hexas.creator;

import java.math.BigDecimal;

import org.apache.ibatis.session.SqlSession;

import hexas.db.SessionFactory;
import hexas.db.dao.TaskPriceDao;
import hexas.db.dao.TasksDao;
import hexas.db.dbo.Task;
import hexas.db.dbo.TaskType;

public class ParserDbUpdater {

	public void createProductScanTask(Task parent, String url) {
		Task task = Task
				.builder()
				.parent(parent)
				.type(TaskType.ProductPriceScan)
				.url(url)
				.product(parent.getProduct())
				.build();
		try (SqlSession session = new SessionFactory().open()) {
			TasksDao dao = session.getMapper(TasksDao.class);
			if (dao.contains(task)) {
				System.out.println("task already exists on " + url);
			} else {
				dao.create(task);
				session.commit();
			}
		}
	}

	public void insertProductPrice(Task task, BigDecimal price) {
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
