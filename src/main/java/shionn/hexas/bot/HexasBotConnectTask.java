package shionn.hexas.bot;

import java.io.IOException;

import org.pircbotx.exception.IrcException;
import org.slf4j.Logger;

/**
 * Tache de lancement
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
public class HexasBotConnectTask implements Runnable {

	private HexasBot bot;
	private Logger logger;

	public HexasBotConnectTask(HexasBot bot, Logger logger) {
		this.bot = bot;
		this.logger = logger;
	}

	@Override
	public void run() {
		try {
			logger.info("Hexas bot lancé");
			bot.start();
		} catch (IOException | IrcException e) {
			logger.error("Erreur lors du lancement du bot", e);
		}
	}
}
