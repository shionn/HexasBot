package shionn.hexas.bot.handlers.adventure;

import javax.inject.Inject;
import javax.inject.Named;

import org.mongojack.JacksonDBCollection;
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
@Named
public class Bag {
	@Inject
	private JacksonDBCollection<Player, String> players;

	public void run(Player player, Adventure adventure, MessageEvent<HexasBot> event) {
		player.setLastBag(System.currentTimeMillis());
		if (player.getItems().isEmpty()) {
			new MessageBuilder(adventure.getMessages().getBagEmpty()).send(event);
		} else {
			new MessageBuilder(adventure.getMessages().getBag()).bag(player.getItems().toString())
					.send(event);
		}
		players.save(player);
	}

}
