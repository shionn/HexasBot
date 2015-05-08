package shionn.hexas.bot.handlers;

import javax.inject.Inject;
import javax.inject.Named;

import org.mongojack.JacksonDBCollection;
import org.pircbotx.hooks.events.MessageEvent;

import shionn.hexas.bot.HexasBot;
import shionn.hexas.mongo.mo.ChannelConfiguration;
import shionn.hexas.mongo.mo.SimpleCommand;
import shionn.hexas.mongo.mo.Timer;

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
	private JacksonDBCollection<ChannelConfiguration, String> channels;

	@Inject
	private SimpleCommandHandler simple;

	@Inject
	private TimerHandler timerHandler;

	@Inject
	private AdventureHandler adventure;

	public void handle(MessageEvent<HexasBot> event) {
		ChannelConfiguration channel = channels.findOneById(event.getChannel().getName());
		if (event.getMessage().indexOf('!') == 0) {
			for (SimpleCommand command : channel.getSimpleCommands()) {
				simple.handle(command, event);
			}
			if (channel.getAdventure().isActivated()) {
				adventure.handle(channel.getAdventure(), event);
			}
		} else {
			for (Timer timer : channel.getTimers()) {
				timerHandler.handle(timer, event);
			}
		}
	}

}
