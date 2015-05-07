package shionn.hexas.bot.handlers;

import javax.inject.Inject;
import javax.inject.Named;

import org.mongojack.JacksonDBCollection;
import org.pircbotx.hooks.events.MessageEvent;

import shionn.hexas.bot.HexasBot;
import shionn.hexas.bot.messages.MessageBuilderFactoy;
import shionn.hexas.mongo.mo.Channel;
import shionn.hexas.mongo.mo.SimpleCommand;

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

	@Inject
	private JacksonDBCollection<Channel, String> channels;

	@Inject
	private MessageBuilderFactoy messages;

	public void handle(MessageEvent<HexasBot> event) {
		if (event.getMessage().indexOf('!') == 0) {
			Channel channel = channels.findOneById(event.getChannel().getName());

			for (SimpleCommand command : channel.getSimpleCommands()) {
				if (event.getMessage().startsWith(command.getCommand())) {
					event.getChannel().send()
							.message(messages.build(command.getMessage()).event(event).message());
				}
			}
		}
	}

}
