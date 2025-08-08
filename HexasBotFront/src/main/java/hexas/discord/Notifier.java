package hexas.discord;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Notifier {

	private ExecutorService execute = Executors.newSingleThreadExecutor();

	@Scheduled(fixedDelay = 30, timeUnit = TimeUnit.SECONDS)
	public void launchNotiy() throws IOException, InterruptedException {
		execute.submit(new GpuDropNotifer());
		execute.submit(new HumbleNotifer());
	}


}
