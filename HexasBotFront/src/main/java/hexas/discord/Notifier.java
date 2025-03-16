package hexas.discord;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.session.SqlSession;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import hexas.db.SpringSessionFactory;
import hexas.db.dao.ProductDao;
import hexas.db.dbo.Product;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.requests.GatewayIntent;

@Component
public class Notifier implements EventListener {

	private static final long CANAL = 1123512494468644984L;

	@Scheduled(fixedDelay = 30, timeUnit = TimeUnit.SECONDS)
	public void notiyDiscord() throws IOException, InterruptedException {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try (SqlSession session = new SpringSessionFactory().open()) {
					ProductDao dao = session.getMapper(ProductDao.class);
					List<Product> products = dao.toNotify();
//					System.out.println(products);
					if (!products.isEmpty()) {
						JDA bot = buildBot();
						TextChannel channel = bot.getTextChannelById(CANAL);
//						bot.getTextChannels().stream().forEach(System.out::println);
						for (Product product : products) {
							channel
									.sendMessage(product.getMarque() + " " + product.getMetaModel() + " "
											+ product.getModel() + " **" + product.getLastPrice() + "**\n"
											+ product.getUrl())
									.queue();
							dao.markNotifyied(product);
							session.commit();
						}
						bot.shutdown();
						bot.awaitShutdown();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
		
	}

	private JDA buildBot() throws IOException, InterruptedException {
		Properties properties = new Properties();
		properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("token.properties"));
		return JDABuilder
				.create(properties.getProperty("token"), Arrays.asList(GatewayIntent.MESSAGE_CONTENT))
				.addEventListeners(this)
				.build()
				.awaitReady();
	}

	@Override
	public void onEvent(GenericEvent event) {
		// nothing to do
	}

}
