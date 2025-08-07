package hexas;

import java.time.Duration;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.jsoup.Jsoup;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import hexas.db.SessionFactory;
import hexas.db.dao.TasksDao;
import hexas.db.dbo.Task;

public class SeleniumProductScanner {

	public static void main(String[] args) {
		new SeleniumProductScanner().doParsing();
	}

	private void doParsing() {
		FirefoxDriver driver = initDriver();
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

	FirefoxDriver initDriver() {
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("intl.accept_languages", "fr-FR");
		FirefoxOptions options = new FirefoxOptions();
		options.setProfile(profile);
		FirefoxDriver driver = new FirefoxDriver(options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
		driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
		return driver;
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
