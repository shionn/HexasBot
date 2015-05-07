package shionn.hexas.task;

import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.pircbotx.exception.IrcException;
import org.slf4j.Logger;

import shionn.hexas.bot.HexasBot;
import shionn.hexas.bot.HexasBotJoinChannelTask;
import shionn.hexas.configuration.ConfigurationKey;

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
	@shionn.hexas.configuration.Configuration(ConfigurationKey.BotAutoJoinDelai)
	private int delai;

	@Inject
	private HexasBotJoinChannelTask joinChannelTask;

	@Inject
	private ScheduledExecutorService executor;

	@Inject
	private HexasBot bot;

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					logger.info("Hexas bot lancé");
					bot.start();
				} catch (IOException | IrcException e) {
					logger.error("Erreur lors du lancement du bot", e);
				}
			}
		}).start();
		executor.scheduleAtFixedRate(joinChannelTask, 10, delai * 60, TimeUnit.SECONDS);
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		executor.shutdown();
		bot.stop();
	}

}
