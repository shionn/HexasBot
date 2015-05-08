package shionn.hexas.bot.handlers;

import javax.inject.Inject;
import javax.inject.Named;

import org.mongojack.JacksonDBCollection;
import org.pircbotx.hooks.events.MessageEvent;

import shionn.hexas.bot.HexasBot;
import shionn.hexas.bot.handlers.adventure.Battle;
import shionn.hexas.mongo.mo.adventure.Adventure;
import shionn.hexas.mongo.mo.adventure.Player;

/**
 * Traite les commandes pour l'aventure
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
@Named
public class AdventureHandler {

	@Inject
	private JacksonDBCollection<Player, String> players;

	@Inject
	private Battle battle;


	public void handle(Adventure adventure, MessageEvent<HexasBot> event) {
		Player player = getPlayer(adventure, event);
		if (event.getMessage().equals(adventure.getCommands().getBattle())) {
			battle.run(player, adventure, event);
		}
	}

	private Player getPlayer(Adventure adventure, MessageEvent<HexasBot> event) {
		String key = event.getUser().getNick() + event.getChannel();
		Player player = players.findOneById(key);
		if (player == null) {
			player = new Player(key);
			players.insert(player);
		}
		return player;
	}


}
