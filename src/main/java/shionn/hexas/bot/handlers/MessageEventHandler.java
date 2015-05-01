package shionn.hexas.bot.handlers;

import javax.inject.Named;

import org.pircbotx.hooks.events.MessageEvent;

import shionn.hexas.bot.HexasBot;

/**
 * 
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
@Named
public class MessageEventHandler {

	public void handle(MessageEvent<HexasBot> event) {
		String message = event.getMessage();
		if (message.startsWith("!")) {
			switch (message) {
			case "!test":
				event.getChannel().send().message("Ton test marche : " + event.getUser().getNick());
				break;

			default:
				break;
			}
		}
	}

}
