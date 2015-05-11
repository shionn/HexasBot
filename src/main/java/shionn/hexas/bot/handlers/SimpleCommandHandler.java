package shionn.hexas.bot.handlers;

import javax.inject.Named;

import org.pircbotx.hooks.events.MessageEvent;

import shionn.hexas.bot.HexasBot;
import shionn.hexas.bot.messages.Message;
import shionn.hexas.mongo.mo.SimpleCommandMo;

/**
 * Traite les message de type {@link SimpleCommandHandler}
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
@Named
public class SimpleCommandHandler {

	public void handle(SimpleCommandMo command, MessageEvent<HexasBot> event) {
		if (event.getMessage().startsWith(command.getCommand())) {
			event.getChannel().send().message(buildMessage(command, event));
		}
	}

	public String buildMessage(SimpleCommandMo command, MessageEvent<HexasBot> event) {
		return new Message(command.getMessage()).event(event).message();
	}

}
