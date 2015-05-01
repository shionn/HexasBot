package shionn.hexas.bot;

import javax.inject.Inject;
import javax.inject.Named;

import org.pircbotx.hooks.Event;
import org.pircbotx.hooks.Listener;
import org.pircbotx.hooks.events.MessageEvent;
import org.slf4j.Logger;

import shionn.hexas.bot.handlers.MessageEventHandler;

/**
 * Listener pour le bot
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
@Named
public class HexasBotListener implements Listener<HexasBot> {

	@Inject
	private Logger logger;

	@Inject
	private MessageEventHandler message;

	@Override
	public void onEvent(Event<HexasBot> event) throws Exception {
		if (event instanceof MessageEvent) {
			message.handle((MessageEvent<HexasBot>) event);
		} else {
			logger.info(event.toString());
		}
	}

}
