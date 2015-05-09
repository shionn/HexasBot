package shionn.hexas.bot.handlers.adventure;

import org.pircbotx.hooks.events.MessageEvent;

import shionn.hexas.bot.HexasBot;
import shionn.hexas.bot.messages.MessageBuilder;
import shionn.hexas.mongo.mo.adventure.Adventure;
import shionn.hexas.mongo.mo.adventure.Player;

/**
 * Traite la commande bag
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
public class Bag {

	public void run(Player player, Adventure adventure, MessageEvent<HexasBot> event) {
		new MessageBuilder(adventure.getMessages().getBag()).bag(player.getItems().toString())
				.send(event);
	}

}
