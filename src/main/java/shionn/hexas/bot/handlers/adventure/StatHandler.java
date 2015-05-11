package shionn.hexas.bot.handlers.adventure;

import javax.inject.Inject;
import javax.inject.Named;

import org.mongojack.JacksonDBCollection;
import org.pircbotx.hooks.events.MessageEvent;

import shionn.hexas.bot.HexasBot;
import shionn.hexas.bot.handlers.adventure.action.NextLvl;
import shionn.hexas.bot.handlers.adventure.manipulator.Player;
import shionn.hexas.bot.messages.Message;
import shionn.hexas.mongo.mo.adventure.AdventureMo;
import shionn.hexas.mongo.mo.adventure.PlayerMo;

/**
 * gere une commande de stat
 * 
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y+
 */
@Named
public class StatHandler {
	@Inject
	private JacksonDBCollection<PlayerMo, String> players;

	private NextLvl nextLvl = new NextLvl();

	public void run(Player player, AdventureMo adventure, MessageEvent<HexasBot> event) {
		new Message(adventure).stat().player(player.mo())
				.nextXp(nextLvl.xp(adventure, player.mo())).send(event);
		players.save(player.updateLastStat().mo());
	}

}
