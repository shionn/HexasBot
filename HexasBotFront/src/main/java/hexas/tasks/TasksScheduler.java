package hexas.tasks;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TasksScheduler implements Serializable {
	private static final long serialVersionUID = -1905973814573778921L;

	@Autowired
	@Value("${scan.root.dir}")
	private String rootDir;

	@Autowired
	@Value("${scan.selenium.enable}")
	private boolean enable;

	@Scheduled(fixedDelay = 15, timeUnit = TimeUnit.MINUTES)
	public void scanProductWithSelenium() {
		if (enable) {
			try {
				System.out.println("Scan with selenium");
				execBash("./selenium.sh");
				System.out.println("fin du scan");
			} catch (IOException | InterruptedException | RuntimeException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Scanner is disable");
		}
	}

	@Scheduled(fixedDelay = 6, timeUnit = TimeUnit.HOURS)
	public void scanHumbleBundle() {
		if (enable) {
			try {
				execBash("./selenium-humble-bundle.sh");
			} catch (IOException | InterruptedException | RuntimeException e) {
				e.printStackTrace();
			}
		}
	}

	@Scheduled(cron = "0 0 3 *  * *")
	public void scanAptUpdate() {
		try {
			execBash("./selenium-apt-cache.sh");
			System.out.println("fin du scan");
		} catch (IOException | InterruptedException | RuntimeException e) {
			e.printStackTrace();
		}
	}

	private void execBash(String script) throws InterruptedException, IOException {
		ProcessBuilder builder = new ProcessBuilder(script);
		builder.redirectError(ProcessBuilder.Redirect.INHERIT);
		builder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
		builder.directory(new File(rootDir));
		builder.start().waitFor();
	}

}
