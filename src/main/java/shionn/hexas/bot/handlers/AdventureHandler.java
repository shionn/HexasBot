package shionn.hexas.bot.handlers;

import javax.inject.Inject;
import javax.inject.Named;

import org.mongojack.JacksonDBCollection;
import org.pircbotx.hooks.events.MessageEvent;

import shionn.hexas.bot.HexasBot;
import shionn.hexas.bot.handlers.adventure.Bag;
import shionn.hexas.bot.handlers.adventure.Battle;
import shionn.hexas.bot.handlers.adventure.Stat;
import shionn.hexas.bot.messages.MessageBuilder;
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

	private static final int MILLIS_IN_MIN = 60 * 1000;

	@Inject
	private JacksonDBCollection<Player, String> players;

	@Inject
	private Battle battle;

	@Inject
	private Stat stat;

	@Inject
	private Bag bag;

	@Inject
	private ItemUse use;

	@Inject
	private Craft craft;

	public void handle(Adventure adventure, MessageEvent<HexasBot> event) {
		Player player = getPlayer(adventure, event);
		if (event.getMessage().equals(adventure.getCommands().getBattle())) {
			if (isNotTooEarly(adventure, player)) {
				battle.run(player, adventure, event);
			} else {
				new MessageBuilder(adventure.getMessages().getBattleColdDown()).coldDown(
						adventure.getCommands().getBattleColdDown()).send(event);
			}
		} else if (event.getMessage().equals(adventure.getCommands().getStat())) {
			stat.run(player, adventure, event);
		} else if (event.getMessage().equals(adventure.getCommands().getBag())) {
			bag.run(player, adventure, event);
		} else if (event.getMessage().startsWith(adventure.getCommands().getItemUse())) {
			use.run(player, adventure, event);
		} else if (event.getMessage().startsWith(adventure.getCommands().getCraft())) {
			craft.run(player, adventure, event);
		}
	}

	private boolean isNotTooEarly(Adventure adventure, Player player) {
		return player.getLastBattle() + adventure.getCommands().getBattleColdDown() * MILLIS_IN_MIN <= System
				.currentTimeMillis();
	}

	private Player getPlayer(Adventure adventure, MessageEvent<HexasBot> event) {
		String key = event.getUser().getNick() + event.getChannel().getName();
		Player player = players.findOneById(key);
		if (player == null) {
			player = new Player(key);
			player.setPv(adventure.getGamer().getPvBase());
			player.setMaxPv(adventure.getGamer().getPvBase());
			players.insert(player);
		}
		return player;
	}

}
