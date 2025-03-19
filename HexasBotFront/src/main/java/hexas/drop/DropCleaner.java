package hexas.drop;

import java.util.Date;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import hexas.db.SpringSessionFactory;
import hexas.db.dao.ProductDao;

@Component
public class DropCleaner {

	@Autowired
	private SpringSessionFactory factory;

	@Scheduled(cron = "0 0 1 * * *")
	public void cleanOldDrop() {
		try (SqlSession session = factory.open()) {
			System.out.println("Delete old drop");
			session.getMapper(ProductDao.class).clearOldDrop("%pccomponentes%", new Date());
			session.getMapper(ProductDao.class).clearOldDrop("%amazon%", new Date());
			session.commit();
		}
	}
	
	
}
