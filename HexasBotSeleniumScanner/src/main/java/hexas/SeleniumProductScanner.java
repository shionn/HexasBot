package hexas;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.jsoup.Jsoup;
import org.openqa.selenium.firefox.FirefoxDriver;

import hexas.db.SessionFactory;
import hexas.db.dao.TasksDao;
import hexas.db.dbo.Task;

public class SeleniumProductScanner {

	public void doParsing() {
		FirefoxDriver driver = new SeleniumInitier().initDriver();
		try {

			try (SqlSession session = new SessionFactory().open()) {
				TasksDao dao = session.getMapper(TasksDao.class);
				List<Task> tasks = dao.listActiveTask();
				for (Task task : tasks) {
					doTask(driver, task);
				}
			}
		} finally {
			driver.quit();
		}
	}

	private void doTask(FirefoxDriver driver, Task task) {
		try {
			driver.get(task.getUrl());
			TaskParser parser = new TaskParserRetreiver().resolve(task);
			parser.sleep();
			parser.parse(Jsoup.parse(driver.getPageSource()), task);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
