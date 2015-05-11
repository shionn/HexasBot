package shionn.hexas.bot.handlers;

import javax.inject.Inject;
import javax.inject.Named;

import org.mongojack.JacksonDBCollection;
import org.pircbotx.hooks.events.MessageEvent;

import shionn.hexas.bot.HexasBot;
import shionn.hexas.configuration.Configuration;
import shionn.hexas.configuration.ConfigurationKey;
import shionn.hexas.mongo.mo.ChannelConfigurationMo;
import shionn.hexas.mongo.mo.SimpleCommandMo;
import shionn.hexas.mongo.mo.TimerMo;

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
	private JacksonDBCollection<ChannelConfigurationMo, String> channels;
	@Inject
	private SimpleCommandHandler simple;
	@Inject
	private TimerHandler timerHandler;
	@Inject
	private AdventureHandler adventure;
	@Inject
	@Configuration(ConfigurationKey.Debug)
	private boolean debug;
	@Inject
	@Configuration(ConfigurationKey.BotName)
	private String botname;

	public void handle(MessageEvent<HexasBot> event) {
		if (!debug || "shi0nn".equals(event.getUser().getNick())) {
			ChannelConfigurationMo channel = channels.findOneById(event.getChannel().getName());
			handle(event, channel);
		}
	}

	private void handle(MessageEvent<HexasBot> event, ChannelConfigurationMo channel) {
		if (event.getMessage().indexOf('!') == 0) {
			for (SimpleCommandMo command : channel.getSimpleCommands()) {
				simple.handle(command, event);
			}
			if (channel.getAdventure().isActivated()) {
				adventure.handle(channel.getAdventure(), event);
			}
		} else {
			for (TimerMo timer : channel.getTimers()) {
				if (timer.isActivated()) {
					timerHandler.handle(timer, event);
				}
			}
		}
	}

}
