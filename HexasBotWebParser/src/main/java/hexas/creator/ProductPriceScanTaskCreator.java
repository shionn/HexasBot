package hexas.creator;

import org.apache.ibatis.session.SqlSession;

import hexas.db.SessionFactory;
import hexas.db.dao.TasksDao;
import hexas.db.dbo.Task;
import hexas.db.dbo.TaskType;

public class ProductPriceScanTaskCreator {

	public void createIfAbsent(Task parent, String url) {
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

}
