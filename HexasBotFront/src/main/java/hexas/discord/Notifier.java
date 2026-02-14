package hexas.discord;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;

@Component
@RequiredArgsConstructor
public class Notifier {

	private ExecutorService execute = Executors.newSingleThreadExecutor();
	private final JDA jda;

	@Scheduled(fixedDelay = 30, timeUnit = TimeUnit.SECONDS)
	public void launchNotiy() throws IOException, InterruptedException {
		execute.submit(new GpuDropNotifer(jda));
		execute.submit(new HumbleNotifer(jda));
	}


}
