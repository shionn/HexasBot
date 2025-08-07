package hexas.drop;

import java.util.Date;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hexas.db.SpringSessionFactory;
import hexas.db.dao.OldProductDao;

@Component
public class DropCleaner {

	@Autowired
	private SpringSessionFactory factory;

//	@Scheduled(cron = "0 0 1 * * *")
	public void cleanOldDrop() {
		try (SqlSession session = factory.open()) {
			System.out.println("Delete old drop");
			session.getMapper(OldProductDao.class).clearOldDrop("%pccomponentes%", new Date());
			session.getMapper(OldProductDao.class).clearOldDrop("%amazon%", new Date());
			session.commit();
		}
	}
	
	
}
