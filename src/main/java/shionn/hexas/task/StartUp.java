package shionn.hexas.task;

import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.mongojack.JacksonDBCollection;
import org.pircbotx.exception.IrcException;
import org.slf4j.Logger;

import shionn.hexas.bot.HexasBot;
import shionn.hexas.mongo.mo.Channel;

/**
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
public class StartUp implements ServletContextListener {

	@Inject
	private Logger logger;

	@Inject
	private HexasBot bot;
	
	@Inject
	private ScheduledExecutorService executor;
	
	@Inject
	private JacksonDBCollection<Channel, String> channels;

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		startBot();
		executor.schedule(new Runnable() {
			@Override
			public void run() {
				for (Channel channel : channels.find()) {
					bot.join(channel.getName());
				}
				bot.join("#gshionn");
			}
		}, 10, TimeUnit.SECONDS);
	}

	public void startBot() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					logger.info("Hexas bot lancé");
					bot.startBot();
				} catch (IOException | IrcException e) {
					logger.error("Erreur lors du lancement du bot", e);
				}
			}
		}).start();
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		executor.shutdown();
		bot.stop();
	}

}
