package hexas.discord;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.ApplicationScope;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

@Configuration
public class BotProducer {

	@Bean(destroyMethod = "shutdown")
	@ApplicationScope()
	public JDA jda() throws IOException, InterruptedException {
		Properties properties = new Properties();
		properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("token.properties"));
		return JDABuilder
				.create(properties.getProperty("token"), Arrays.asList(GatewayIntent.MESSAGE_CONTENT))
				.setAutoReconnect(true)
				.build()
				.awaitReady();
	}


}
