package shionn.hexas.bot.handlers.adventure;

import javax.inject.Inject;
import javax.inject.Named;

import org.mongojack.JacksonDBCollection;
import org.pircbotx.hooks.events.MessageEvent;

import shionn.hexas.bot.HexasBot;
import shionn.hexas.bot.handlers.adventure.manipulator.Player;
import shionn.hexas.bot.messages.Message;
import shionn.hexas.mongo.mo.adventure.AdventureMo;
import shionn.hexas.mongo.mo.adventure.PlayerMo;

/**
 * Traite la commande bag
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
@Named
public class BagHandler {
	@Inject
	private JacksonDBCollection<PlayerMo, String> players;

	public void run(Player player, AdventureMo adventure, MessageEvent<HexasBot> event) {
		if (player.items().isEmpty()) {
			new Message(adventure).bagEmpty().send(event);
		} else {
			new Message(adventure).bag().bag(player.items()).send(event);
		}
		players.save(player.updateLastBag().mo());
	}

}
