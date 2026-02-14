package hexas.discord;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.session.SqlSession;

import hexas.db.SessionFactory;
import hexas.db.dao.HumbleBundleDao;
import hexas.db.dbo.Bundle;
import hexas.db.dbo.BundleChoice;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.requests.GatewayIntent;

@RequiredArgsConstructor
public class HumbleNotifer implements Runnable, EventListener {

//	private final JDA bot;

	@Override
	public void run() {
		try (SqlSession session = new SessionFactory().open()) {
			HumbleBundleDao dao = session.getMapper(HumbleBundleDao.class);
			Bundle bundle = dao.oneToNotify();
			if (bundle != null) {
				JDA bot = buildBot();
				String message = buildMessage(bundle);

				List<TextChannel> channels = bot.getTextChannelsByName("humble-bundle", true);
				channels.forEach(channel -> channel.sendMessage(message).queue());
				dao.markNotifyied(bundle);
				session.commit();
				bot.shutdown();
				bot.awaitShutdown();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private String buildMessage(Bundle bundle) {
		Iterator<BundleChoice> choices = bundle.getChoices().iterator();
		BundleChoice choice = choices.next();
		String message = "@here **Humble Bundle** propose un pack de jeu [" + bundle.getName() + "](" + bundle.getUrl()
				+ ") contenant :\n";
		Iterator<String> games = choice.getGames().iterator();
		while (games.hasNext() && message.length() < 1800) {
			message += "- " + games.next() + "\n";
		}
		if (games.hasNext()) {
			int rest = 0;
			while (games.hasNext()) {
				games.next();
				rest++;
			}
			message += "- et encore " + rest + " autres.";
		}
		message += "Pour " + choice.getPrice() + "€, disponible jusqu'au "
				+ new SimpleDateFormat("dd MMMM yyyy").format(bundle.getEndDate()) + ".";
		return message;
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
