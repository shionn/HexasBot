package hexas.discord;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.session.SqlSession;

import hexas.db.SessionFactory;
import hexas.db.dao.ProductPriceNotifyDao;
import hexas.db.dbo.ProductPriceNotification;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class GpuDropNotifer implements Runnable, EventListener {
	private static final long CANAL_NOTFICATION = 1123512494468644984L;

	@Override
	public void run() {
		try (SqlSession session = new SessionFactory().open()) {
			ProductPriceNotifyDao dao = session.getMapper(ProductPriceNotifyDao.class);
			List<ProductPriceNotification> notifications = dao.list();
			notifications = notifications.stream().toList();
			if (!notifications.isEmpty()) {
				JDA bot = buildBot();
//				bot.getTextChannels().stream().forEach(System.out::println);
				for (ProductPriceNotification notification : notifications) {
					List<TextChannel> channels = getChannel(notification, bot);
					String message = buildMessage(notification);
					channels.forEach(c -> c.sendMessage(message).queue());
					dao.markNotifyied(notification);
					session.commit();
				}
				bot.shutdown();
				bot.awaitShutdown();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String buildMessage(ProductPriceNotification notification) {
		return "@here " + notification.getMarque() + " " + notification.getName() + " **" + notification.getPrice()
				+ "** " + "\n" + notification.getUrl();
	}

	private List<TextChannel> getChannel(ProductPriceNotification notification, JDA bot) {
		List<TextChannel> channels = bot.getTextChannelsByName(notification.getNotifyChannel(), true);
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
