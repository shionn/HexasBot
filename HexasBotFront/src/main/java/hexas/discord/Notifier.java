package hexas.discord;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.session.SqlSession;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import hexas.db.SessionFactory;
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

	private static final long CANAL_NOTFICATION = 1123512494468644984L;

	private ExecutorService execute = Executors.newSingleThreadExecutor();

	@Scheduled(fixedDelay = 1, timeUnit = TimeUnit.SECONDS)
	public void notiyDiscord() throws IOException, InterruptedException {
//		System.out.println("notify");
		execute.submit(new Runnable() {
			@Override
			public void run() {
				try (SqlSession session = new SessionFactory().open()) {
					ProductDao dao = session.getMapper(ProductDao.class);
					List<Product> products = dao.toNotify();
//					System.out.println(products);
					if (!products.isEmpty()) {
						JDA bot = buildBot();
//						bot.getTextChannels().stream().forEach(System.out::println);
						for (Product product : products) {
							List<TextChannel> channels = getChannel(product, bot);
							String message = buildMessage(product);
							channels.forEach(c -> c.sendMessage(message).queue());
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

			private String buildMessage(Product product) {
				return product.getMarque() + " " + product.getMetaModel() + " "
						+ product.getModel() + " **" + product.getLastPrice() + "** par "
						+ product.getVendor() + "\n" + product.getUrl();
			}
		});
	}

	private List<TextChannel> getChannel(Product product, JDA bot) {
		List<TextChannel> channels = bot.getTextChannelsByName(product.getNotifyChannel(), true);
		if (channels.isEmpty()) {
			channels = Arrays.asList(bot.getTextChannelById(CANAL_NOTFICATION));
		}
		return channels;
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
